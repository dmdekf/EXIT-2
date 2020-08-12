<template>
  <div class="editor">
    <v-app id="inspire">
      <v-row>
        <v-col>
          <v-card class="elevation-12">
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>글 작성</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>

            <v-card-text>
              <v-form ref="form">
                <v-text-field
                  v-model="subject"
                  id="subject"
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
      
      <editor-content class="editor__content" :editor="editor" />
        
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
import axios from "axios";
import SERVER from "@/api/api";
import Icon from '../../assets/img/menubar/index.vue'
import { required, rules, valid } from "vuelidate/lib/validators";

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
  components: {
    EditorContent,
    EditorMenuBar,
    Icon,
  },
  created() {},
  methods: {
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
          content:this.editor.content,
          email:this.email,
          uid:this.uid,
          editor: null,
        },
      })
        .then((res) => { 
          
          alert("글쓰기 성공~");
          this.$router.push("/");

          if (res.data.status) {
            this.$router.push("/");
          }
        })
        .catch((err) => console.log(err.response.data));
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
      editor: new Editor({
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
          <h2>
            Hi there,
          </h2>
          <p>
            this is a very <em>basic</em> example of tiptap.
          </p>
          <pre><code>body { display: none; }</code></pre>
          <ul>
            <li>
              A regular list
            </li>
            <li>
              With regular items
            </li>
          </ul>
          <blockquote>
            It's amazing 👏
            <br />
            – mom
          </blockquote>
        `,
      }),
    }
  },
  beforeDestroy() {
    this.editor.destroy()
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
  
</style>