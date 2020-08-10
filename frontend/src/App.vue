<template>
<div>
  <div id="app">
    <v-app id="inspire">
      <v-app-bar
      id="header"
        elevate-on-scroll
        absolute
        color="teal lighten-3"
        dark
      > 
        <v-btn text to="/"><v-toolbar-title color="green">
          비상구</v-toolbar-title>
        </v-btn>
        <v-spacer></v-spacer>
        <div v-if="isLoggedIn">
          <small class="font-weight-bold">{{ this.$store.state.login_user }}</small>님 환영합니다.
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

      <!-- <v-show=messages>
      <v-for=message in messages>
      <div if=message.tags class="alert alert-message.tags alert-dismissable fade show">{{ message }}
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      </div>
      </v-for>
      </v-if> -->

        <router-view class="container my-10 mx-10px"/>
        <v-footer
          id="footer"
          app
          padless
        >
          <v-col
            class="text-center"
            cols="12"
          >
              <small>&copy; {{ new Date().getFullYear() }},EXIT<strong>
            made by <a href="https://github.com/gyw8526">@gyw8526</a> <a href="https://github.com/dmdekf">@dmdekf</a> <a href="https://lab.ssafy.com/twoposition">@twoposition</a> <a href="https://lab.ssafy.com/twoposition">@wjsgudwls89</a></strong></small>
          </v-col>
      </v-footer>
    </v-app>
  </div>
 </div> 
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import axios from "axios";

export default {
  naem: 'App',
  computed: {
    ...mapGetters(['isLoggedIn']),
  },
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
#header {
  position: fixed;
}
table {
  border-collapse: collapse;
  width: 100%;
  word-break: break-all;
}
th {
    width: 50px;
}
td,th {
    border: 1px solid black;
}
a { 
  text-decoration:none;
  }
#footer {
  opacity: 80%;
}
#footer a {
  color:black; 
}
</style>