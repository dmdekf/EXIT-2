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
            axios({
                method:"get",
                url:SERVER.URL+"/user/detail/"+this.$store.state.login_user,
            }).then((res)=>{
                if (res.data.status){
                    this.email = res.data.object.email
                    this.nickName = res.data.object.uid
                }
            })
        },
}
</script>

<style>
.middle {
    margin-top: 20px;
}
</style>