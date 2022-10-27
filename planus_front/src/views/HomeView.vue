<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <login-button></login-button>
    <token-test-button></token-test-button>
    <token-test-button2></token-test-button2>
    <create-trip></create-trip>
  </div>
</template>

<script>
// @ is an alias to /src
import LoginButton from "@/components/KakaoLogin/LoginButton.vue";
import TokenTestButton from "@/components/KakaoLogin/TokenTestButton.vue";
import TokenTestButton2 from "@/components/KakaoLogin/TokenTestButton2.vue";
import CreateTrip from "@/components/manageTrip/createTrip.vue";
export default {
  name: "HomeView",
  components: {
    LoginButton,
    TokenTestButton,
    TokenTestButton2,
    CreateTrip,
  },
};
</script>
