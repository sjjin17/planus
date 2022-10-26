import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCookies from "vue-cookies";
import vuetify from "./plugins/vuetify";

import * as VueGoogleMaps from "vue2-google-maps"; // Import package

Vue.config.productionTip = false;

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyBfcNUXkFbC3frOKuKVqdkJkLxfvC_an0Q",
    libraries: "places",
    region: "KR",
  },
});

new Vue({
  router,
  store,
  VueCookies,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
