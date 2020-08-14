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
      <v-icon class="mr-2" large>mdi-code-tags</v-icon>
        <v-btn color="#3DBB3D" to="/"><v-toolbar-title color="white">
          EXIT</v-toolbar-title>
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
      <v-alert
        v-show="this.$store.state.alert.on"
        dismissible
        text
        fade
        :color="col"
        >
        {{msg}}
        </v-alert>
        <router-view class="container  mx-10px"/>
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
import { mapGetters, mapActions } from 'vuex'
import axios from "axios";
let cols = ""
export default {
  name: 'App',
  computed: {
    ...mapGetters(['msg', 'col']),
    ...mapGetters(['isLoggedIn'])
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
.v-alert {
  transform: initial;
  margin-bottom: 0;
}
#header {
  position: sticky;
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