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
      <div v-if="isLoggedIn">
        
        <small class="font-weight-bold">{{ this.$store.state.login_user }}</small
        >님 환영합니다.
      <v-btn rounded class="ma-2" color="primary" to="/search" >
            <v-icon>mdi-magnify</v-icon>검색하기
          </v-btn>
      <v-btn rounded class="ma-2" color="#9896f1" to="/user/logout" >
          로그아웃
        </v-btn>
        <v-btn rounded class="ma-2" color="#8c82fc" v-on:click="moveDetail">
          회원정보
        </v-btn>
        </div>
        <div v-if="!isLoggedIn">        
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
import { mapState, mapGetters } from 'vuex'
import constants from "./lib/constants";
import axios from "axios";


export default {
  naem: 'App',
  computed: {
    ...mapGetters(['isLoggedIn']),
  },
  beforeDestroy () {
      if (typeof window !== 'undefined') {
        window.removeEventListener('resize', this.onResize, { passive: true })
      }
    },
    // mounted () {
    //   this.onResize()
    //   window.addEventListener('resize', this.onResize, { passive: true })
    // },
  methods: {
    
    moveDetail() {
      this.$router.push("/user/detail");
    },
    moveSearch(){
      this.$router.push("/search");
    },
  },
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