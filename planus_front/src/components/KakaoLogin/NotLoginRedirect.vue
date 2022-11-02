<template>
  <div>
    <h3>로그인한 사용자만 이용할 수 있습니다.</h3>
    <login-button-for-redirect></login-button-for-redirect>
  </div>
</template>

<script>
import LoginButtonForRedirect from "@/components/KakaoLogin/LoginButtonForRedirect.vue";
export default {
  name: "NotLoginRedirect",
  components: {
    LoginButtonForRedirect,
  },
};
</script>

<style></style>
