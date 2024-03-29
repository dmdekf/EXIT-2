import Vue from "vue";
import Vuex from "vuex";

import axios from "axios";
import Cookies from 'js-cookie'

import router from "@/router";
import SERVER from "@/api/api";
import createPersistedState from 'vuex-persistedstate';
const storagesession = window.sessionStorage;
const storagelocal = window.localStorage;
Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    token: "",
    user_email: "",
    status: "",
    login_user: "",
    auth_token:"",
    alert: {
      on: false,
      1: { 0: "red", 1: "삭제되었습니다." },
      2: { 0: "warning", 1: "입력정보를 확인하세요" },
      3: { 0: "success", 1: "작성이 완료되었습니다." },
      4: { 0: "success", 1: "로그인 되었습니다." },
      5: { 0: "primary", 1: "로그아웃 되었습니다." },
      6: { 0: "primary", 1:"수정이 완료되었습니다."},
      num: 1
    },
    
  },
  plugins: [createPersistedState()],
  getters: {
    info: state => ({
      status: state.status,
      token: state.token,
      auth_token: state.auth_token
    }),
    isLoggedIn: state => !!state.token,
    col: state => { return state.alert[state.alert.num][0] },
    msg: state => { return state.alert[state.alert.num][1] }
  },
  mutations: {
    SET_ON(state, { num }) {
      state.alert.on = true
      state.alert.num = num
    },
    SET_AUTHTOKEN(state, { auth_token }) {
      state.auth_token = auth_token
    },
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
    showAlert({ commit, state }, n) {
      commit('SET_ON', { num:n })
      setTimeout(() => {
        state.alert.on=false
      },3000)
    },
    postAuthData({ commit }, info) {
      axios.post(SERVER.URL + info.location, info.data)
        .then(res => {
          commit('SET_TOKEN', res.headers["jwt-auth-token"])
          router.push({ name: 'Home' })
        })
        .catch(err => console.log(err.response.data))
    },

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
      this.dispatch("showAlert", 1)
    },
    login({ commit, getters,state }, loginData) {
      axios
        ({
          method: 'post',
          url: SERVER.URL + "/user/signin",
          data: {
            email: loginData.email, password: loginData.password
          }
        })
        .then((res) => {
          if (res.data.status) {
            commit('SET_TOKEN', { token: res.headers["jwt-auth-token"] })
            commit('SET_EMAIL', { user_email: res.data.data.email })
            commit('SET_USER', { login_user: res.data.data.uid })
            commit('SET_STATUS', { status: res.data.status })
            getters.config
            router.push({ name: "MAIN" })
            this.dispatch("showAlert",4)
            
          } else {
            this.dispatch("showAlert",2)
          }
        })
        .catch(e => {
          console.log(e.response.data)
          this.dispatch("showAlert",2)
        });
    },
    sociallogin({ commit, getters, state }, loginData) {
      commit('SET_AUTHTOKEN', { auth_token: loginData.auth_token })
      const email = loginData.email
      axios
        ({
          method: 'post',
          url: SERVER.URL + "/user/socialsignin",
          data: {
            email
          }
        })
        .then((res) => {
          if (res.data.status) {
            commit('SET_TOKEN', { token: res.headers["jwt-auth-token"] })
            commit('SET_EMAIL', { user_email: res.data.data.email })
            commit('SET_USER', { login_user: res.data.data.uid })
            commit('SET_STATUS', { status: res.data.status })
            
          } else {
            this.dispatch("showAlert", 2)
          }
        })
        .catch(function (error) {
          if (error.response) {
            console.log(error.response.data);
            this.dispatch("showAlert", 2)
          }
          else if (error.request) {
            console.log(error.request);
            this.dispatch("showAlert", 2)
          }
          else {
            this.dispatch("showAlert", 2)
          }
        })
        .then(() => {
          this.dispatch("showAlert", 4)
          router.push({ name: "MAIN" })
        })
      
    },
    logout({ commit, state }) {
      commit('SET_TOKEN', { token: "" })
      commit('SET_EMAIL', { user_email: "" })
      commit('SET_USER', { login_user: "" })
      commit('SET_STATUS', { status: "" })
      if (state.auth_token) {
        axios({
          method: "POST",
          url: "https://cors-anywhere.herokuapp.com/https://kapi.kakao.com/v1/user/logout",
          headers: {
            "Authorization": 'Bearer ' + state.auth_token,
            "Content-type": 'application/x-www-form-urlencoded;charset=utf-8'
          },
        })
          .then(() => {
            commit('SET_AUTHTOKEN', { auth_token: "" })
          })
          .catch(function (error) {
            if (error.response) {
              console.log(error.response.data);
            }
            else if (error.request) {
              console.log(error.request);
              this.dispatch("showAlert", 1)
            }
            else {
              this.dispatch("showAlert", 1)
            }
          })
          .then(() => {
            storagesession.clear()
            storagelocal.clear()
          })
          .then(() => {
            router.push({ name: "MAIN" })
            this.dispatch("showAlert", 5)
          })
      }
    },
  },
})