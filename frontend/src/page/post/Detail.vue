<template>
  <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>게시글 정보</h1>
                <div v-if="this.likestatus">
                    <v-btn icon color="#DC143C" v-on:click="likePost(id)" >
                        <v-icon>mdi-heart</v-icon>
                    </v-btn>
                </div>
                <div v-if="!this.likestatus">
                    <v-btn icon color="black" v-on:click="likePost(id)">
                         <v-icon>mdi-heart</v-icon>
                    </v-btn>
                </div>
                <div class="form-wrap">
                    <div class="input-wrap">
                       <p>제목: <input v-model="subject" id="subject" type="text" placeholder=this.subject/></p>
                        <p>내용:  <input v-model="content" id="content" type="text" placeholder=this.content/>{{this.content}}</p>
                        <p>작성날짜: {{this.created}}</p>
                    </div>

                </div>

                <button class="btn" v-on:click="moveUpdate()"> 
                    <span>
                        정보수정
                    </span>
                </button>
                <button class="btn" v-on:click="moveList">
                    <span>
                        메인화면
                    </span>
                </button>
                <button class="btn" v-on:click="deletePost(id)">
                    <span>
                        글 삭제
                    </span>
                </button>
                    
            </div>
        </div> 
        

    </div>
</template>

<script>
import axios from 'axios';
import SERVER from "@/api/api";

const storage = window.sessionStorage;
console.log(storage);
export default {
    props:{
        id:{
            type:Number,
            required:true,
        }
    },
    data: () => {
            return {
                subject: '',
                content: '',
                created: '',
                likestatus:false,
            }
        },
        methods: {

            moveList(){
                this.$router.push("/");
            },
           
            moveUpdate(){
                console.log(this.subject)
                console.log(this.content)
                axios({
                    method:"put",
                    url:SERVER.URL+"/feature/board/update",
                    data :{
                        subject : this.subject,
                        content : this.content,
                        created : this.created,
                        id : this.id
                    }
                }).then((res)=>{
                    var msg ;
                    if(res.data.status){
                        msg = "수정이 완료되었습니다.";
                        this.$router.push("/");
                    }
                    alert(msg);
                    this.$router.push("/");
                })
            },
            deletePost(postId){
                console.log(postId);
                axios({
                    method:"delete",
                    url:SERVER.URL +"/feature/board/delete/"+postId,

                    }).then((res)=>{
                    let msg = postId+"번 글이 삭제가 완료됐습니다.";
                    if(res.data.status){
                        msg = "삭제가 완료되었습니다.";
                        this.$router.push("/");
                    }else{

                    }
                    alert(msg);
                    this.$router.push("/");
                })
            },
            likePost(postId){
                axios({
                    method: "GET",
                    url : SERVER.URL +"/like/"+postId+"/"+storage.getItem("login_user"),
                    
                }).then(
                        this.likestatus = !this.likestatus
                        
                    )
            }
            
        },
        created() {
            axios
                .get(SERVER.URL +"/feature/board/detail/"+storage.getItem("login_user")+"/"+this.id)
                .then((res) => {
                    console.log(res.data);
                    this.subject = res.data.subject;
                    this.content = res.data.content;
                    this.created = res.data.created;
                    this.likestatus = res.data.ilike  
                })
                .catch((err) => console.error(err));
                
            
        },
        
}
</script>

<style>

</style>