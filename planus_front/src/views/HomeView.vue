<template>
  <div class="mainPage">
    <div class="mainHeaderDiv">
      <v-btn @click="goToCommunity">커뮤니티</v-btn>
      <v-btn @click="goToMypage"> 마이페이지 </v-btn>
      <login-button></login-button>
    </div>
    <div class="mainLogoDiv">Planus</div>
    <create-trip class="mainTripDiv"></create-trip>
    <festival-list class="mainFestivalDiv"></festival-list>
  </div>
</template>

<script>
import LoginButton from "@/components/KakaoLogin/LoginButton.vue";
import CreateTrip from "@/components/manageTrip/CreateTrip.vue";
import FestivalList from "@/components/mainPage/FestivalList.vue";

export default {
  name: "HomeView",
  components: {
    LoginButton,
    CreateTrip,
    FestivalList,
  },
  methods: {
    goToCommunity() {
      this.$router.push("/community");
    },
    goToMypage() {
      this.$router.push("/mypage");
    },
  },
};
</script>

<style>
.mainPage {
  height: 100%;
}
.mainHeaderDiv {
  display: flex;
  height: 10%;
  justify-content: flex-end;
}
.mainLogoDiv {
  display: flex;
  height: 20%;
  width: 80%;
  margin: 0 auto;
  justify-content: center;
  align-items: center;
  border-radius: 12px;
  background-color: #b8dbc6;
  font-size: 6rem;
  font-weight: 800;
  color: white;
  text-shadow: 1px 2px gray;
}
.mainTripDiv {
  height: 20%;
  width: 80%;
  margin: 0 auto;
  border-radius: 12px;
  background-color: #b8dbc6;
}
.mainFestivalDiv {
  height: 40%;
  width: 80%;
  margin: 0 auto;
  margin-top: 2%;
  border-radius: 12px;
  background-color: #88c7af;
}
</style>
