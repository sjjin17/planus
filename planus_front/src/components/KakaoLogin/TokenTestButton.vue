<template>
  <div>
    <button @click="test">테스트하기</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "TokenTestButton.vue",

  methods: {
    test() {
      // var temp = this;
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + this.$cookies.get("token");
      axios
        //로컬
        .get("https://k7a505.p.ssafy.io/planus/login/test")
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
          this.toLogin();
        });
    },
  },
};
</script>

<style></style>
