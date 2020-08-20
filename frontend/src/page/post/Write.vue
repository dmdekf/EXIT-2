<template>
  <div class="editor">
    <v-app id="inspire">
      <v-row>
        <v-col>
          <v-card class="elevation-12" >
            <v-toolbar color="primary" dark flat>
                  <v-toolbar-title>글 작성</v-toolbar-title>
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
              ></v-text-field>      

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
                <!-- <p>{{content}}</p> -->

                <!-- Input hashtag -->
            </v-form>
            </v-card-text>
      <v-card-actions> 
                  <v-spacer></v-spacer>
                  <v-btn color="primary" v-on:click="writePost">글올리기</v-btn>
                  <v-btn color="lime" v-on:click="moveList">메인화면</v-btn>
                </v-card-actions>
      </v-card>
    </v-col>
    </v-row>
    </v-app>
    <v-container>
      <h2>파일 업로더</h2>
      <input
      id = "file-selector"
      ref="file"
      type="file"
      @change="handleFileUpload()"
      />
      <v-btn @click="uploadFile" color="primary" flat> 업로드</v-btn>
      <h1>파일 리스트</h1>
      <div v-for="(file,index) in fileList" :key="file.Key">
        #{{index+1}} {{file.Key}}
        <img :src="`https://photo-album-two.s3.ap-northeast-2.amazonaws.com/`+file.Key"/>
      </div>
    </v-container>
  </div>
</template>

<script>
import AWS from 'aws-sdk'
import "../../assets/css/user.scss";
import "../../assets/css/editor.scss"
import axios from "axios";
import SERVER from "@/api/api";
import Icon from '../../assets/img/menubar/index.vue'
import { required, rules, valid } from "vuelidate/lib/validators";
import { mapActions } from 'vuex'
import { Editor, EditorContent, EditorMenuBar } from 'tiptap'
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
export default {
   data() {
    return {
      albumBucketName :"photo-album-two",
      bucketRegion : "ap-northeast-2",
      IdentityPoolId : "ap-northeast-2:242f0477-ae48-4591-9ee8-e628cfcfd999",
      file : null,

      fileList:[],

      photoUrlList:[],

      alert: true,
      subject: '',
      email:'',
      hit:'',
      uid:'',
      content: '',
      tag:'',
      editor:null,
      tags:[],
    }
  },
  components: {
    EditorContent,
    EditorMenuBar,
    Icon,
  },
  created() {
    this.getFiles()
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
    getFiles(){
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
      s3.listObjects({
        Delimiter: '/'
      }, (err, data) => {
        if (err) {
            return alert('There was an error listing your albums: ' + err.message);
        } else {
          this.fileList = data.Contents
          console.log(data)
        }
    });
    },
    ...mapActions(['showAlert']),
    moveList() {
      this.$router.push("/");
    },
    writePost() {
    this.email=this.$store.state.user_email
    this.uid=this.$store.state.login_user
    
      axios({
        method: "post",
        url: SERVER.URL+"/feature/board/write/",
        data: {
          subject:this.subject,
          content:this.content,
          email:this.email,
          uid:this.uid,
          tag:this.tag,
        },
      })
        .then((res) => { 
            this.showAlert(3)
            this.$router.push("/");
          
        })
        .catch((err) => console.log(err.response.data));
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
  watch: {},
 
  beforeDestroy() {
    this.editor.destroy()
  },
  mounted(){

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
        content: `
          <p>This is some inserted text. </p>
        `,
        onUpdate: ({ getHTML }) => {
          this.content = getHTML()
        },
    })
  },
}
</script>
<style lang="scss" scope>
.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 .3rem;
  top: -.05rem;
  fill: currentColor;

  // &.has-align-fix {
  // 	top: -.1rem;
  // }

  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }

  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
  }

}

// svg sprite
body > svg,
.icon use > svg,
symbol {
  path,
  rect,
  circle,
  g {
    fill: currentColor;
    stroke: none;
  }

  *[d="M0 0h24v24H0z"] {
    display: none;
  }
}
.tag-editor, .tag-input, .tag-list {
  padding: 10px;
  margin: 20px;
  border: 1px dashed gray;
}

.tag-editor {
  display: flex;
  flex-direction: column;
}

.tag-input {
  border-color: blue;
}

.tag-list {
  border-color: green;
}
</style>