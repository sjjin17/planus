<template>
  <div>
    <div class="mainHeaderDiv">
      <div>
        <img
          src="@/assets/home.png"
          @click="goToHome"
          width="45px"
          style="cursor: pointer"
        />
      </div>
      <div>
        <button class="mainPageBtn" @click="goToCommunity">
          <span>커뮤니티</span>
        </button>
      </div>
      <div>
        <button class="mainPageBtn" @click="goToMypage">
          <span>마이페이지</span>
        </button>
      </div>
      <v-spacer />
      <login-button @isLoginChange="isLoginChange"></login-button>
    </div>
    <v-dialog v-model="alert" max-width="450">
      <v-card>
        <v-card-title></v-card-title>
        <v-card-text color="white"> 로그인 후 이용해주세요</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#4a8072" outlined @click="alert = false">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import LoginButton from "@/components/KakaoLogin/LoginButton.vue";
export default {
  name: "NaviBar",
  data() {
    return {
      alert: false,
      isLogin: false,
    };
  },
  components: { LoginButton },
  methods: {
    goToCommunity() {
      this.$router.push("/community");
    },
    goToMypage() {
      if (!this.isLogin) {
        this.alert = !this.alert;
      } else {
        this.$router.push("/mypage");
      }
    },
    isLoginChange(isLogin) {
      this.isLogin = isLogin;
      this.$emit("isLoginChange", isLogin);
    },
    goToHome() {
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
