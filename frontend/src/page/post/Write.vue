
<template>
  <div id="app">
    <v-app id="inspire">
      <v-main>
        <v-container class="fill-height" fluid>
          <v-row align="center" justify="center">
            <v-col cols="12" sm="8" md="8">
              <v-card class="elevation-12">
                <v-toolbar color="primary" dark flat>
                  <v-toolbar-title>글 작성</v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form ref="form">
                    <v-text-field
                      v-model="subject"
                      id="subject"
                      label="제목을 입력해주세요"
                      name="subject"
                      type="text"
                      :counter="10"
                      required
                    ></v-text-field>
                    <v-textarea
                      v-model="content"
                      id="content"
                      label="내용을 입력해주세요"
                      required
                      name="content"
                      type="text"
                    ></v-textarea>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" v-on:click="writePost">글올리기</v-btn>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                </v-card-actions>
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
const storage = window.sessionStorage;

export default {
  components: {},
  created() {},
  methods: {
    moveList() {
      this.$router.push("/");
    },
    writePost() {
    this.email=storage.getItem("user_email");
    this.uid=storage.getItem("login_user");
      axios({
        method: "post",
        url: SERVER.URL+"/feature/board/write/",
        data: {
          subject:this.subject,
          content:this.content,
          email:this.email,
          uid:this.uid
        },
      })
        .then((res) => { 
          
          alert("글쓰기 성공~");
          this.$router.push("/");

          if (res.data.status) {
            this.$router.push("/");
          }
        })
        .catch((err) => console.log(err.response.data));
    },
  },

  watch: {},
  data: () => {
    return {
      alert: true,
      subject: '',
      content: '',
      rules: {
        nameRules: [
          (v) => !!v || "Name is required",
          (v) =>
            (v && v.length <= 10) || "Name must be less than 10 characters",
        ],
        emailRules: [
          (v) => !!v || "E-mail is required",
          (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
        ],
        email:'',
        hit:'',
        uid:''
      },
    };
  },
  computed: {
  },
};
</script>
