<template>
<v-app id="inspire">
    <v-main class="mx-10px"
>
    <div class="post">
            <section class="post-list" >
            <div v-for="(post, uid) in list" :key="uid">
                <div class="post-card" v-if="post.id" v-on:click="showDetail(post.id)" >
                    <a style="color: black">
                        <img :src="getcolor(post.id)" class="post-img"/>
                        <div class="contents">
                        <v-row>
                            <v-col>
                                <v-icon>mdi-account-edit-outline</v-icon>  {{post.uid}}
                            </v-col>
                            <v-col cols="auto">
                            <v-btn color="#DC143C" fab x-small dark>
                                <v-icon>mdi-heart</v-icon>{{post.lnt}}
                            </v-btn>
                            </v-col>
                        </v-row>
                            <h3>#{{post.id}}</h3>
                            <h3>
                                {{post.subject}}
                            </h3>
                            <hr/> 
                            <p class="content">{{post.content}}</p>
                            <span class="date">{{post.created}}</span>  <br/>
                            <span class="comment"><v-icon>mdi-comment-multiple-outline</v-icon>  {{post.cnt}}</span>
                        </div> 
                    </a>
                </div>
            </div>
            <div class="tag-list-wrap justify-center">
            <v-btn class="mx-8 my-2" v-on:click="scrollToTop" color="#ffb367"><v-icon>mdi-arrow-collapse-up</v-icon></v-btn>
                <hr/>
                <div class="tag-list">
                    <v-btn class="mx-2 mt-1" dark color="indigo" v-on:click="writePost">
                        <v-icon dark>mdi-pencil</v-icon>
                        글 올리기
                    </v-btn>
                </div>
            </div>
            <infinite-loading @infinite="infiniteHandler" slot="append" spinner="waveDots"></infinite-loading>
            </section>
        </div>
    </v-main>
  </v-app>
</template>
 
<script>
import InfiniteLoading from 'vue-infinite-loading';
import '../../assets/css/post.scss';
import axios from "axios";
import SERVER from "@/api/api";

export default {
    name:"MAIN",
    data: () => {
        return {
            posts:[],
            list:[],
            photos: [],
            limit:0,
        }
    },
    components:{
        InfiniteLoading,
    },
    mounted(){
        this.getPosts()
    },
    
    methods: {
        getPosts() {
            this.nickName = this.$store.state.login_user;
            axios.get(SERVER.URL+"/feature/board/list/")
            .then((res)=>{
                    if(res.data) {
                        this.posts = res.data}
            })
            .catch((err) => console.error(err));
        },
        showDetail(id){
            axios
                .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+id)
                .then((res) => {
                    this.$router.push(`/post/detail/${id}`);
                })
                .catch((err) => console.error(err));
        },
        writePost(){
            this.$router.push("/post/write");
        },
        getPhotos: function () {
        axios
            .get("https://jsonplaceholder.typicode.com/photos")
            .then((res) => {
            this.photos = [...this.photos, ...res.data];
            })
            .catch((err) => console.error(err));
        },
        getcolor(postnum) {
            let result = this.photos[postnum+3].thumbnailUrl
            return result
        },
        scrollToTop: function () {
        scroll(0, 0);
        },
        infiniteHandler($state) {
            setTimeout(() => {
                if(this.posts[this.limit]) {
                    this.list = this.list.concat(this.posts[this.limit])
                    $state.loaded();
                    this.limit+=1
                } else {
                    $state.complete();
                }
            }, 500 )
            this.getPhotos();
        },
    },
}
</script>

<style scoped>
.tag-list-wrap[data-v-fbaed234] {
    position: fixed;
    width: max-content;
    align-items: center;
    padding: 0;
}
.post .post-list {
    min-height: 500px;
    width: 80vm;
    align-items: left;
}
</style>