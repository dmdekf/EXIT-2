<template>
  <div>
      삭제중...
  </div>
</template>

<script>
import axios from 'axios';
import { mapActions } from 'vuex'
import SERVER from "@/api/api";
export default {
    created() {
        this.uid = this.$store.state.login_user
        console.log(this.uid);
        axios({
            method:"delete",
            url:SERVER.URL+"/user/delete/"+this.uid,

        }).then((res)=>{
            let msg = "삭제 중 문제가 발생했습니다.";
            if(res.data.status){
                msg = "삭제가 완료되었습니다.";
                this.logout()
                this.$router.push("/");
            }else{

            }
            alert(msg);
            this.$router.push("/");
        })
    },
    data: function(){
        return {
            uid:''
        };
    },
    methods: {
        ...mapActions(['logout'])
    },
}
</script>

<style>

</style>