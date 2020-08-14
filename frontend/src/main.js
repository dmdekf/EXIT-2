import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import store from "./store";
import VueSession from 'vue-session';
import InfiniteLoading from 'vue-infinite-loading';
import moment from 'moment';
Vue.prototype.moment = moment
/* eslint-disable no-new */
new Vue({
  router,
  store,
  vuetify,
  VueSession,
  InfiniteLoading,
  moment,
  render: (h) => h(App),
}).$mount("#app");
