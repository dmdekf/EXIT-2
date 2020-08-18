<template>
  <div id="app">
      <h1>유저프로필{{uid}}</h1>
  <v-app id="inspire">
    <v-card
      max-width="100%"
      class="mx-auto"
    >
    <v-img :src=getcolor() >
    
    <!-- <v-img :src="post.uimg=='' ? getcolor(post.id) : require('@/assets/img/bimg/'+post.uimg)" class="post-img" height="300px"
        dark
      >/ -->
        <v-row class="fill-height">
          <v-card-title class="white--text pl-12 pt-12">
            <div class="display-1 pl-12 pt-12">{{uid}}</div>
          </v-card-title>
        </v-row>
      </v-img>
    <v-list one-line>
        <v-list-item-icon justify="center">
            <v-icon color="indigo">mdi-newspaper-variant-multiple-outline</v-icon>작성한 글 목록
        </v-list-item-icon>
        <v-divider inset></v-divider>\
            <v-list-item v-for="(post,id) in posts" 
                
                @click="showDetail(post.id)"
                :key="id">
            <v-list-item-content>
                <v-list-item-title>{{post.title}}</v-list-item-title>
                <v-list-item-subtitle>{{ moment(post.created).locale('ko-kr').format("LLLL")}}</v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-icon color="#DC143C" x-small dark >
                <v-icon >mdi-heart</v-icon>{{post.lnt}}
            </v-list-item-icon>
            <v-list-item-icon>
                <v-icon>mdi-comment-multiple-outline</v-icon><span>:{{post.cnt}}</span>
            </v-list-item-icon>
            </v-list-item>  
        <v-divider inset></v-divider>   
      </v-list>
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
        }
    },
    methods: {
        getcolor: function () {
        axios
            .get("https://jsonplaceholder.typicode.com/photos/6")
            .then((res) => {
                let result = res.data.thumbnailUrl
            return result
            })
            .catch((err) => console.error(err));
        },
        
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
    }
        
}
</script>

<style>

</style>