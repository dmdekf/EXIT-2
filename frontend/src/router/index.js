import Vue from "vue";
import Router from "vue-router";
import store from "../store/index";
//소셜 콜백
import Github from "../page/user/githublogin.vue"
import Kakao from "../page/user/kakaologin.vue"
// 유저
import Loginvutify from "../page/user/Loginvuetify.vue";
import Joinvuetify from "../page/user/Joinvuetify.vue";
import Signup from "../page/user/Signup.vue";
import Authentication from "../page/user/Authentication";
import Detail from "../page/user/Detail";
import Profile from "../page/user/Profile.vue";
import Update from "../page/user/Update";
import Delete from "../page/user/Delete";
import Logout from "../page/user/LogoutView.vue"
// 포스트
import List from "../page/post/List.vue";
import Write from "../page/post/Write.vue";
import WriteComplete from "../page/post/WriteComplete.vue";
import postDetail from "../page/post/Detail.vue";
import postUpdate from "../page/post/Update.vue";
//검색
import Search from "../views/search/Search.vue";
Vue.use(Router);

const routes = [
  //포스트리스트 = 메인페이지
  {
    path: "/",
    name: 'MAIN',
    component: List,
  },
  //소셜 api로그인
  {
    path: "/user/logintest/callback",
    name: 'githublogin',
    component: Github,
  },
  {
    path: "/user/logintest/kakao/callback",
    name: 'kakaologin',
    component: Kakao,
  },
  // 유저
  {
    path: "/user/jointest",
    name: 'SIGNUP',
    component: Joinvuetify,
  },
  {
    path: "/user/logintest",
    name: 'LOGIN',
    component: Loginvutify,
  },
  {
    path: "/user/detail",
    name: "USERDETAIL",
    component: Detail,
  },
  {
    path: "/user/profile/:uid",
    props: ({params}) => ({uid:String(params.uid)}),
    name: "USERPROFILE",
    component: Profile,
  },
  {
    path: "/user/signup",
    name: "USERSIGNUP",
    component: Signup,
  },
  {
    path: "/user/auth",
    name: "AUTH",
    component: Authentication,
  },
   {
    path: "/user/delete",
    name: "USERDELETE",
    component: Delete,
  },
  {
    path: "/user/update",
    name: "USERUPDATE",
    component: Update,
  },
  {
    path: '/user/logout',
    name: 'Logout',
    component: Logout,
  },
  //검색
  {
    path: "/search",
    name: "SEARCH",
    component: Search,
  },
  //포스트
  {
    path: "/post/write",
    name: "WRITE",
    component: Write
  },
  {
    path: "/post/writecomplete",
    name: "WRITECOMPLETE",
    component: WriteComplete
  },
  {
    path: "/post/detail/:id",
    props: ({params}) => ({id:Number.parseInt(params.id)}),
    name: "POSTDETAIL",
    component: postDetail
  },
  {
    path: "/post/update/:id",
    props: ({params}) => ({id:Number.parseInt(params.id)}),
    name: "POSTUPDATE",
    component: postUpdate
  },
];

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const publicPages = ["LOGIN", "SIGNUP", "MAIN","SEARCH", "githublogin",'kakaologin']; // Login 안해도 됨
  const authPages = ["LOGIN", "SIGNUP"]; // Login 되어있으면 안됨
  const authRequired = !publicPages.includes(to.name); // 로그인 해야 함.
  const unauthRequired = authPages.includes(to.name); // 로그인 해서는 안됨
 
  if (unauthRequired && store.state.status) {
    alert("잘못된 접근입니다.")
    next("/");
  }
  authRequired && !store.state.status ? next({ name: 'LOGIN' }) : next();
});

export default router;
