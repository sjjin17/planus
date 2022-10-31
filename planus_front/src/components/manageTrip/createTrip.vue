<template>
  <div>
    <button @click="getAllArea">지역 목록</button><br />
    <button @click="createTrip">일정 생성</button>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  components: {},
  props: {},
  data: () => ({
    startDate: "2022-10-27",
    period: 4,
    areaId: [1, 2],
    tripId: 0,
    tripUrl: "",
    areaList: [
      {
        areaId: 0,
        doName: "",
        siName: "",
      },
    ],
  }),
  created() {},
  methods: {
    isLogin() {
      if (!this.$cookies.get("token")) {
        window.alert("로그인 해주세요!");
      }
    },
    async getAllArea() {
      this.res = await api.getAllArea();
      this.areaList = this.res.areaList;
      console.log(this.areaList);
    },
    async createTrip() {
      this.isLogin();
      this.res = await api.createTrip(this.startDate, this.period, this.areaId);
      this.tripId = this.res.result.tripId;
      this.tripUrl = this.res.result.tripUrl;
      this.$router.push("/plans/" + this.tripUrl);
    },
  },
};
</script>

<style></style>
