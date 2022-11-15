<template>
  <div>
    <div class="mainAreaHeader">인기지역</div>
    <v-row align="center" justify="center">
      <v-col
        v-for="(area, areaindex) in areaList"
        :key="areaindex"
        :area="area"
        cols="2"
      >
        <v-card @click="getAddArea(area.areaId, area.siName)">
          <v-img :src="area.imageUrl"> </v-img>
          <div class="textCutting bestAreaName">{{ area.siName }}</div>
        </v-card>
      </v-col>
    </v-row>
    <div class="mainInfoDivider"></div>
    <div class="mainFestivalHeader">축제정보</div>
    <v-row align="center" justify="center" class="pl-10 pr-10">
      <v-col
        v-for="(festival, festivalindex) in festivalList"
        :key="festivalindex"
        :festival="festival"
        cols="3"
      >
        <v-card @click="getAddArea(festival.areaId, festival.siName)">
          <v-img :src="festival.imageUrl" height="300px"> </v-img>
          <v-card-title class="textCutting" style="display: block">{{
            festival.title
          }}</v-card-title>
          <v-card-subtitle class="textCutting">{{
            festival.address
          }}</v-card-subtitle>
          <v-card-text class="textCutting">
            시작: {{ festival.startDate }}<br />종료: {{ festival.endDate }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  data: () => ({
    areaList: [],
    festivalList: [],
  }),
  created() {
    this.getBestArea();
    this.getFestival();
  },
  methods: {
    async getBestArea() {
      this.res = await api.getBestArea();
      this.areaList = this.res.areaList;
      console.log(this.areaList);
    },
    async getFestival() {
      this.res = await api.getFestival();
      this.festivalList = this.res.festivalList;
      console.log(this.festivalList);
    },
    getAddArea(areaId, siName) {
      this.$emit("getAddArea", areaId, siName);
    },
  },
};
</script>

<style>
.mainAreaHeader {
  display: flex;
  padding: 3%;
  justify-content: center;
  font-size: 2.2rem;
  font-weight: 700;
  color: white;
}
.mainFestivalHeader {
  display: flex;
  padding-bottom: 3%;
  justify-content: center;
  font-size: 2.2rem;
  font-weight: 700;
  color: white;
}
.bestAreaName {
  padding: 4%;
  text-align: center;
  font-size: 1.2rem;
  font-weight: 700;
}
.mainInfoDivider {
  width: 90%;
  margin: 3% auto;
  border: 1px solid;
  border-color: white;
  background-color: white;
}
</style>
