import Vue from "vue";
import Vuex from "vuex";
import mapStore from "@/store/module/mapStore";

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    mapStore: mapStore,
  },
});

export default store;
