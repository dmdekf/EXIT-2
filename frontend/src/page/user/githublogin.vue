<template>
  <div>
      <h1>Github Logging in...</h1>
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
        const client_id="16f1cfdb3ceb66705b57"
        const client_secret="3bf8ef26e7773919295eff93163d2ec9ccf9342a"
        axios({
          method:"POST",
          url:'https://github.com/login/oauth/access_token?',
          headers: {'Accept': 'application/json'},
          params: {'client_id': client_id, 'client_secret': client_secret,'code':this.code},
        })
        .then((res) => {
          console.log("token:" + res.data.access_token)
          this.token=res.data.access_token
          axios({
            method:"GET",
            url:"https://api.github.com/user/public_emails",
            headers:{
              "Authorization" : 'token '+this.token
            }
              })
          .then((res)=> {
            console.log(res.data[0].email)
            var email = res.data[0].email
            console.log(email)
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