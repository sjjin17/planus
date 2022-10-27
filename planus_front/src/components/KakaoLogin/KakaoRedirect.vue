<template>
  <div>
    <h1>스피너라도 넣자</h1>
  </div>
</template>

<script>
import axios from "axios";
import VueCookies from "vue-cookies";

export default {
  name: "KakaoRedirect",
  created() {
    const code = this.$route.query.code;
    console.log(code);
    this.sendCode(code);
  },
  methods: {
    sendCode(code) {
      var temp = this;
      axios
        .get("http://localhost:8080/login/oauth2/kakao?code=" + code)
        .then((response) => {
          console.log(response);
          VueCookies.set("token", response.data);
        })
        .catch((error) => console.log(error))
        .finally(() => temp.$router.push("/"));
    },
  },
};
</script>

<style></style>
