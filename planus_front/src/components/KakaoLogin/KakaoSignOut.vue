<template>
  <div>
    <h5 @click="signOut" style="color: #544c4c">탈퇴하기&nbsp;&nbsp;&nbsp;</h5>
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
