<template>
<div id="app">
    <v-app id="inspire">
        <!-- <v-alert
        v-show=alert
        dismissible
        text
        fade
        color="success"
        v-bind="attrs"
        >
        댓글 작성이 성공했습니다.
        </v-alert> -->
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
                    <v-btn  v-on:click="updatePost(id)">
                        <v-icon>mdi-playlist-edit</v-icon>글 수정하기
                    </v-btn>
                    </div>
                    <div v-else>
                    <v-btn icon v:on="userfrofile(uid)">
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
        <v-card-text>
            <span v-for="(tagl, idx) in tags" :key="idx">
                <strong><span class="mr-2">#{{tagl.tag}}</span></strong>
            </span>
        </v-card-text>
        </v-card>
        
        <hr>
        
        <div>
            <v-form
                ref="form"
                v-model="valid"
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
            subject: '',
            content: '',
            created: '',
            likestatus:false,
            tags:[],
            taglist:[],
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
        this.getTags();
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
            this.$router.push(`user/profile/${uid}`);
        },
        getTags(){
            axios({
            method:"get",
            url:SERVER.URL+"/searchBoard/"+this.id,
                }).then((res)=>{
                    if(res.data){
                        console.log(res.data);
                        this.tags = res.data.object;
                        this.taglist = this.tags.tag;
                    }
                }).catch((err) => console.error(err));
        },
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
                this.comments.push(res.data)
                this.showAlert(3)   
            })
            .catch(function (error) {
            if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
            }
            else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
            alert(error.request+ "입력 정보를 확인하세요.");
            }
            else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log('Error', );
            alert(error.message+ "입력 정보를 확인하세요.");
            }
            
        })  
             
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
            this.showAlert(1)
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
                this.tags = res.data.object.tag;
            })
            .catch((err) => console.error(err));
    },
}
</script>
