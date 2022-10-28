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
      // var temp = this;
      axios
        .get("http://localhost:8080/planus/login/test2")
        .then((res) => console.log(res))
        .catch((error) => console.log(error));
    },
  },
};
</script>

<style></style>
