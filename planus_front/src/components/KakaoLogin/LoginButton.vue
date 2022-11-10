<template>
  <div v-if="!isLogin">
    <a :href="kakaoUrl">
      <img src="@/assets/kakao_login_medium_narrow.png" />
    </a>
  </div>
  <v-btn class="mainPageBtn" v-else @click="logout">로그아웃</v-btn>
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
      kakaoUrl: process.env.VUE_APP_API_URL_KAKAO,
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
