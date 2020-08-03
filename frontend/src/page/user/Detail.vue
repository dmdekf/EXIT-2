<template>
  <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원정보</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <p>닉네임: {{nickName}}</p>
                        <p>이메일: {{email}}</p>
                    </div>

                </div>

                <button class="btn" v-on:click="moveUpdate"> 
                    <span>
                        정보수정
                    </span>
                </button>
                <button class="btn" v-on:click="moveList">
                    <span>
                        메인화면
                    </span>
                </button>
                <button class="btn" v-on:click="moveDelete">
                    <span>
                        탈퇴
                    </span>
                </button>
            </div>


        </div> 
        

    </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
const storage = window.sessionStorage;

export default {
    
    data: () => {
            return {
                email: '',
                nickName: '',
            }
        },
        methods: {
            moveList(){
                this.$router.push("/");
            },
            moveUpdate(){
                this.$router.push("/user/update");
            },moveDelete(){
                this.$router.push("/user/delete");
            },
            
        },
        created() {
            this.nickName = storage.getItem("login_user");
            this.email = storage.getItem("user_email");
            
            axios({
                method:"get",
                url:SERVER.URL+"/user/detail?uid="+this.nickName,
            }).then((res)=>{
                if(res.data.status){
                    this.password = res.data.object.password;
                }else{

                }
            })
        },
}
</script>

<style>

</style>