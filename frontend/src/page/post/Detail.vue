<template>
<div id="app">
    <v-app id="inspire">
        <v-window class="mb-6">
        <v-window-item class="my-6">
        <v-card flat>
        <v-card-title class="blue lighten-5">
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
                    <div v-if="(uid)===this.$store.state.login_user">
                    <v-btn  v-on:click="updatePost(id)">
                        <v-icon>mdi-playlist-edit</v-icon>글 수정하기
                    </v-btn>
                    </div>
                    <div v-else>
                    <v-btn icon v-on:click="userfrofile(uid)">
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
        <div v-for="(b, index) in boardImageList" :key="index">
            <img :src="b.uimage"/>
        </div>
        <v-card-text >
            <div v-html="content">{{content}}</div>
        </v-card-text>
        </v-card>
        
        <hr>
        
        <div>
            <v-form
                ref="form"
                lazy-validation
                onsubmit="return false" 
            >
            <v-text-field v-model="inputComment"
                ref="forminput"
                :counter="100"
                label="댓글 달기"
                lazy-validation
                :rules="rules" hide-details="auto"
                @keypress.enter="createComment(id)"
            >
                <template v-slot:prepend>
                    <v-btn icon color="green" @click="reset">
                    <v-icon>mdi-cached</v-icon>
                    </v-btn>
                </template>
                <template v-slot:append>
                    <v-btn id="createbtn" class="mx-2 my-1" icon color="primary"  @click="createComment(id)">
                        <v-icon>mdi-plus</v-icon>
                    </v-btn>
                </template>
            </v-text-field>
            </v-form>
        </div>
         <div v-for="(comment, index) in reverseComments" :key="index">
            <div class="contents"> 
            <v-row justify="space-between" class="ma-0" >
                <div># {{reverseComments.length-index}}번째 댓글
                    <small>{{ moment(comment.insertTime).locale('ko-kr').fromNow()}}</small>
                </div>
                <div>
                    <div v-if="(comment.writer)===login_user">
                        <v-btn  v-on:click="deleteComment(index,comment.id)" icon color="red">
                            <v-icon>mdi-trash-can-outline</v-icon>삭제
                        </v-btn>
                    </div>
                    <div v-else>
                        <v-btn icon v-on:click="userfrofile(comment.writer)">
                        <v-icon>mdi-account-outline</v-icon></v-btn>
                        {{comment.writer}}
                    </div>
                </div>
                </v-row>
                <v-row class="ma-2" >
                    <v-col class="ma-0 pa-0">
                    <v-avatar
                    size="50px"
                    class="mr-2"
                    >
                    <img :src="comment.uimage=='' ? 'https://picsum.photos/100/100' : comment.uimage" class="post-img"/>
                    </v-avatar>
                    <span >
                    {{comment.content}}
                    </span>
                    </v-col>
                </v-row>
                <hr/> 
            </div> 
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
import { mapState, mapActions } from 'vuex'
import { required, rules, valid } from "vuelidate/lib/validators";
export default {
    props:{
        id:{
            type:Number,
            required:true,
        },
    },
    data: () => {
        return {
            boardImageList : [],

            subject: '',
            content: '',
            created: '',
            likestatus:false,
            comments:[],
            rules: [
                value => !!value || '내용을 입력해 주세요',
                value => (value && value.length >= 2) || '2글자 이상 입력해 주세요',
                value => (value && value.length < 100 ) || '글자수가 초과되었습니다.',

            ],
            inputComment:'',
            uid:'',
            login_user:'',
            boardIdx:"",
            idx:"",
        }
    },
    mounted(){
        this.getComments();
    },
    computed:{
        ...mapState(['msg', 'col']),
        reverseComments() {
            return this.comments.slice().reverse()
        }
    },
    methods: {
        ...mapActions(['showAlert']),
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
        userfrofile(uid) {
            this.$router.push(`/user/profile/${uid}`);
        },          
        userdetail(){              
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
        getComments(){
            axios({
            method:"get",
            headers: {'Content-Type': 'multipart/form-data'},
            url:SERVER.URL+"/feature/comment/detail/"+this.id,
                }).then((res)=>{
                    if(res.data){
                        this.comments = res.data;
                    }
                }).catch((err) => console.error(err));
        },
        updatePost(postId) {
            this.$router.push({ name: "POSTUPDATE", postId })
        },
        
        createComment(postId) {
            axios({
                method: "post",
                //url: SERVER.URL+"/feature/comment/list/detail/comments/"+postId+"/write",
                url: SERVER.URL+"/feature/comment/wirte",
                data: {
                        boardIdx:postId,
                        content:this.inputComment,
                        writer:this.login_user,
                    },
            })
            .then((res) => { 
                this.comments.push(res.data)
                this.showAlert(3)   
            })
            .catch(function (error) {
            if (error.response) {
            }
            else if (error.request) {
            alert(error.request+ "입력 정보를 확인하세요.");
            }
            else {
            alert(error.message+ "입력 정보를 확인하세요.");
            }
            })
            this.reset()
        },  
        reset() {
            this.$refs.forminput.reset()
        },
        deleteComment(index,commentid) {
            var idx = this.comments.length-index-1
            axios({
                method: "DELETE",
                url: SERVER.URL+"/feature/comment/list/detail/comments/"+commentid,
            })
            .then((res) => { 
            this.comments.splice(idx, 1)
            this.showAlert(1)
            })
            .catch((err) => console.log(err));
        },
    },
    created() {
        axios
            .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+this.id)
            .then((res) => {
                this.subject = res.data.subject;
                const linecontent = res.data.content.replace(/(?:\r\n|\r|\n)/g, '<br />')
                this.content = linecontent;
                this.created = res.data.created;
                this.likestatus = res.data.ilike
                this.uid = res.data.uid
                this.login_user = this.$store.state.login_user
            })
            .catch((err) => console.error(err));
        axios({
            method : "post",
            url :  SERVER.URL + "/boardImg/"+this.id,
        }).then((res)=>{
            if(res.data.status){
                this.boardImageList = res.data.object
                console.dir(this.boardImageList)
                
            }
        })
        console.log("실행은 되나..")

    },
  }

</script>
