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
            token:"",
            loginData:{
              email:"",
              auth_token:"",
            }
        }
    },
    methods:{
      ...mapActions(['sociallogin']),
      postcode() {
        const client_id=process.env.VUE_APP_GIT
        const client_secret=process.env.VUE_APP_GITSECERET
        axios({
          method:"POST",
          url:'https://cors-anywhere.herokuapp.com/https://github.com/login/oauth/access_token?',
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
            this.loginData.email = res.data[0].email
            this.loginData.auth_token=this.token
            this.sociallogin(this.loginData)
          })
          .catch(function (error) {
          if (error.response) {
          }
          else if (error.request) {
            alert(error.request+ "입력 정보를 확인하세요.");
          }
          else {
            alert(error.message+ "입력 정보를 확인하세요.");
          }
        })
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