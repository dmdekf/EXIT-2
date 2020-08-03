import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import store from "./store";
import VueSession from 'vue-session';
import InfiniteLoading from 'vue-infinite-loading';
import VueMoment from "vue-moment";
/* eslint-disable no-new */
new Vue({
  router,
  store,
  VueMoment,
  VueSession,
  vuetify,
  InfiniteLoading,
  render: (h) => h(App),
}).$mount("#app");
