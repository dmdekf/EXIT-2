
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
                  <v-form ref="form" onsubmit="return false;">
                    <v-text-field
                      v-model="subject"
                      id="subject"
                      label="제목을 입력해주세요"
                      name="subject"
                      type="text"
                      :counter="10"
                      required
                    >{{subject}}</v-text-field>
                    <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
                    <div class="menubar">
                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.bold() }"
                        @click="commands.bold"
                      >
                      <img class="icon" src="../../assets/img/icon/bold.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.italic() }"
                        @click="commands.italic"
                      >
                        <img class="icon" src="../../assets/img/icon/italic.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.strike() }"
                        @click="commands.strike"
                      >
                      <img class="icon" src="../../assets/img/icon/strike.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.underline() }"
                        @click="commands.underline"
                      >
                        <img class="icon" src="../../assets/img/icon/underline.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.code() }"
                        @click="commands.code"
                      >
                        <img class="icon" src="../../assets/img/icon/code.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.paragraph() }"
                        @click="commands.paragraph"
                      >
                      <img class="icon" src="../../assets/img/icon/paragraph.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                        @click="commands.heading({ level: 1 })"
                      >
                        H1
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                        @click="commands.heading({ level: 2 })"
                      >
                        H2
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                        @click="commands.heading({ level: 3 })"
                      >
                        H3
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.bullet_list() }"
                        @click="commands.bullet_list"
                      >
                        <img class="icon" src="../../assets/img/icon/ul.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.ordered_list() }"
                        @click="commands.ordered_list"
                      >
                        <img class="icon" src="../../assets/img/icon/ol.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.blockquote() }"
                        @click="commands.blockquote"
                      >
                        <img class="icon" src="../../assets/img/icon/quote.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        :class="{ 'is-active': isActive.code_block() }"
                        @click="commands.code_block"
                      >
                        <img class="icon" src="../../assets/img/icon/code.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        @click="commands.horizontal_rule"
                      >
                      <img class="icon" src="../../assets/img/icon/hr.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        @click="commands.undo"
                      >
                        <img class="icon" src="../../assets/img/icon/undo.svg" alt="">
                      </button>

                      <button
                        class="menubar__button"
                        @click="commands.redo"
                      >
                        <img class="icon" src="../../assets/img/icon/redo.svg" alt="">
                      </button>

                        </div>
                      </editor-menu-bar>
                      <editor-content class="editor__content" 
                        :editor="editor"
                        />
                        <p>{{content}}</p>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                  <v-btn color="primary" v-on:click="moveUpdate()">수정하기</v-btn>
                  <v-btn color="red" v-on:click="deletePost(id)">삭제하기</v-btn>
                </v-card-actions>
                <v-container>
                  <h2>파일 업로더</h2>
                  <input
                  id = "file-selector"
                  ref="file"
                  type="file"
                  @change="handleFileUpload()"
                  />
                  <v-btn @click="uploadFile" color="primary" flat> 업로드</v-btn>
                </v-container>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import AWS from 'aws-sdk'
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
import { Editor, EditorContent, EditorMenuBar } from 'tiptap'
import Icon from '../../assets/img/menubar/index.vue'
import {
  Blockquote, 
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
} from 'tiptap-extensions'
let contents=""
export default {
  components: {
    EditorContent,
    EditorMenuBar,
    Icon,
  },
    name:"postUpdate",
    props:{
      id:{
          type:Number,
          required:true,
      }
    },
    data: () => {
      return {
        albumBucketName :"photo-album-two",
        bucketRegion : "ap-northeast-2",
        IdentityPoolId : "ap-northeast-2:242f0477-ae48-4591-9ee8-e628cfcfd999",
        file : null,

          subject: '',
          created: '',
          content: '',
          }
    },
    methods: {
      handleFileUpload(){
        this.file = this.$refs.file.files[0]
        console.log('파일이 업로드 되엇습니다')
      },
      uploadFile(){
        AWS.config.update({
          region: this.bucketRegion,
          credentials: new AWS.CognitoIdentityCredentials({
              IdentityPoolId: this.IdentityPoolId
          })
        })
        const s3 = new AWS.S3({
          apiVersion: '2006-03-01',
          params: {
              Bucket: this.albumBucketName
          }
        })
        let photoKey = this.file.name
         axios({
            method: "post",
            url: SERVER.URL + "/board/bimg",
            data: {
                    bid: this.id,
                    uimage : 'https://photo-album-two.s3.ap-northeast-2.amazonaws.com/'+photoKey
                }
        })
        s3.upload({
          Key: photoKey,
          Body: this.file,
          ACL: 'public-read'
        }, (err, data) => {
            if (err) {
                return alert('There was an error uploading your photo: ', err.message);
            }
            alert('Successfully uploaded photo.');
        });
      },
      ...mapActions(['showAlert']),
      moveList(){
          this.$router.push("/");
      },
      moveUpdate(){
        axios({
            method:"put",
            url:SERVER.URL+"/feature/board/update",
            data :{
                subject : this.subject,
                content : contents,
                created : this.created,
                id : this.id
            }
        }).then((res)=>{
            var msg ;
            if(res.data.status){
                this.$router.push("/");
                this.showAlert(6)
            } else {
              this.showAlert(2)
            }
            
        })
      },
      deletePost(postId){
        axios({
            method:"delete",
            url:SERVER.URL +"/feature/board/delete/"+postId,
            }).then((res)=>{
            let msg = postId+"번 글이 삭제가 완료됐습니다.";
            if(res.data.status){
                msg = "삭제가 완료되었습니다.";
                this.showAlert(1)
                this.$router.push("/");
            }else{
            }
            alert(msg);
            this.$router.push("/");
        })
      },
      clearContent() {
      this.editor.clearContent(true)
      this.editor.focus()
    },
    setContent() {
      // you can pass a json document
      this.editor.setContent({
        type: 'doc',
        content: [{
          type: 'paragraph',
          content: [
            {
              type: 'text',
              text: 'This is some inserted text. ',
            },
          ],
        }],
      }, true)
      // HTML string is also supported
      // this.editor.setContent('<p>This is some inserted text. </p>')
      this.editor.focus()
    },
  },
  created() {
      axios
          .get(SERVER.URL +"/feature/board/detail/"+this.$store.state.login_user+"/"+this.id)
          .then((res) => {
              //(res.data);
              this.subject = res.data.subject;
              this.content = res.data.content;
              
              this.created = res.data.created;
              contents = this.content
              this. editor = new Editor({
                  extensions: [
                    new Blockquote(),
                    new BulletList(),
                    new CodeBlock(),
                    new HardBreak(),
                    new Heading({ levels: [1, 2, 3] }),
                    new HorizontalRule(),
                    new ListItem(),
                    new OrderedList(),
                    new TodoItem(),
                    new TodoList(),
                    new Link(),
                    new Bold(),
                    new Code(),
                    new Italic(),
                    new Strike(),
                    new Underline(),
                    new History(),
                  ],
                  content: contents,
                  
                  onUpdate: ({ getHTML }) => {
                    contents = getHTML()
                    //("2 this.content: "+this.content)
                    //("2 contents: "+contents)
                  },
              })
              ////("create this.content: "+this.content)
              ////("create contents: "+contents)
          })
          .then(()=>{
            contents = this.content
          })
          .catch((err) => console.error(err));
    
  },
  mounted(){
  },
}
</script>
<style>
.v-application code {
    background-color: black;
    color: white;
    /* padding: 0 0.4rem; */
}
.menubar {
  margin: 1rem;
}
button, [type=button], [type=reset], [type=submit], [role=button] {
    margin-right: 0.3rem;
}
.editor__content * {
    margin-left: 1rem;
}
</style>
