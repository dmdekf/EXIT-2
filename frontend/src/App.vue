<template>
  <div id="app">
    <v-app id="inspire">
  <div id="header" >
    <v-app-bar
      absolute
      color="teal lighten-3"
      dark
    > 
      <v-btn text to="/"><v-toolbar-title>
        비상구</v-toolbar-title>
      </v-btn>
      <v-spacer></v-spacer>
      <div v-if="loginStatus">
        
        <small class="font-weight-bold">{{ this.nickname }}</small
        >님 환영합니다.
      <v-btn rounded class="ma-2" color="primary" to="/search" >
            <v-icon>mdi-magnify</v-icon>검색하기
          </v-btn>
      <v-btn rounded class="ma-2" color="#9896f1" v-on:click="logout">
          로그아웃
        </v-btn>
        <v-btn rounded class="ma-2" color="#8c82fc" v-on:click="moveDetail">
          회원정보
        </v-btn>
        </div>
        <div v-if="!loginStatus">        
        <v-btn rounded class="ma-2" color="#2eb872" to="/user/logintest" >
            <v-icon>mdi-account-outline</v-icon>로그인
          </v-btn>
        </div>
        
    </v-app-bar>
    </div>
    <div class="container mt-10">
    </div>
    </v-app>
    <router-view />
  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>

<script>
import { mapGetters } from 'vuex'
import constants from "./lib/constants";
import axios from "axios";
// 토큰 및 사용자 정보를 저장하기 위해서 세션 스토리지를 사용한다.
const storage = window.sessionStorage;


export default {
  naem: 'App',
  computed: {
    ...mapGetters(['isLoggedIn'])
  },
  data() {
    return {
      constants,
      nickname:"",
      email: "",
      password: "",
      message: "로그인해주세요.",
      status: "",
      token: "",
      info: "",
      loginStatus: false,
    };
  },
  beforeDestroy () {
      if (typeof window !== 'undefined') {
        window.removeEventListener('resize', this.onResize, { passive: true })
      }
    },
    created() {
      let url = this.$router.name;
      this.checkUrl(url);
    },
    mounted () {
      this.onResize()
      window.addEventListener('resize', this.onResize, { passive: true })
    },
  methods: {
    checkUrl(url) {
      let array = [
        constants.URL_TYPE.USER.LOGIN,
        constants.URL_TYPE.USER.JOIN,
        constants.URL_TYPE.USER.SIGNUP,
        constants.URL_TYPE.USER.DETAIL,
        constants.URL_TYPE.USER.UPDATE,
        constants.URL_TYPE.USER.DELETE,
        constants.URL_TYPE.POST.SEARCH,
        constants.URL_TYPE.POST.MAIN,
        constants.URL_TYPE.POST.WRITE,
        constants.URL_TYPE.POST.WRITECOMPLETE,
        constants.URL_TYPE.POST.DETAIL,
        constants.URL_TYPE.POST.UPDATE,
        // LOGIN,
        // MAIN,
        // SIGNUP,
      ];
    },
    setInfo(status, token, info) {
      this.status = status;
      this.token = token;
      this.info = info;
    },
    logout() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      storage.setItem("user_email","");
      this.init();
    },
    
    init() {
      if (storage.getItem("jwt-auth-token")) {
        this.nickname = storage.getItem("login_user");
        this.loginStatus = true;
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다.";
      } else {
        storage.setItem("jwt-auth-token", "");
        
        this.nickname = "";
        this.loginStatus = false;
      }
    } // init
    ,
    moveDetail() {
      this.$router.push("/user/detail");
    },
    moveSearch(){
      this.$router.push("/search");
    },
  },
  mounted() {
    this.init();
  }
};
</script>
<style scoped>
table {
    border-collapse: collapse;
    width: 100%;
    word-break: break-all;
}
th {
    width: 50px;
}
td,
th {
    border: 1px solid black;
}
</style>