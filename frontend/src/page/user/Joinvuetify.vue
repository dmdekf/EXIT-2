<template>
  <div id="app">
    <v-app id="inspire">
      <v-main>
        <v-container class="fill-height" fluid>
          <v-row align="center" justify="center">
            <v-col cols="12" sm="8" md="4">
              <v-card class="elevation-12">
                <v-toolbar color="primary" dark flat>
                  <v-toolbar-title>회원가입</v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form ref="form" v-model="valid" lazy-validation>
                    <v-text-field
                      v-model="nickName"
                      id="nickName"
                      label="닉네임을 입력해주세요"
                      name="nickName"
                      prepend-icon="mdi-emoticon-cool-outline"
                      type="text"
                      :counter="10"
                      :rules="nameRules"
                    ></v-text-field>
                    <v-text-field
                      v-model="email"
                      id="email"
                      label="이메일을 입력해주세요"
                      :rules="emailRules"
                      name="email"
                      prepend-icon="mdi-email"
                      type="text"
                    ></v-text-field>

                    <v-text-field
                      v-model="password"
                      :type="show ? 'text' : 'password'"
                      name="password"
                      label="비밀번호를 입력해 주세요"
                      hint="8글자 이상,숫자 혹은 특수기호 포함. "
                      counter
                      :rules="passwordRules"
                      @click:append="show = !show"
                    ></v-text-field>

                    <v-text-field
                      v-model="passwordConfirm"
                      :rules="[passwordConfirmationRule]"                       
                      :type="show1 ? 'text' : 'password'"
                      name="passwordConfirm"
                      label="비밀번호를 다시 입력해 주세요"
                      hint="8글자 이상,숫자 혹은 특수기호 포함."
                      counter
                      @click:append="show1 = !show1"
                    ></v-text-field>
                    <v-checkbox
                      v-model="checkbox"
                      :rules="[v => !!v || 'You must agree to continue!']"
                      label="약관에 동의하십니까?"
                      required
                    ></v-checkbox>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" :disabled="!valid" v-on:click="signup">회원가입</v-btn>
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
import "../../assets/css/user.scss";
import SERVER from "@/api/api";
import constants from "../../lib/constants";
import axios from "axios";
import { required, rules, valid } from "vuelidate/lib/validators";

export default {
  components: {},
  created() {},
  methods: {
    validate () {
        this.$refs.form.validate()
      },
    moveList() {
      this.$router.push("/");
    },
    signup() {
      axios({
        method: "post",
        url: SERVER.URL+"/account/signup",
        data: {
          email: this.email,
          password: this.password,
          nickname: this.nickname,
        },
      })
        .then((res) => {
          if (res.data.status) {
            alert("이메일 인증!!");
            this.$router.push("/user/signup");
          }
        })
        .catch((err) => console.log(err.response.data));
    },
  },
  data: () => {
    return {
      alert: true,
      show: false,
      show1: false,
      email: "",
      checkbox: false,
      nameRules: [
          (v) => !!v || '값을 입력해 주세요',
          (v) =>
            (v && v.length <= 10) || '닉네임은 10글자 이하여야 합니다.'],
      emailRules: [
        (v) => !!v || '값을 입력해 주세요',
        (v) => /.+@.+\..+/.test(v) || 'E-mail 형식을 맞춰주세요.',
      ],
      passwordRules: [
      (value) => !!value || '값을 입력해 주세요',
      (value) => (value && value.length >= 7) || '8글자 이상 입력해주세요'],
      valid:true,
      nickName: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
    }
  },
  computed: {
    passwordConfirmationRule() {
      return (
        this.password === this.passwordConfirm ||
        "동일한 비밀번호를 입력해 주세요."
      );
    },
  },
};
</script>
