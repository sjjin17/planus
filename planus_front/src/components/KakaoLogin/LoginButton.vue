<template>
  <div v-if="!isLogin">
    <a :href="kakaoUrl">
      <img src="@/assets/kakao_login_medium_narrow.png" />
    </a>
  </div>
  <div v-else>
    <h3 @click="logout">로그아웃</h3>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
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
    async logout() {
      await api.logout();
      this.$cookies.remove("token");
      this.$cookies.remove("refresh");
      this.isLogin = false;
    },
  },
};
</script>

<style></style>
