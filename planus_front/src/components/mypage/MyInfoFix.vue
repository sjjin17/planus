<template>
  <v-container>
    <v-row>
      <my-info></my-info>
    </v-row>
    <v-row class="d-flex justify-end mb-6">
      <kakao-sign-out></kakao-sign-out>
    </v-row>
  </v-container>
</template>

<script>
import MyInfo from "@/components/mypage/MyInfo.vue";
import KakaoSignOut from "@/components/KakaoLogin/KakaoSignOut.vue";
export default {
  name: "MyInfoFix",
  components: {
    MyInfo,
    KakaoSignOut,
  },
};
</script>

<style></style>
