<template>
  <h1>스피너</h1>
</template>

<script>
export default {
  name: "getKakaoToken",
  data() {
    return {};
  },
  created() {
    const token = this.$route.query.token;
    const refreshToken = this.$route.query.refresh;
    this.saveToken(token, refreshToken);
  },
  methods: {
    saveToken(token, refreshToken) {
      // var expire = new Date(new Date().getTime() + 30 * 60 * 1000);
      this.$cookies.set("token", token, 60 * 10 * 6);
      this.$cookies.set("refresh", refreshToken, 60 * 60 * 24 * 30);
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
