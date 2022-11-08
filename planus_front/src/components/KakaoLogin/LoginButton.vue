<template>
  <div v-if="!isLogin">
    <!-- <a href="http://localhost:8080/planus/oauth2/authorization/kakao"> -->
    <a href="https://planus.co.kr/planus/oauth2/authorization/kakao">
      <img src="@/assets/kakao_login_medium_narrow.png" />
    </a>
  </div>
  <div v-else>
    <h3 @click="logout">로그아웃</h3>
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
      if (this.$cookies.get("refresh") == null) {
        return false;
      }
      return true;
    },
    logout() {
      this.$cookies.remove("token");
      this.$cookies.remove("refresh");
      this.isLogin = false;
    },
  },
};
</script>

<style></style>
