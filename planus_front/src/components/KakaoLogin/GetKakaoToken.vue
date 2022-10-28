<template>
  <h1>스피너</h1>
</template>

<script>
import axios from "axios";
import VueCookies from "vue-cookies";
export default {
  name: "getKakaoToken",
  data() {
    return {};
  },
  created() {
    const token = this.$route.query.token;
    console.log(token);
    this.saveToken(token);
  },
  methods: {
    saveToken(token) {
      VueCookies.set("token", token);
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + VueCookies.get("token");
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
