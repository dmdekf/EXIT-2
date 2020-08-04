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
                </div>
            </v-row>
            </v-list-item-content>
            </v-list-item>
        </v-card-title>
        <v-card-text>
            {{content}}
        </v-card-text>
    </v-card>
    </v-window-item>
    </v-window>
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

