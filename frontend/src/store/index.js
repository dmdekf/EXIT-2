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
    login_user:"",
  },
  plugins: [createPersistedState()],
  getters: {
    // auth
    info: state => ({
      status: state.status,
      token: state.token,
    }),
    isLoggedIn: state => !!state.token,
    
    config: state => ({ headers: { Authorization: `Bearer ${state.token}` } })
  },
  mutations: {
    
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
            alert(state.login_user+"님 로그인 되었습니다.");
          } else {
            commit('SET_MESSAGE', "로그인해주세요.")
            alert("입력 정보를 확인하세요.");
          }
        })
        .catch(e => {
          
          // console.log(e.response.data)
          // getters.info = e.response
        });
      router.push({ name: "MAIN" })
    },
    sociallogin({ commit, getters }, gitemail) {
      console.log(gitemail)
      axios
        ({
          method: 'post',
          url: SERVER.URL + "/user/socialsignin",
          data: {
            email: gitemail
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
            alert(state.login_user+"님 로그인 되었습니다.");
          } else {
            alert("입력 정보를 확인하세요.");
          }
        })
        .catch(e => {
          
          console.log(e.response.data)
          alert(e.response.data.data+ "입력 정보를 확인하세요.");
        });
      router.push({ name: "MAIN" })
    },
    logout({ commit }) {
      commit('SET_TOKEN', { token: null })
      commit('SET_EMAIL', { user_email: null })
      commit('SET_USER', { login_user: null})
      commit('SET_STATUS', { status: null })  
      
      router.push({ name: "MAIN" })
    }
  },
})
