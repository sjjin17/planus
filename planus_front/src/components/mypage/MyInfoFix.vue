<template>
  <v-container>
    <v-row>
      <my-info-card></my-info-card>
    </v-row>
    <v-container class="background-box"></v-container>
    <v-row class="d-flex justify-end mt-6 mb-6">
      <kakao-sign-out></kakao-sign-out>
    </v-row>
  </v-container>
</template>

<script>
import MyInfoCard from "@/components/mypage/MyInfoCard.vue";
import KakaoSignOut from "@/components/KakaoLogin/KakaoSignOut.vue";
export default {
  name: "MyInfoFix",
  components: {
    MyInfoCard,
    KakaoSignOut,
  },
};
</script>

<style scoped>
.background-box {
  margin-top: -22%;
  max-width: 450px;
  min-height: 320px;
  z-index: -1;
  background-color: #b8dbc6;
}
</style>
