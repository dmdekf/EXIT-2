<template>
  <v-app id="inspire">
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="8">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>로그인</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form ref="form" v-model="valid" lazy-validation>
                  <v-text-field
                    v-model="loginData.email"
                    id="email"
                    label="이메일을 입력해주세요"
                    name="email"
                    prepend-icon="mdi-account"
                    :rules="[(v) => /.+@.+\..+/.test(v) || 'E-mail 형식을 맞춰주세요.', (v) => (!!v) || '값을 입력해 주세요']"
                    type="email"
                    required
                    
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    v-model="loginData.password"
                    label="비밀번호"
                    name="password"
                    prepend-icon="mdi-lock"
                    :rules="[v => !!v || '값을 입력해 주세요' , (v) => (v && v.length >= 7) || '8글자 이상 입력해주세요']"
                    type="password"
                    required
                  ></v-text-field>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" :disabled="!valid" v-on:click="locallogin(loginData)">로그인</v-btn>
                <v-btn color="primary" to="/user/jointest">회원가입</v-btn>
              </v-card-actions>
                </v-form>
              </v-card-text>
              <div class="contents"> 
              <!-- <v-layout align-center justify-center class="ma-3 contents"> -->
                <v-row class="ma-3">
                <v-btn block class="grey lighten-2 mb-3" v-on:click="gitlogin" ><v-icon>mdi-github</v-icon> | Signin with GitHub </v-btn>
                </v-row>
                <v-row class="ma-3">
                <v-btn block class="yellow accent-4 mb-3" v-on:click="kakaologin"><v-icon>mdi-message</v-icon> | Signin with Kakao </v-btn>
                </v-row>
              <!-- </v-layout> -->
              </div>
              
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
import { required, valid,rules } from "vuelidate/lib/validators";
export default {
data() {
    return {
      valid:true,
      loginData: {
        email: null,
        password: null
      }
    }
  },
  methods: {
    ...mapActions(['login']),
    locallogin(loginData) {
        this.login(loginData)
      },
    kakaologin() {
      const client_id=process.env.VUE_APP_KAKAO
      const redirect_uri="http://i3a501.p.ssafy.io/user/logintest/kakao/callback"
      // const redirect_uri="http://localhost:3000/user/logintest/kakao/callback"
      const kakaoapi = "https://kauth.kakao.com/oauth/authorize?client_id="+client_id+"&redirect_uri="+redirect_uri+"&response_type=code"
     
      window.location.href=kakaoapi
    },
    gitlogin() {
      const client_id=process.env.VUE_APP_GIT
      const redirect_uri="http://i3a501.p.ssafy.io/user/logintest/callback"
      // const redirect_uri="http://localhost:3000/user/logintest/callback"
      const gitapi = "https://github.com/login/oauth/authorize?client_id="+client_id+"&redirect_uri="+redirect_uri+"&scope=user:email"
     
      window.location.href=gitapi
    },  
  } 
}
</script>

<style lang="scss" scoped>
*{ text-transform: none !important; }
</style>