import Vue from "vue";
import Vuex from "vuex";

import axios from "axios";
import cookies from 'js-cookie'

import router from "@/router";
import SERVER from "@/api/api";
import constants from '../lib/constants'
Vue.use(Vuex);

const storage = window.sessionStorage;
export default new Vuex.Store({
  state: {
    // auth
    token: "",
    email: "",
    password: "",
    message: "로그인해주세요.",
    status: "",
    login_user:"",
  },
  getters: {
    // auth
    info: state => ({
      status: state.status,
      token: state.token,
      info: state.info
    }),
    isLoggedIn: state => !!state.token,
    
    config: state => ({ headers: { Authorization: `Token ${state.token}` } })
  },
  mutations: {
    // auth
    SET_TOKEN(state, { token }) {
      state.token = token
    },
    SET_EMAIL(state, { email }) {
      state.email = email
    },
    SET_PASSWORD(state, { password }) {
      state.password = password
    },
    SET_TOKEN(state, { token }) {
      state.token = token
    },
    SET_MESSAGE(state, { message }) {
      state.message = message
    },
    SET_STATUS(state, { status }) {
      state.status = status
    },
      SET_USER(state, { login_user }) {
      state.login_user = login_user
    },


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
          SERVER.URL + "user/signin",
        data: {
          email: loginData.email,
          password: loginData.password
        }
      })
        .then(res => {
          if (res.data.status) {
            commit('SET_TOKEN', res.headers["jwt-auth-token"])
            commit('SET_MESSAGE', res.data.data.email + "로 로그인 되었습니다.")
            commit('SET_EMAIL', res.data.data.email)
            commit('SET_PASSWORD', res.data.data)
            commit('SET_USER',res.data.data.uid)
            alert(state.message);
            router.push({ name: constants.URL_TYPE.POST.MAIN })
          } else {
            commit('SET_MESSAGE', "로그인해주세요.")
            alert("입력 정보를 확인하세요.");
          }
        })
        .catch(e => {
          this.setInfo("실패", "", JSON.stringify(e.response || e.message));
        });
    },

    logout({ getters, commit }) {
      commit('SET_TOKEN', null)  
        cookies.remove('auth-token')  
        router.push({ name: constants.URL_TYPE.POST.MAIN })
    }
  },
})

