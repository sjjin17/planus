<template>
  <div>
    <navi-bar @isLoginChange="isLoginChange"></navi-bar>
    <div class="mainLogoDiv">Planus</div>
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
    <create-trip
      ref="createTripMenu"
      class="mainTripDiv"
      @alert="change"
    ></create-trip>
    <festival-list
      class="mainFestivalDiv"
      @getAddArea="getAddArea"
    ></festival-list>
    <v-dialog v-model="dialog" persistent max-width="300">
      <v-card>
        <v-card-title>{{ siName }}</v-card-title>
        <v-card-text>이 지역을 여행지에 추가하시겠습니까?</v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn outlined color="#4a8072" @click="dialog = false">취소</v-btn>
          <v-btn outlined color="#ff1744" @click="addArea()">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import CreateTrip from "@/components/manageTrip/CreateTrip.vue";
import FestivalList from "@/components/mainPage/FestivalList.vue";
import NaviBar from "@/components/nav/NaviBar.vue";

export default {
  name: "HomeView",
  components: {
    NaviBar,
    CreateTrip,
    FestivalList,
  },
  data: () => {
    return {
      alert: false,
      areaId: 0,
      siName: "",
      dialog: false,
      isLogin: false,
    };
  },
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
    change() {
      this.alert = !this.alert;
    },
    getAddArea(areaId, siName) {
      this.areaId = areaId;
      this.siName = siName;
      this.dialog = true;
    },
    addArea() {
      this.$refs.createTripMenu.addArea2(this.areaId, this.siName);
      this.dialog = false;
    },
    isLoginChange(isLogin) {
      this.isLogin = isLogin;
    },
  },
};
</script>

<style>
.mainHeaderDiv {
  width: 80%;
  display: flex;
  justify-content: space-between;
  margin: 2% auto 0;
}
.mainLogoDiv {
  display: flex;
  height: 300px;
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
  height: 140px;
  width: 80%;
  margin: 0 auto;
  border-radius: 0px 0px 12px 12px;
  background-color: #b8dbc6;
  box-shadow: 0px 5px 10px rgb(91 87 75 / 20%);
}
.mainFestivalDiv {
  min-height: 300px;
  width: 80%;
  margin: 2% auto 6%;
  border-radius: 12px;
  background-color: #88c7af;
  box-shadow: 0px 5px 10px rgb(91 87 75 / 20%);
}
.mainPageBtn {
  height: 45px;
  width: 110px;
  border-radius: 6px;
  color: #383d3c;
  font-size: 1.2rem;
  font-weight: 800;
}
.mainPageBtn span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}
.mainPageBtn span:after {
  content: "\00bb";
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}
.mainPageBtn:hover span {
  padding-right: 20px;
}
.mainPageBtn:hover span:after {
  opacity: 1;
  right: 0;
}
.theme--light.v-pagination .v-pagination__item {
  font-weight: 600;
  color: #383d3c;
}
.theme--light.v-pagination .v-pagination__item--active {
  color: white;
}
.theme--light.v-chip:not(.v-chip--active) {
  background-color: #4a8072;
  color: white;
  font-weight: 600;
}
</style>
