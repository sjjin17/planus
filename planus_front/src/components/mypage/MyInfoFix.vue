<template>
  <v-container>
    <v-row>
      <my-info-card style="z-index: 1"></my-info-card>
    </v-row>
    <v-container class="background-box"></v-container>
    <kakao-sign-out></kakao-sign-out>
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
  position: relative;
  top: -250px;
  width: 33%;
  height: 320px;
  z-index: 0;
  background-color: #b8dbc6;
}
</style>
