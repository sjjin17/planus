<template>
  <div @click="createTrip">일정 생성</div>
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
  }),
  //   created() {
  //     this.createTrip();
  //   },
  watch: {},
  methods: {
    async createTrip() {
      this.res = await api.createTrip(
        this.admin,
        this.areaId,
        this.startDate,
        this.period
      );
      this.tripId = this.res.result.tripId;
      this.tripUrl = this.res.result.tripUrl;
      console.log("tripId: " + this.tripId + ", tripUrl: " + this.tripUrl);
    },
  },
};
</script>

<style></style>
