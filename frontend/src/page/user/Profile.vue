<template>
  <div id="app">
  <v-app id="inspire">
    <v-card class="mx-auto mb-14"
    >
    <v-container>
    <v-row>
        <v-col cos="12">
        <v-img src="https://picsum.photos/400/500" >
        <v-row class="fill-height">
          <v-card-title class="text_border white--text pl-12 pt-15 text-right ma-5">
            <div class="display-1 pl-12 pt-15 mt-5">{{uid}}</div>
          </v-card-title>
        </v-row>
      </v-img>
    <v-list>
        <v-list-item-icon justify="center">
            <v-icon color="indigo" class="mr-9">mdi-newspaper-variant-multiple-outline</v-icon>
            작성한 글 목록
        </v-list-item-icon>
        <v-divider></v-divider>
            <v-list-item v-for="(post, idx) in posts" 
                
                @click="showDetail(post.id)"
                :key="idx">
            <v-list-item-content>
                <v-list-item-title class="mb-2">#{{idx+1}}. {{post.subject}}</v-list-item-title>
                <v-list-item-subtitle>{{ moment(post.created).locale('ko-kr').format("LLLL")}}</v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-icon >
                <v-icon color="#DC143C" small>mdi-heart</v-icon>: {{post.lnt}}  
            </v-list-item-icon>
            <v-list-item-icon>
                <v-icon color="black" small>mdi-comment-multiple-outline</v-icon><span>: {{post.cnt}}  </span>
            </v-list-item-icon>            
            </v-list-item>  
            <v-divider
        ></v-divider>
      </v-list>
    </v-col>
    </v-row>
    </v-container>
    </v-card>
  </v-app>
</div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import router from "@/router";
import SERVER from "@/api/api";
export default {
    name:"Profile",
    props:{
    uid:{
        type:String,
        required:true,
    },
    },
    data:() => {
        return {
            posts:[],
            searchData: { selected: "user",word :""},
            randomImage:""
        }
    },
    methods: {
        sendData() {
            this.searchData.word = this.uid
            axios
                .get(
                SERVER.URL +
                    SERVER.ROUTES.searchpost +
                    this.searchData.selected +
                    "/" +
                this.searchData.word)
                .then((res) => {
                if (res.data.object.length==0) {
                    this.posts = ''
                } else {    
                    this.posts = res.data.object
                }
                })
                .catch((err) => console.error(err));
        },
        showDetail(id){
            this.$router.push(`/post/detail/${id}`);
        },
    },
    created() {
        this.searchData.word = this.uid
            axios
                .get(
                SERVER.URL +
                    SERVER.ROUTES.searchpost +
                    this.searchData.selected +
                    "/" +
                this.searchData.word)
                .then((res) => {
                if (res.data.object.length==0) {
                    this.posts = ''
                } else {    
                    this.posts = res.data.object
                }
                })
                .catch((err) => console.error(err));
    }
}
</script>
<style>
.text_border {
	text-shadow: 0 0 1px black;
}
</style>
