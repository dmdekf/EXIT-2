<template>
  <div>
    <v-app id="inspire">
    <v-card
      class="mx-auto my-10"
      max-width="1000px"
    >
      <v-container style="height: 600px;">
        <v-row
          class="fill-height"
          align-content="center"
          justify="center"
        >
          <v-col
            class="subtitle-1 text-center mb-5"
            cols="12"
          >
          <div class="text-h2"> 
            Kakao Logging in...
          </div>
          </v-col>
          <v-col cols="6">
            <v-progress-linear
              color="yellow accent-4"
              indeterminate
              rounded
              height="6"
            ></v-progress-linear>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
  </div>
</template>

<script >
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
export default {
    name:"Kakao",
    data: () => {
        return {
            code:"",
            token:"",
            refresh_token:"",
            loginData:{
              email:"",
              auth_token:"",
            }
        }
    },
    methods:{
      ...mapActions(['sociallogin']),
      postcode() {
        const client_id=process.env.VUE_APP_KAKAO
        const redirect_uri="http://i3a501.p.ssafy.io/user/logintest/kakao/callback"
        // const redirect_uri="http://localhost:3000/user/logintest/kakao/callback"
        axios({
          method:"POST",
          url:'https://cors-anywhere.herokuapp.com/https://kauth.kakao.com/oauth/token?',
          headers: {'content-type':'application/x-www-form-urlencoded; charset=utf-8'},
          params: {"grant_type":"authorization_code",'client_id': client_id, 'redirect_uri': redirect_uri,'code':this.code},
        })
        .then(async (res) => {
          console.log(res.data.access_token)
          this.token=res.data.access_token
          this.refresh_token=res.data.refresh_token
          if (this.token) {
          await axios({
            method:"GET",
            url:"https://cors-anywhere.herokuapp.com/https://kapi.kakao.com/v2/user/me",
            headers:{
              "Authorization":'bearer ' + this.token,
              "Content-type": 'application/x-www-form-urlencoded;charset=utf-8'
            },
          })
          .then((res)=> {
            console.log(res.data.kakao_account.email)
            this.loginData.email='+'+res.data.kakao_account.email
            this.loginData.auth_token=this.token
            console.log(this.loginData.email)
            this.sociallogin(this.loginData)
          })
          .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          }
          else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
            alert(error.request+ "입력 정보를 확인하세요.");
          }
          else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log('Error', );
            alert(error.message+ "입력 정보를 확인하세요.");
          }
        })
          }
        })
        },        
      },
    created() {
      this.code = this.$route.query.code
    },
    beforeMount() {
      this.postcode()
    },
}
</script>