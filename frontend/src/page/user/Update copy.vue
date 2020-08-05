<template>
  <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원정보</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <input v-model="nickName"
                            id="nickname" type="text" readonly/>
                    </div>

                    <div class="input-wrap">
                        <input v-model="email" 
                            id="email"
                            type="text" readonly/>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="password" 
                            id="password" 
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"/>
                        <span :class="{active : passwordType==='text'}">
                                <i class="fas fa-eye"></i>
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="passwordConfirm" 
                            id="password-confirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"/>
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i class="fas fa-eye"></i> 
                            </span>
                    </div>
                    <div class="input-wrap password-wrap">
                        <input  
                            id="profile"
                            type="file"
                            placeholder="프로필 사진을 등록해주세요"/>
                    </div>
                    <div class="input-wrap password-wrap">
                        <textarea id="introduce" placeholder="자기소개를 등록해주세요" >
                        </textarea>
                    </div>
                </div>

                <button class="btn" v-on:click="userUpdate"> 
                    <span>
                        수정하기
                    </span>
                </button>
                <button class="btn" v-on:click="moveDelete"> 
                    <span>
                        회원탈퇴
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
const storage = window.sessionStorage;
console.log(storage);
export default {
    data: () => {
            return {
                email: '',
                nickName: '',
                password: '',
                passwordConfirm: '',
                passwordType:"password",
                passwordConfirmType:"password",
            }
        },
        created() {
            this.nickName = storage.getItem("login_user");
            this.email = storage.getItem("user_email");
            axios({
                method:"get",
                url:SERVER.URL+"/user/update?uid="+this.nickName,
            }).then((res)=>{
                if(res.data.status){
                    console.log(res.data);
                    this.email = res.data.object.email;
                }else{

                }
            })
        },
        methods: {
            moveList(){
                this.$router.push("/");
            },
            moveDelete(){
                this.$router.push("/user/delete");
            },
            userUpdate(){
                axios({
                    method:"put",
                    url:SERVER.URL+"/user/update",
                    data:{
                        id:this.id,
                        email:this.email,
                        password:this.password,
                        uid:this.nickName
                    }
                }).then((res)=>{
                    if(res.data.status){
                        alert("수정이 완료되었습니다.");
                        storage.setItem("jwt-auth-token","");
                        storage.setItem("login_user","");
                        stotage.setItem("user_email","");
                        this.$router.push("/user/logintest");
                    }else{

                    }
                })
            }
        },
}
</script>

<style>

</style>