import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCookies from "vue-cookies";
import vuetify from "./plugins/vuetify";
import VueChatScroll from "vue-chat-scroll";
import Kakaosdk from "vue-kakao-sdk";

import * as VueGoogleMaps from "vue2-google-maps"; // Import package

Vue.config.productionTip = false;

Vue.use(VueGoogleMaps, {
  load: {
    key: process.env.VUE_APP_GOOGLE_MAP_KEY,
    libraries: "places",
    region: "KR",
  },
});

Vue.use(VueCookies);

Vue.use(VueChatScroll);

Vue.use(Kakaosdk, { apiKey: process.env.VUE_APP_KAKAO_JS_KEY });

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
