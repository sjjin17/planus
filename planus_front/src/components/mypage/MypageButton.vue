<template>
  <div>
    <v-btn @click="goToMypage"> 마이페이지 </v-btn>
  </div>
</template>

<script>
export default {
  name: "MypageButton",
  methods: {
    goToMypage() {
      this.$router.push({ name: "mypage" }).catch(() => {});
    },
  },
};
</script>

<style></style>
