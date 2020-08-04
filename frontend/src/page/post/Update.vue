
<template>
  <div id="app">
    <v-app id="inspire">
      <v-main>
        <v-container class="fill-height" fluid>
          <v-row align="center" justify="center">
            <v-col cols="12" sm="8" md="8">
              <v-card class="elevation-12">
                <v-toolbar color="primary" dark flat>
                  <v-toolbar-title>글 수정 & 삭제</v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form ref="form">
                    <v-text-field
                      v-model="subject"
                      id="subject"
                      label="제목을 입력해주세요"
                      name="subject"
                      type="text"
                      :counter="10"
                      required
                    >{{subject}}</v-text-field>
                    <v-textarea
                      v-model="content"
                      id="content"
                      label="content"
                      required
                      name="content"
                      type="text"
                    >{{content}}</v-textarea>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                  <v-btn color="primary" v-on:click="moveUpdate()">수정하기</v-btn>
                  <v-btn color="red" v-on:click="deletePost(id)">삭제하기</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
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