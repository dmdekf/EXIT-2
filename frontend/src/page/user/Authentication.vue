<template>
  <div class="user"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>이메일 인증</h1>
                <h3>{{text}}</h3>
                <button class="btn" v-on:click="login">
                    <span>
                        로그인하기
                    </span>
                </button>
                <button class="btn" v-on:click="moveList">
                    <span>
                        메인화면
                    </span>
                </button>
            </div>
        </div>
  </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
export default {
    data: function(){
        return {
            text:"",
            uid:"",
            userkey:""
        };
    },
    methods: {
        moveList(){
                this.$router.push("/");
            },
            login(){

            }
            
    },
    created() {
        this.uid = this.$route.query.uid;
        this.userkey = this.$route.query.userkey;
        //(this.uid);
        //(this.userkey);
        axios({
            method:"get",
            url:SERVER.URL+'/account/key_alter?uid='+this.uid+'&userkey='+this.userkey,

        }).then((res)=>{
            if(res.data.status){
                this.text = "이메일 인증이 완료되었습니다. 정상적으로 서비스 이용이 가능합니다."
            }else{
                this.text = "이메일 인증에 실패하였습니다. 다시 시도해주세요.";
            }
        })
    },
}
</script>

<style>

</style>