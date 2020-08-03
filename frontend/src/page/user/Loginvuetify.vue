<template>
  <v-app id="inspire">
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>로그인</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="email"
                    id="email"
                    label="이메일을 입력해주세요"
                    name="login"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    v-model="password"
                    label="비밀번호"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" v-on:click="userLogin">로그인</v-btn>
                <v-btn color="primary" v-on:click="moveJoin">회원가입</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";
import axios from "axios";
import SERVER from "@/api/api";
const storage = window.sessionStorage;

export default {
  components: {},
  created() {},
  watch: {},
  methods: {
    moveList() {
    },
    moveJoin(){
      this.$router.push("/user/jointest");
    },
    userLogin() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      storage.setItem("user_email", "");
      axios({
        method: "get",
        url:
          SERVER.URL+"/account/login?email=" +
          this.email +
          "&password=" +
          this.password,
      })
        .then((res) => {
          console.dir(res.headers["jwt-auth-token"])
          if (res.data.status) {
            storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
            storage.setItem("login_user", res.data.object.uid);
            storage.setItem("user_email", res.data.object.email);

            alert("로그인에 성공했습니다.");
             this.$router.push("/");
          } else {
            alert("로그인에 실패했습니다.");
          }
          this.$router.push({name: constants.URL_TYPE.POST.MAIN })
        })
        .catch((e) => {
          alert("로그인에 실패했습니다.");
          this.moveList();
        });
    },
     
  },
  data: () => {
    return {
      constants,
      email: "",
      password: "",
    };
  },
};
</script>


