<template>
  <div>
      <h1>git code</h1>
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
        })
        .catch((err) => {
            console.log(err)
        })
        .then(()=> {
          axios({
            method:"GET",
            url:"https://api.github.com/user/public_emails",
            headers:{
              "Authorization" : 'token '+this.token
            }
              })
            .then((res) => {
                console.log(res.data);
            })
            .catch(function(err) {
                console.log(err)
            })
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
        // this.$router.push({name:"MAIN"})
  
}
</script>


<style>

</style>