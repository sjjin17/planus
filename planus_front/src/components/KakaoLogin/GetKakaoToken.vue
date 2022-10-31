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
    this.saveToken(token);
  },
  methods: {
    saveToken(token) {
      this.$cookies.set("token", token);
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
