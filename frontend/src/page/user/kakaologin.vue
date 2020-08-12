<template>
  <div>
      <h1 class="display-1">Sosial Kakao Logging in...</h1>
  </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
export default {
    name:"Kakao",
    data: () => {
        return {
            code:"",
            token:"",
            refresh_token:""
        }
    },
    methods:{
      ...mapActions(['sociallogin']),
      postcode() {
        const client_id="b360d9e7af9bd0e8769148f4b9d5af1b"
        const redirect_uri="http://localhost:3000/user/logintest/kakao/callback"
        axios({
          method:"POST",
          url:'https://kauth.kakao.com/oauth/token?',
          headers: {'content-type':'application/x-www-form-urlencoded; charset=utf-8'},
          params: {"grant_type":"authorization_code",'client_id': client_id, 'redirect_uri': redirect_uri,'code':this.code},
        })
        .then((res) => {
            console.log(res.data.access_token)
          this.token=res.data.access_token
          this.refresh_token=res.data.refresh_token
          console.log(this.token)
          axios({
            method:"GET",
            url:"https://kapi.kakao.com/v2/user/me",
            headers:{
              "Authorization":"Bearer " + this.token
            },
              })
              console.log("DDDD"+this.token)
          .then((res)=> {
              console.log(res)
            // var email = res.data[0].email
            // this.sociallogin(email)
          .catch(function(err) {
          //백단 서버에 api 로 토큰과 이메일 데이터를 넘겨주고 로그인 된 페이지로 이동하기.
            console.log(err.data)
        })
        })
        })
        .catch(function(err) {
          //백단 서버에 api 로 토큰과 이메일 데이터를 넘겨주고 로그인 된 페이지로 이동하기.
            console.log(err.data)
        })
      }
    },
    created() {
      this.code = this.$route.query.code
      console.log(this.code)
    },
    beforeMount() {
      this.postcode()
    },
}
</script>