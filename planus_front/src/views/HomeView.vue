<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <login-button></login-button>
    <create-trip></create-trip>
    <kakao-sign-out></kakao-sign-out>
    <mypage-button></mypage-button>
  </div>
</template>

<script>
// @ is an alias to /src
import LoginButton from "@/components/KakaoLogin/LoginButton.vue";
import CreateTrip from "@/components/manageTrip/createTrip.vue";
import KakaoSignOut from "@/components/KakaoLogin/KakaoSignOut.vue";
import MypageButton from "@/components/mypage/MypageButton.vue";

export default {
  name: "HomeView",
  components: {
    LoginButton,
    CreateTrip,
    KakaoSignOut,
    MypageButton,
  },
};
</script>
