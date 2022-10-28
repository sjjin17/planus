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
    admin: 1,
    areaId: [1, 2],
    startDate: "2022-10-27",
    period: 4,
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
  watch: {},
  methods: {
    async getAllArea() {
      this.res = await api.getAllArea();
      this.areaList = this.res.areaList;
      console.log(this.areaList);
    },
    async createTrip() {
      this.res = await api.createTrip(
        this.admin,
        this.areaId,
        this.startDate,
        this.period
      );
      this.tripId = this.res.result.tripId;
      this.tripUrl = this.res.result.tripUrl;
      this.$router.push({
        name: "plans",
        params: {
          tripId: this.tripId,
          tripUrl: this.tripUrl,
        },
      });
    },
  },
};
</script>

<style></style>
