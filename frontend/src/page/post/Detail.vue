<template>
<div id="app">
    <v-app id="inspire">
    <v-window>
        <v-window-item>
        <v-card flat>
        <v-card-title class="lime">
            <v-list-item>
            <v-list-item-content>
            <v-list-item-subtitle><small>#{{id}}번 글</small></v-list-item-subtitle>
            <v-list-item-title class="headline my-2">제목 :  {{ subject }}</v-list-item-title>
            <hr/>
            <v-row justify="space-around"  class="child-flex">
            <div class="ml-3"><small>작성날짜: {{ moment(created).locale('ko-kr').format("LLLL")}}</small></div>
                <div>
                    <div v-if="this.likestatus">
                    <v-btn icon color="#DC143C" v-on:click="likePost(id)" >
                        <v-icon>mdi-heart</v-icon>
                    </v-btn>
                    </div>
                    <div v-else>
                        <v-btn icon color="black" v-on:click="likePost(id)">
                            <v-icon>mdi-heart</v-icon>
                        </v-btn>
                    </div>
                </div>
                <div>
                    <div v-if="(this.uid)===this.$store.state.login_user">
                    <v-btn  v-on:click=updatePost(id)>
                        <v-icon>mdi-playlist-edit</v-icon>글 수정하기
                    </v-btn>
                    </div>
                    <div v-else>
                    <v-btn icon v:on="userdetail()">
                        <v-icon>mdi-account</v-icon>
                    </v-btn>
                    <small>{{uid}}</small>
                    </div>                
                </div>
            </v-row>
            </v-list-item-content>
            </v-list-item>
        </v-card-title>
        <v-card min-height="300">
        <v-card-text >
            <div v-html="content">{{content}}</div>
            
        </v-card-text>
        </v-card>
        <hr>
         <div v-for="(comment, idx) in comments" :key="idx">
             
                    <a style="color: black">
                        <div class="contents"> 
                        <v-row class="ml-3">
                            <v-col>
                                #{{comment.idx}}번 댓글 
                                {{ moment(comment.insertTime).locale('ko-kr').startOf('hour').fromNow()}}
                                <v-icon>mdi-account-edit-outline</v-icon>{{comment.writer}}<br>
                                {{comment.content}}
                            </v-col>
                        </v-row>
                            
                            <hr/> 
                        </div> 
                    </a>
            </div>
    </v-card>
    </v-window-item>
    </v-window>
</v-app>
</div>
</template>


<script>
import axios from 'axios';
import SERVER from "@/api/api";
export default {
    props:{
        id:{
            type:Number,
            required:true,
        },
    },
    data: () => {
            return {
                subject: '',
                content: '',
                created: '',
                likestatus:false,
                comments:[]
            }
        },
        mounted(){
            this.getComments();
        },
        methods: {
            moveList(){
                this.$router.push("/");
            },            
            likePost(postId){
                axios({
                    method: "GET",
                    url : SERVER.URL +"/like/"+postId+"/"+this.$store.state.login_user,                    
                }).then(
                        this.likestatus = !this.likestatus                        
                    )
            },            
            userdetail(){
                console.log(SERVER.URL);                
                axios({
                method:"get",
                url:SERVER.URL+"/user/detail/"+this.uid,
                    }).then((res)=>{
                        if(res.data.status){
                            this.password = res.data.object.password;
                        }else{
                        }
                    })
            },
            getComments(uid){
                axios({
                method:"get",
                url:SERVER.URL+"/feature/comment/detail/"+this.$store.state.login_user+"/"+this.id+"/comments",
                    }).then((res)=>{
                        if(res.data){
                            console.log(res.data);
                            this.comments = res.data;                            
                        }
                    }).catch((err) => console.error(err));
            },
            updatePost(postId) {
                this.$router.push({ name: "POSTUPDATE", postId })
            }
        },
        created() {
            axios
                .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+this.id)
                .then((res) => {
                    console.log(res.data);
                    this.subject = res.data.subject;
                    const linecontent = res.data.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
                    this.content = linecontent;
                    this.created = res.data.created;
                    this.likestatus = res.data.ilike
                    this.uid = res.data.uid
                })
                .catch((err) => console.error(err));
        },
}
</script>

