<template>
  <div>
    <button @click="test">테스트하기</button>
  </div>
</template>

<script>
import axios from "axios";
import VueCookies from "vue-cookies";
export default {
  name: "TokenTestButton.vue",

  methods: {
    test() {
      // var temp = this;
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + VueCookies.get("token");
      axios
        //로컬
        .get("http://localhost:8080/planus/login/test")
        .then((res) => console.log(res))
        .catch((error) => console.log(error));
    },
  },
};
</script>

<style></style>
