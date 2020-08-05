<template>
<div id="app">
    <v-app id="inspire">
    <v-window>
        <v-window-item>
        <v-card flat>
        <v-card-title class="lime">
            <v-list-item>
            <v-list-item-content>
            <v-list-item-subtitle><small>#{{id}}</small></v-list-item-subtitle>
            
            <v-list-item-title class="headline">제목 :  {{ subject }}</v-list-item-title>
            <v-row justify="space-around"  class="child-flex">
            <div class="ml-3"><small>작성날짜: {{this.created}}</small></div>
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
                <div>
                <v-btn icon v:on="userdetail()">
                    <v-icon>mdi-account</v-icon>
                </v-btn>
                <small>{{uid}}</small>
                </div>
            </v-row>
            </v-list-item-content>
            </v-list-item>
        </v-card-title>
        <v-card-text>
            {{content}}
        </v-card-text>
        <hr>
         <div v-for="(comment, idx) in comments" :key="idx">
                    <a style="color: black">
                        <div class="contents"> 
                        <v-row>
                            <v-col>
                                #{{comment.idx}}번 댓글 
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
        uid:{
            type:String,
            required:true,
        }
    },
    data: () => {
            return {
                subject: '',
                content: '',
                created: '',
                likestatus:false,
                uid:'',
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
                
                //this.$router.push("/user/detail/"+this.uid);
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
                //console.log()
                url:SERVER.URL+"/feature/board/detail/"+this.uid+"/"+this.id+"/comments",
                    }).then((res)=>{
                        if(res.data){
                            console.log(this.uid);
                            console.log(res.data);
                            this.comments = res.data;
                            
                        }
                    })

            }

        },
        created() {
            axios
                .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+this.id)
                .then((res) => {
                    console.log(res.data);
                    this.subject = res.data.subject;
                    this.content = res.data.content;
                    this.created = res.data.created;
                    this.likestatus = res.data.ilike
                    this.uid = res.data.uid
                    console.log("UID"+this.uid)  
                })
                .catch((err) => console.error(err));
                
            
        },
        
}
</script>

<style>

</style>

