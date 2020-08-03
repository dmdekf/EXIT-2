<template>
  <div>
    <v-container fluid >
      <v-row align="center">
        <v-col cols="12" sm="6">
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
            <v-card v-if="posts"
              class="mx-auto mt-12"
            >
            <v-system-bar>검색 결과</v-system-bar>
            <v-toolbar
              flat
              color="transparent"
            >
              
            </v-toolbar>

            <v-list three-line>
              <!-- 누나 여기 키 뭐로해야하지?.? for문이 틀렸다고해서 대충 틀은 잡아놨는데 실수한걸까봐-->
              <v-list-item
                v-for="(post,id) in posts" 
                ripple
                @click="showDetail(post.id)"
                :key="id"
              >
            <v-list-item-content>
            <span
              class="text-uppercase font-weight-regular caption"
              v-text="post.title"
            ></span>

            <div v-text="post.content"></div>
            <hr/>
            글쓴이 : <div v-text="post.uid"></div>
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

import router from "@/router";
import SERVER from "@/api/api";
export default {
  name: "Search",
  data() {
    return {
      posts:[],
      selects: ["all", "user", "title", "content", "tag"],
      searchData: { selected: "", word: "" },
      searchrules: [
        (value) => !!value || "Required.",
        (value) => (value && value.length >= 2) || "2글자 이상 입력해주세요",
      ],
    };
  },
  methods: {
    showDetail(id){
            axios
                .get(SERVER.URL+"/feature/board/list/detail/{id}?id="+id)
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
          console.log(typeof(res.data.object))
            this.posts = res.data.object
            console.log(this.posts[0].id)
            // this.posts = Object.keys(this.posts)
        })
        .catch((err) => console.error(err));
        },
       },
};
</script>
<style>
.v-text-field--outlined > .v-input__control {
    margin: 0 12px;
}
</style>
