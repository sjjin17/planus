<template>
  <div>
    <div class="mainHeaderDiv">
      <login-button></login-button>
      <v-btn class="mainPageBtn" @click="goToCommunity">커뮤니티</v-btn>
      <v-btn class="mainPageBtn" @click="goToMypage"> 마이페이지 </v-btn>
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
.mainHeaderDiv {
  display: flex;
  justify-content: flex-end;
}
.mainLogoDiv {
  display: flex;
  height: 30%;
  width: 80%;
  margin: 2% auto 0;
  justify-content: center;
  align-items: center;
  border-radius: 12px 12px 0px 0px;
  background-color: #b8dbc6;
  font-size: 6rem;
  font-weight: 800;
  color: white;
  text-shadow: 1px 2px gray;
}
.mainTripDiv {
  height: 12%;
  width: 80%;
  margin: 0 auto;
  border-radius: 0px 0px 12px 12px;
  background-color: #b8dbc6;
}
.mainFestivalDiv {
  min-height: 300px;
  width: 80%;
  margin: 2% auto;
  border-radius: 12px;
  background-color: #88c7af;
}
.mainPageBtn {
  height: 45px !important;
  width: 120px !important;
}
</style>
