<template>
<div id="app">
    <v-app id="inspire">
    <v-window>
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
            <v-row justify="space-between" class="ma-2" >
                <div># {{reverseComments.length-index}}번째 댓글
                    <small>{{ moment(comment.insertTime).locale('ko-kr').fromNow()}}</small>
                </div>
                <div>
                    <div v-if="(comment.writer)===login_user">
                        <v-btn  v-on:click="deleteComment(index,comment.idx)" icon color="red">
                            <v-icon>mdi-trash-can-outline</v-icon>삭제
                        </v-btn>
                    </div>
                    <div v-else>
                        <v-btn icon>
                        <v-icon>mdi-account-outline</v-icon></v-btn>
                        {{comment.writer}}
                    </div>
                </div>
                </v-row>
                <v-row class="ma-4">
                    <span class="mr-4">

                    <img width="50" height="50" :src="comment.uimage=='' ? require('@/assets/img/pimg/ttoru.jpg') : require('@/assets/img/pimg/'+comment.uimage)" class="post-img"/>
                    </span>
                    {{comment.content}}
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
import { required, rules } from "vuelidate/lib/validators";
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
            comments:[],
            rules: [
                value => !!value || '내용을 입력해 주세요',
                value => (value && value.length >= 2) || '2글자 이상 입력해 주세요',
                value => (value && value.length < 100 ) || '글자수가 초과되었습니다.',

            ],
            inputComment:'',
            uid:'',//글작성자
            login_user:'',
            boardIdx:"",
            idx:"",
        }
    },
    mounted(){
        this.getComments();
    },
    computed:{
        reverseComments() {
            return this.comments.slice().reverse()
        }
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
        // getComments(){
        //     axios({
        //     method:"get",
        //     url:SERVER.URL+"/feature/comment/detail/"+this.$store.state.login_user+"/"+this.id+"/comments",
        //         }).then((res)=>{
        //             if(res.data){
        //                 console.log(res.data);
        //                 this.comments = res.data;
        //             }
        //         }).catch((err) => console.error(err));
        // },
        getComments(){
            axios({
            method:"get",
            url:SERVER.URL+"/feature/comment/detail/"+this.id,
                }).then((res)=>{
                    if(res.data){
                        console.log(res.data);
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
                url: SERVER.URL+"/feature/comment/list/detail/comments/"+postId+"/write",
                data: {
                        boardIdx:postId,
                       content:this.inputComment,
                        writer:this.login_user,
                    },
            })
            .then((res) => { 
                console.log(res.data)
                alert("댓글 작성 성공~");
                this.comments.push(res.data)
            })
            .catch((err) => console.log(err.response.data))
        },
        reset () {
            this.$refs.forminput.reset()
            },
        deleteComment(index,commentidx) {
            console.log(index, commentidx)
            var idx = this.comments.length-index-1
            axios({
                method: "DELETE",
                url: SERVER.URL+"/feature/comment/list/detail/comments/"+commentidx,
            })
                         .then((res) => { 
            this.comments.splice(idx, 1)
            alert("댓글 삭제 성공~");
            })
            .catch((err) => console.log(err));
        },
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
                this.login_user = this.$store.state.login_user
            })
            .catch((err) => console.error(err));
    },
    beforeUpdate() {

    }
}
</script>

