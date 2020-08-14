import Vue from "vue";
import Vuex from "vuex";

import axios from "axios";
import Cookies from 'js-cookie'

import router from "@/router";
import SERVER from "@/api/api";
import createPersistedState from 'vuex-persistedstate';
Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    // auth
    token: "",
    user_email: "",
    status: "",
    login_user: "",
    alert: {
      on: false,
      1: { 0: "red", 1: "삭제되었습니다." },
      2: { 0: "warning", 1: "입력정보를 확인하세요" },
      3: { 0: "success", 1: "작성이 완료되었습니다." },
      4: { 0: "success", 1: "로그인 되었습니다." },
      5: { 0: "primary", 1: "로그아웃 되었습니다." },
      6: { 0: "primary", 1:"수정이 완료되었습니다."},
      num: 1
    }
  },
  plugins: [createPersistedState()],
  getters: {
    // auth
    info: state => ({
      status: state.status,
      token: state.token,
    }),
    isLoggedIn: state => !!state.token,
    col: state => { return state.alert[state.alert.num][0] },
    msg: state => { return state.alert[state.alert.num][1] }
  },
  mutations: {
    SET_ON(state, { num }) {
      state.alert.on = !state.alert.on
      state.alert.num = num
    },
    // auth
    SET_TOKEN(state, { token }) {
      state.token = token
    },
    SET_EMAIL(state, { user_email }) {
      state.user_email = user_email
    },
    SET_STATUS(state, { status }) {
      state.status = status
    },
    SET_USER(state, { login_user }) {
      state.login_user = login_user
    },
  },
  actions: {
    postAuthData({ commit }, info) {
      axios.post(SERVER.URL + info.location, info.data)
        .then(res => {
          commit('SET_TOKEN', res.headers["jwt-auth-token"])
          router.push({ name: 'Home' })
        })
        .catch(err => console.log(err.response.data))
    },

    // auth
    signup({ commit }, signupData) {
      axios({
        method: "post",
        url: SERVER.URL + "/account/signup",
        data: {
          email: signupData.email,
          password: signupData.password,
          nickname: signupData.nickName,
        },
      })
        .then((res) => {
          if (res.data.status) {
            this.$router.push("/user/signup");
          }
        })
        .then(res => {
          if (res.data.status) {
            commit('SET_TOKEN', res.data.key)
            this.$router.push("/user/signup");
          }
        })
        .catch(err => console.log(err.response.data))
      alert("회원가입에 실패했습니다.");
    },
    login({ commit, getters }, loginData) {
      console.log(loginData)
      axios
        ({
          method: 'post',
          url: SERVER.URL + "/user/signin",
          data: {
            email: loginData.email, password: loginData.password
          }
        })
        // console.log(res.data)
        .then((res) => {
          console.log(res.data.status)
          console.dir(res.headers["jwt-auth-token"]);
          if (res.data.status) {
            commit('SET_TOKEN', { token: res.headers["jwt-auth-token"] })
            commit('SET_EMAIL', { user_email: res.data.data.email })
            commit('SET_USER', { login_user: res.data.data.uid })
            commit('SET_STATUS', { status: res.data.status })
            getters.config
            console.log(state.token)
            this.showAlert(4)
            // alert(state.login_user + "님 로그인 되었습니다.");
          } else {
            this.showAlert(2)
          }
        })
        .catch(e => {
          
          // console.log(e.response.data)
          // getters.info = e.response
        });
      router.push({ name: "MAIN" })
    },
    sociallogin({ commit, getters }, email) {
      console.log(email)
      axios
        ({
          method: 'post',
          url: SERVER.URL + "/user/socialsignin",
          data: {
            email: email
          }
        })
        // console.log(res.data)
        .then((res) => {
          console.log(res.data.status)
          console.dir(res.headers["jwt-auth-token"]);
          if (res.data.status) {
            commit('SET_TOKEN', { token: res.headers["jwt-auth-token"] })
            commit('SET_EMAIL', { user_email: res.data.data.email })
            commit('SET_USER', { login_user: res.data.data.uid })
            commit('SET_STATUS', { status: res.data.data.status })
            getters.config
            this.showAlert(4)
            // alert(state.login_user + "님 로그인 되었습니다.");
          } else {
            this.showAlert(2)
            // alert("입력 정보를 확인하세요.");
          }
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
            this.showAlert(2)
            // alert(error.response.data.data + "입력 정보를 확인하세요.");
          }
          else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
            this.showAlert(2)
            // alert(error.request + "입력 정보를 확인하세요.");
          }
          else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log('Error',);
            this.showAlert(2)
            // alert(error.message + "입력 정보를 확인하세요.");
          }
        })
        .then(
          router.push({ name: "MAIN" })
        )
      
    },
    logout({ commit }) {
      console.log(state.token)
      commit('SET_TOKEN', { token: "" })
      commit('SET_EMAIL', { user_email: "" })
      commit('SET_USER', { login_user: "" })
      commit('SET_STATUS', { status: "" })
      console.log(state.token)
      router.push({ name: "MAIN" })
      this.showAlert(5)
    },
    showAlert({ commit }, n) {
      commit('SET_ON', { num:n })
      setTimeout(()=>{
        commit('SET_ON', { num:3 })
      },5000)
    },
   
  },
})
