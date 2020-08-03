import Vue from "vue";
import Vuex from "vuex";

import axios from "axios";
import cookies from 'js-cookie'

import router from "@/router";
import SERVER from "@/api/api";
import constants from '../lib/constants'
Vue.use(Vuex);


export default new Vuex.Store({
  state: {
    // auth
    authToken: null,
    // articles
  },
  getters: {
    // auth
    loginStatus: state => !!state.authToken,
    // auth, articles
    config: state => ({ headers: { Authorization: `Token ${state.authToken}` } })
  },
  mutations: {
    // auth
    SET_TOKEN(state, { token }) {
      state.authToken = token
      localStorage.authToken = token
    }
  },
  actions: {
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
    login({ commit }, loginData) {      
      axios({
        method: "get",
        url:
          SERVER.URL+"/account/login?email=" +
          loginData.email +
          "&password=" +
          loginData.password
      })
      .then(res => {
        commit('SET_TOKEN', res.data.userkey)
        alert("로그인에 성공했습니다.");
        router.push({ name: constants.URL_TYPE.POST.MAIN })
      })
      .catch(err => console.log(err.response.data))
      alert("로그인에 실패했습니다.");
    },
    logout({ getters, commit }) {
      commit('SET_TOKEN', null)  
        cookies.remove('auth-token')  
        router.push({ name: constants.URL_TYPE.POST.MAIN })
    }
  },
})

