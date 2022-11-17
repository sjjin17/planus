<template>
  <div v-if="!isLogin">
    <a :href="kakaoUrl">
      <img src="@/assets/kakao_login_medium.png" />
    </a>
  </div>
  <button
    class="mainPageBtn"
    v-else
    @click="logout"
    style="color: rgb(56, 61, 60, 50%)"
  >
    <span>로그아웃</span>
  </button>
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
      this.$emit("goToHome");
    },
  },
};
</script>

<style></style>
