<template>
  <div>
    <h1>스피너라도 넣자</h1>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "KakaoRedirect",
  created() {
    const code = this.$route.query.code;
    console.log(code);
    this.sendCode(code);
  },
  methods: {
    sendCode(code) {
      console.log(code);
      // axios
      //로컬
      // .get("http://localhost:8080/planus/login/oauth2/kakao?code=" + code);
      // .then((response) => {
      //   console.log(response);
      //   VueCookies.set("token", response.data);
      // })
      // .catch((error) => console.log(error))
      // .finally(() => temp.$router.push("/"));
    },
  },
};
</script>

<style></style>
