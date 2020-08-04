<template>
  <v-row align="center">
        <v-card flat>
          <v-card-text>
            <v-row class="mb-4" align="center">
              <v-avatar color="grey" class="mr-4"></v-avatar>
              <h3>#{{post.id}}</h3>
              <strong class="title">제목 :  {{ subject }}</strong>
              <p>작성날짜: {{this.created}}</p>
              <hr/> 
              <v-spacer></v-spacer>
              <div class="contents">
                <v-row>
                    <v-col cols="auto">
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
                    </v-col>
                </v-row>

                </div>
              <v-btn icon v:on="userdetail()">
                <v-icon>mdi-account</v-icon>
              </v-btn>
            </v-row>

            <p>
              {{content}}
            </p>
          </v-card-text>
        </v-card>
      </v-window-item>
    </v-window>
  </v-row>
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

