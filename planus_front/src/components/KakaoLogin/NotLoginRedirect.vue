<template>
  <div>
    <h3>로그인한 사용자만 이용할 수 있습니다.</h3>
    <login-button></login-button>
  </div>
</template>

<script>
import LoginButton from "@/components/KakaoLogin/LoginButton.vue";
export default {
  name: "NotLoginRedirect",
  components: {
    LoginButton,
  },
};
</script>

<style></style>
