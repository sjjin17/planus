<template>
  <div>
    <a :href="loginLink">
      <img src="@/assets/kakao_login_medium_wide.png" />
    </a>
  </div>
</template>

<script>
export default {
  name: "LoginButton",
  data: () => ({
    clientId: "88eede4a0af62a869b7cdac429891a07",
    //로컬
    redirectUri: "http://localhost:8080/planus/login/oauth2/code/kakao",
  }),
  computed: {
    loginLink() {
      return (
        "https://kauth.kakao.com/oauth/authorize?client_id=" +
        this.clientId +
        "&redirect_uri=" +
        this.redirectUri +
        "&response_type=code"
      );
    },
  },
};
</script>

<style></style>
