<template>
  <div>
    <h3 @click="signOut">탈퇴하기</h3>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "KakaoSignOut",
  methods: {
    signOut() {
      this.$cookies.remove("token");
      //로컬
      axios.get("http://localhost:8080/planus/login/signout");
    },
  },
};
</script>

<style></style>
