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
                    name="email"
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
                <v-btn color="primary" v-on:click="login">로그인</v-btn>
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
import axios from "axios";
// 토큰 및 사용자 정보를 저장하기 위해서 세션 스토리지를 사용한다.
const storage = window.sessionStorage;

const ai = axios.create({
  baseURL: "http://localhost:8080/"
});

export default {
  data() {
    return {
      email: "",
      password: "",
      message: "로그인해주세요.",
      status: "",
      token: "",
      info: ""
    };
  },
  methods: {
    setInfo(status, token, info) {
      this.status = status;
      this.token = token;
      this.info = info;
    },
    getInfo() {
      ai.post(
        "user/info",
        {
          email: "some@email.com",
          password: "some password"
        },
        {
          headers: {
            "jwt-auth-token": storage.getItem("jwt-auth-token")
          }
        }
      )
        .then(res => {
          this.setInfo(
            "정보 조회 성공",
            res.headers.auth_token,
            JSON.stringify(res.data)
          );
        })
        .catch(e => {
          this.setInfo("정보 조회 실패", "", e.response.data.msg);
        });
    },
    moveJoin(){
      this.$router.push("/user/jointest");
    },
    login() {
      storage.setItem("jwt-auth-token", "");
      storage.setItem("login_user", "");
      storage.setItem("user_email","");
      ai.post("/user/signin", {
        email: this.email,
        password: this.password
      })
        .then(res => {
          if (res.data.status) {
            this.message = res.data.data.email + "로 로그인 되었습니다.";
            console.dir(res.headers["jwt-auth-token"]);
            this.setInfo(
              "성공",
              res.headers["jwt-auth-token"],
              JSON.stringify(res.data.data)
            );
            storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
            storage.setItem("login_user", res.data.data.uid);
            storage.setItem("user_email",res.data.data.email);
            console.log(storage);
            alert(this.message);
            this.$router.push("/");
          } else {
            this.setInfo("", "", "");
            this.message = "로그인해주세요.";
            alert("입력 정보를 확인하세요.");
          }
        })
        .catch(e => {
          this.setInfo("실패", "", JSON.stringify(e.response || e.message));
        });
    },

    init() {
      if (storage.getItem("jwt-auth-token")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다.";
      } else {
        storage.setItem("jwt-auth-token", "");
      }
    } // init
  },
  mounted() {
    this.init();
  }
};
</script>