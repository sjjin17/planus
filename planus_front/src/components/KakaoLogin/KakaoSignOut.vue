<template>
  <div>
    <h3 @click="signOut">탈퇴하기</h3>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "KakaoSignOut",
  methods: {
    async signOut() {
      this.res = await api.signOut();
      this.$cookies.remove("token");
    },
  },
};
</script>

<style></style>
