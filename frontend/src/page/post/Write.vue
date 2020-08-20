<template>
  <div class="editor">
    <v-app id="inspire">
      <v-row>
        <v-col cols="12">
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
  </div>
</template>

<script>
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
  CodeBlockHighlight,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
} from 'tiptap-extensions'

import javascript from 'highlight.js/lib/languages/javascript'
import css from 'highlight.js/lib/languages/css'

import {
  JavaScriptExample,
  CSSExample,
  ExplicitImportExample,
} from '../codeHighlight/example'

export default {
  components: {
    EditorContent,
    EditorMenuBar,
    Icon,
  },
  created() {
  },
  methods: {
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
              text: 'This is some inserted text. <br>',
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
  data() {
    return {
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
          new CodeBlockHighlight({
            languages: {
              javascript,
              css,
            },
          }),
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
pre {
  &::before {
    content: attr(data-language);
    text-transform: uppercase;
    display: block;
    text-align: right;
    font-weight: bold;
    font-size: 0.6rem;
  }
  code {
    .hljs-comment,
    .hljs-quote {
      color: #999999;
    }
    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #f2777a;
    }
    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #f99157;
    }
    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #99cc99;
    }
    .hljs-title,
    .hljs-section {
      color: #ffcc66;
    }
    .hljs-keyword,
    .hljs-selector-tag {
      color: #6699cc;
    }
    .hljs-emphasis {
      font-style: italic;
    }
    .hljs-strong {
      font-weight: 700;
    }
  }
}
.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 1rem;
  height: 1rem;
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
  border: 1px dashed black;
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
.v-application code {
    background-color: black;
    //color: white;
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
    margin-right: 1rem;
}
</style>