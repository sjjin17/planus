<template>
  <div>
    <button @click="test">테스트하기</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "TokenTestButton2.vue",
  methods: {
    test() {
      axios
        .get("https://k7a505.p.ssafy.io/planus/login/test2")
        .then((res) => console.log(res))
        .catch((error) => console.log(error));
    },
  },
};
</script>

<style></style>
