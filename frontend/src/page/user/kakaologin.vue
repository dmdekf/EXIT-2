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
            refresh_token:""
        }
    },
    methods:{
      ...mapActions(['sociallogin']),
      postcode() {
        const client_id=process.env.VUE_APP_KAKAO
        const redirect_uri="http://i3a501.p.ssafy.io:3000/user/logintest/kakao/callback"
        axios({
          method:"POST",
          url:'https://kauth.kakao.com/oauth/token?',
          headers: {'content-type':'application/x-www-form-urlencoded; charset=utf-8'},
          params: {"grant_type":"authorization_code",'client_id': client_id, 'redirect_uri': redirect_uri,'code':this.code},
        })
        .then(async (res) => {
          console.log(res.data.access_token)
          this.token=res.data.access_token
          this.refresh_token=res.data.refresh_token
          console.log(this.token)
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
            var email = res.data.kakao_account.email
            this.sociallogin(email)
          })
          .catch(function(err) {
            console.log(err.response.data)
            alert(err.response.data)
          })
          }
        })
        .catch(function(err) {
            console.log(err.response.data)
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