<template>
  <div id="app">
    <v-app id="inspire">
      <v-main>
        <v-container class="fill-height" fluid>
          <v-row align="center" justify="center">
            <v-col cols="8">
              <v-card class="elevation-12">
                <v-toolbar color="deep-purple accent-3" dark flat>
                  <v-toolbar-title>회원 정보 수정</v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form ref="form" lazy-validation v-model="valid">
                    <v-text-field
                        prepend-icon="mdi-emoticon-cool-outline"
                        v-model="nickName" id="nickname"
                        label="nickName : 수정 불가"
                        outlined
                        readonly
                        dense
                        type="text"
                    ></v-text-field>
                    <v-text-field
                        prepend-icon="mdi-email"
                         v-model="email" id="email" type="text"
                        label="email : 수정 불가"
                        outlined
                        readonly
                        dense
                    ></v-text-field>
                   
                    <v-text-field
                      v-model="password"
                      id="password" 
                      append-icon="mdi-eye"
                      counter
                      required
                      :rules="passwordRules"
                      :type="show ? 'text' : 'password'"
                      name="password"
                      label="비밀번호를 변경합니다"
                      hint="숫자 혹은 특수기호 포함 8글자 이상"
                      @click:append="show = !show"
                    ></v-text-field>

                    <v-text-field
                      v-model="passwordConfirm"
                      :rules="[passwordConfirmationRule]"    
                      required
                      :type="show1 ? 'text' : 'password'"
                      name="input-10-1"
                      label="비밀번호를 다시 입력해 주세요"
                      hint="숫자 혹은 특수기호 포함 8글자 이상"
                      counter
                    ></v-text-field>
                    <!-- <v-file-input 
                      show-size
                      :rules="[(v) => (!v || v.size < 3500000) || '이미지 크기는 3.5MB 이하여야합니다.']"
                      id="profile"
                      accept="image/png, image/jpeg, image/bmp"
                      placeholder="프로필 사진을 변경할 수 있습니다."
                      prepend-icon="mdi-camera"
                      label="프로필 사진"
                      :value="profileUrl"
                    ></v-file-input> -->
                    <input
                      id = "file-selector"
                      ref="file"
                      type="file"
                      @change="handleFileUpload()"
                    />
                    <v-btn @click="uploadFile" color="primary" text> 업로드</v-btn>
                    <v-textarea
                      id="introduce"
                      clearable
                      clear-icon="x"
                      auto-grow
                      required=false
                      label="자기 소개를 입력해 주세요"
                    ></v-textarea>
                    <v-container fluid :v-show="profileUrl" max-height="400">
                    <v-row justify="center" align="center" max-height="400">
                    <v-col cols="8" aspect-ratio="2" contain  align="center" justify="center" max-height="400">
                      
                      <v-img  :src="url" aspect-ratio="2" max-width="200" max-height="400">
                      </v-img>
                    </v-col>
                  </v-row>
                  </v-container>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" :disabled="!valid" v-on:click="userUpdate">수정하기</v-btn>
                  <v-btn color="red" v-on:click="moveDelete">회원탈퇴</v-btn>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                </v-card-actions>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
import { required, rules, valid } from "vuelidate/lib/validators";
export default {
    data: () => {
        return {
          albumBucketName :"photo-album-two",
          bucketRegion : "ap-northeast-2",
          IdentityPoolId : "ap-northeast-2:242f0477-ae48-4591-9ee8-e628cfcfd999",
          file : null,


            valid:true,
            email: '',
            nickName: '',
            password: '',
            passwordConfirm: '',
            passwordType: "password",
            passwordConfirmType: "password",
            profileUrl:"",
            url: null,
            image: null,
            show: false,
            show1: false,
            isTerm: false,
            passwordRules: [
                (v) => !!v || '값을 입력해 주세요',
                (v) => (v && v.length >= 7) || '8글자 이상 입력해주세요',
              ],
        }
    },
    created() {
        axios({
            method: "",
            url: SERVER.URL + "/user/detail/" + this.$store.state.login_user
        }).then((res) => {
            if (res.data.status) {
                //(res.data);
                this.email = res.data.object.email;
                this.nickName = res.data.object.uid
            } else {}
        })
        axios({
            method : "post",
            url :  SERVER.URL + "/getImg/"+this.nickName,
        }).then((res)=>{
            if(res.data.status){
                //this.camera_off_img = require("../../assets/images/camera_off.png")
                this.profileUrl =res.data.object.uimage;
                console.log(this.profileUrl)
                
            }
        })
    },

    methods: {
      handleFileUpload(){
      this.file = this.$refs.file.files[0]
      console.log('파일이 업로드 되엇습니다')
    },
    uploadFile(){
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: this.IdentityPoolId
        })
      })
      const s3 = new AWS.S3({
        apiVersion: '2006-03-01',
        params: {
            Bucket: this.albumBucketName
        }
      })
      let photoKey = this.file.name
      axios({
            method: "post",
            url: SERVER.URL + "/user/pimg",
            data: {
                    uid: this.nickName,
                    uimage : 'https://photo-album-two.s3.ap-northeast-2.amazonaws.com/'+photoKey
                }
      })
      s3.upload({
        Key: photoKey,
        Body: this.file,
        ACL: 'public-read'
      }, (err, data) => {
          if (err) {
              return alert('There was an error uploading your photo: ', err.message);
          }
          alert('Successfully uploaded photo.');
      });
    },
        ...mapActions(['showAlert']),
        ...mapActions(['logout']),
        Preview_image() {
      this.url= URL.createObjectURL(this.image)
      //(this.url)
    },
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
        validate () {
        this.$refs.form.validate()
      },
        userUpdate() {
            this.validate
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
    },
    computed: {
    passwordConfirmationRule() {
      return (
        this.password === this.passwordConfirm ||
        '동일한 비밀번호를 입력해 주세요.'
      );
    },
  },
};
</script>

<style></style> 