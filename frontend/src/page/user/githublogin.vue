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
            Github Logging in...
          </div>
          </v-col>
          <v-col cols="6">
            <v-progress-linear
              color="grey lighten-2"
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

<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
export default {
    name:"Github",
    data: () => {
        return {
            code:"",
            token:""
        }
    },
    methods:{
      ...mapActions(['sociallogin']),
      postcode() {
        const client_id=process.env.VUE_APP_GIT
        const client_secret=process.env.VUE_APP_GITSECERET
        axios({
          method:"POST",
          url:'https://github.com/login/oauth/access_token?',
          headers: {'Accept': 'application/json'},
          params: {'client_id': client_id, 'client_secret': client_secret,'code':this.code},
        })
        .then((res) => {
          this.token=res.data.access_token
          axios({
            method:"GET",
            url:"https://cors-anywhere.herokuapp.com/https://api.github.com/user/public_emails",
            headers:{
              "Authorization" : 'token '+this.token
            }
              })
          .then((res)=> {
            const email = res.data[0].email
            this.sociallogin(email)
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
          })
        },        
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