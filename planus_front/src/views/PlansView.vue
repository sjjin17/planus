<template>
  <v-container>
    <plan-map />
    <h1>{{ this.result.tripId }}번 방</h1>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

import PlanMap from "@/components/plans/PlanMap.vue";

export default {
  name: "PlansView",
  components: {
    PlanMap,
  },
  data() {
    return {
      tripId: 0,
      tripUrl: "",
      result: {
        tripId: 0,
        admin: 0,
        startDate: "",
        period: 0,
        tripArea: [
          {
            areaId: 0,
            doName: "",
            siName: "",
            lat: 0,
            lng: 0,
          },
        ],
      },
      memberList: [
        {
          userId: 0,
          name: "",
          email: "",
        },
      ],
    };
  },
  created() {
    this.tripId = this.$route.params.tripId;
    this.tripUrl = this.$route.params.tripUrl;
    this.getTripInfo();
    this.getMemberList();
  },
  methods: {
    async getTripInfo() {
      this.res = await api.getTripInfo(this.tripId);
      this.result = this.res.result;
      console.log(this.result);
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
      console.log(this.memberList);
    },
  },
};
</script>

<style></style>
