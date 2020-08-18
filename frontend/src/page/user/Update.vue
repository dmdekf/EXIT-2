<template>
    <div class="user" id="join">
        <div class="wrapC table">
            <div class="middle">
                <h1 class="display-2">회원정보</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <input v-model="nickName" id="nickname" type="text" readonly="readonly"/>
                    </div>

                    <div class="input-wrap">
                        <input v-model="email" id="email" type="text" readonly="readonly"/>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input
                            v-model="password"
                            id="password"
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"/>
                        <span :class="{active : passwordType==='text'}">
                            <i class="fas fa-eye"></i>
                        </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input
                            v-model="passwordConfirm"
                            id="password-confirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"/>
                        <span :class="{active : passwordConfirmType==='text'}">
                            <i class="fas fa-eye"></i>
                        </span>
                    </div>
                    <div class="input-wrap password-wrap">
                        <input id="profile" type="file" placeholder="프로필 사진을 등록해주세요"/>
                    </div>
                    <div class="input-wrap">
                        <textarea id="introduce" placeholder="자기소개를 등록해주세요"></textarea>
                    </div>
                    <div>
                        <p>뭐든</p>
                        <img :src="profileUrl">
                    </div> 
                </div>
                <div justify="space-between" class="ma-2">
                  <v-btn color="primary" v-on:click="userUpdate">수정하기</v-btn>
                  <v-btn color="red" v-on:click="moveDelete">회원탈퇴</v-btn>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                </div>
            </div>

        </div>

    </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex';

export default {
    data: () => {
        return {
            email: '',
            nickName: '',
            password: '',
            passwordConfirm: '',
            passwordType: "password",
            passwordConfirmType: "password",
            profileUrl:""
        }
    },
    created() {
        this.nickName = this.$store.state.login_user;
        this.email = this.$store.state.user_email;
        axios({
            method: "get",
            url: SERVER.URL + "/user/update?uid=" + this.nickName
        }).then((res) => {
            if (res.data.status) {
                console.log(res.data);
                this.email = res.data.object.email;
            } else {}
        })
        axios({
            method : "post",
            url :  SERVER.URL + "/getImg/"+this.nickName,
        }).then((res)=>{
            if(res.data.status){
                //this.camera_off_img = require("../../assets/images/camera_off.png")
                this.profileUrl =require('../../assets/img/pimg/'+res.data.object.uimage);
                console.log(this.profileUrl)
                
            }
        })
    },

    methods: {
        ...mapActions(['showAlert']),
        ...mapActions(['logout']),
        moveList() {
            this
                .$router
                .push("/");
        },
        moveDelete() {
            this
                .$router
                .push("/user/delete");
        },
        userUpdate() {
            axios({
                method: "put",
                url: SERVER.URL + "/user/update",
                data: {
                    id: this.id,
                    email: this.email,
                    password: this.password,
                    uid: this.nickName
                }
            }).then((res) => {
                if (res.data.status) {
                    this.showAlert(6)
                    this.logout()
                    this
                        .$router
                        .push("/user/logintest");
                } else {
                    this.showAlert(1)
                }
            })
        }
    }
}
</script>

<style></style>