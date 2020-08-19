<template>
  <div>
    <v-container fluid >
      <v-row align="center">
        <v-col cols="12" sm="6" id="select">
          <v-select
            dense
            solo
            v-model="searchData.selected"
            :items="selects"
            label="Select"
            chips
            hint="검색 기준을 선택해 보세요"
            persistent-hint
          ></v-select>
        </v-col>
      </v-row>
    </v-container>
    <div>
      <v-text-field
        class="mx-8"
        outlined
        label="검색어를 입력하세요"
        :rules="searchrules"
        hide-details="auto"
        v-model="searchData.word"
        clearable
        prepend-inner-icon="mdi-magnify"
        @keypress.enter="sendData()"
      ></v-text-field>
    </div>
    <v-container fluid >
      <v-row align="center">
        <v-col v-show="posts.length" cols="12" class="mx-8" >
          <SearchList :posts="posts"/>
          <v-card 
            class="mx-auto mt-12"
          >
            <v-toolbar
              color="#5C6BC0"
              dark
            >
            검색 결과
            </v-toolbar>
            <v-list three-line >
              <!-- 누나 여기 키 뭐로해야하지?.? for문이 틀렸다고해서 대충 틀은 잡아놨는데 실수한걸까봐-->
              <v-list-item
                v-for="(post,id) in posts" 
                ripple
                @click="showDetail(post.id)"
                :key="id"
              >
                <v-list-item-content>
                <span
                  class="text-uppercase font-weight-large caption"
                  v-text="post.subject"
                ></span>
                <!-- <div v-text="post.content"></div> -->
                <v-divider ></v-divider>  
                <small>
                <div> 글쓴이 :<span>{{post.uid}}</span></div>
                </small>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card> 
        </v-col>
        <v-col v-show="!posts" cols="12" class="mx-8" >
          <v-card 
            class="mx-auto mt-12"
          >
            <v-toolbar
              color="#5C6BC0"
              dark
            >
            검색 결과
            </v-toolbar>
            <v-list three-line >
              <!-- 누나 여기 키 뭐로해야하지?.? for문이 틀렸다고해서 대충 틀은 잡아놨는데 실수한걸까봐-->
              <v-list-item
              >
                <v-list-item-content>
                조건에 맞는 글이 없습니다.
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card> 
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import { required, rules, valid } from "vuelidate/lib/validators";
import router from "@/router";
import SERVER from "@/api/api";
export default {
  name: "Search",
  data() {
    return {
      posts:[],
      selects: ["all", "user", "title", "content", "tag"],
      searchData: { selected: "", word: "" },
      rules:{
      searchrules: [
        (value) => !!value || "Required.",
        (value) => (value && value.length >= 2) || "2글자 이상 입력해주세요",
      ],}
    };
  },
  methods: {
    showDetail(id){
      axios
        .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+id)
        .then((res) => {
            this.$router.push(`/post/detail/${id}`);
        })
        .catch((err) => console.error(err));
    },
    sendData() {
      axios
        .get(
          SERVER.URL +
            SERVER.ROUTES.searchpost +
            this.searchData.selected +
            "/" +
          this.searchData.word)
        .then((res) => {
          console.log(res.data)
          if (res.data.object.length==0) {
            this.posts = ''
            console.log(this.posts)
          } else {    
            this.posts = res.data.object
            console.log(this.posts)
          }
        })
        .catch((err) => console.error(err));
    },
  },
};
</script>
<style>
#select {
  margin-left: 20px;
}
</style>
