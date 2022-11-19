<template>
  <div>
    <!-- <a href="http://localhost:8080/planus/oauth2/authorization/kakao"> -->
    <a href="https://planus.co.kr/planus/oauth2/authorization/kakao">
      <img src="@/assets/kakao_login_medium.png" />
    </a>
  </div>
</template>

<script>
export default {
  name: "LoginButton",
  created() {
    this.isLogin = this.checkLogin();
  },
  data: () => {
    return {
      isLogin: true,
    };
  },
  methods: {
    checkLogin() {
      if (this.$cookies.get("token") == null) {
        return false;
      }
      return true;
    },
  },
};
</script>

<style></style>
