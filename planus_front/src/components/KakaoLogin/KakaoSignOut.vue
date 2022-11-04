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
      this.res = await api
        .signOut()
        .then(() => this.$cookies.remove("token"))
        .then(() => this.$router.push("/"));
    },
  },
};
</script>

<style></style>
