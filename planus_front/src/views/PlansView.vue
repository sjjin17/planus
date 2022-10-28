<template>
  <v-container>
    <plan-map />
    <h1>{{ this.tripId }}번 방</h1>
    <div v-for="(member, i) in memberList" :key="i" :member="member">
      {{ member.name }}({{ member.email }})
    </div>
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
        complete: false,
        imageUrl: "",
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
  async created() {
    await this.getTripInfo();
    await this.getMemberList();
  },
  methods: {
    async getTripInfo() {
      this.res = await api.getTripInfo(this.$route.params.tripUrl);
      this.result = this.res.result;
      this.tripId = this.result.tripId;
      console.log(this.result);
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
    },
  },
};
</script>

<style></style>
