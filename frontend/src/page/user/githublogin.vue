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
            url:"https://api.github.com/user/public_emails",
            headers:{
              "Authorization" : 'token '+this.token
            }
              })
          .then((res)=> {
            var email = res.data[0].email
            this.sociallogin(email)
        })
        })
        .catch(function(err) {
          //백단 서버에 api 로 토큰과 이메일 데이터를 넘겨주고 로그인 된 페이지로 이동하기.
            console.log(err)
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