<template>
<v-app id="inspire">
    <v-main class="mx-10px"
>
    <div class="post">
            <section class="post-list" >
            <div v-for="(post, uid) in list" :key="uid">
                <div class="post-card" v-if="post.id" v-on:click="showDetail(post.id)" >
                    <a style="color: black">
                        <v-img :src="getImg(post.id)"></v-img>
                        
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
                            <p class="content" v-html="post.content">{{post.content}}</p>
                            <small class="date">{{ moment(post.created).locale('ko-kr').format("LLLL")}}</small>
                            
                            <div class="comment mt-1"><v-icon>mdi-comment-multiple-outline</v-icon>  {{post.cnt}}</div>
                        </div> 
                    </a>
                </div>
            </div>
            <div class="tag-list-wrap justify-center">
            <v-btn class="mx-8 my-2" v-on:click="scrollToTop" color="#ffb367"><v-icon>mdi-arrow-collapse-up</v-icon></v-btn>
                <p>
                    <v-btn class="mx-2 mt-1" dark color="indigo" v-on:click="writePost">
                        <v-icon dark>mdi-pencil</v-icon>
                        글 올리기
                    </v-btn>
                </p>
                
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
            url:""
        }
    },
    components:{
        InfiniteLoading,
    },
    mounted(){
        this.getPosts()
    },
    methods: {
        getImg(postnum) {
            let result = "https://picsum.photos/id/"+(postnum+50)+"/300/200"
            console.log(result)
            if (typeof result == 'jpeg') {
                return result
            } else {
                postnum = postnum+10
                result = "https://picsum.photos/id/"+(postnum)+"/300/200"
                return result
            }
            
        },
        getPosts() {
            this.nickName = this.$store.state.login_user;
            axios.get(SERVER.URL+"/feature/board/list/")
            .then((res)=>{
                    if(res.data) {
                        this.posts = res.data;
                                              
                    }
            })
            .catch((err) => console.error(err));
        },
        showDetail(id){
            this.$router.push(`/post/detail/${id}`);
        },
        writePost(){
            this.$router.push("/post/write");
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
            this.getImg(this.limit)
        }
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