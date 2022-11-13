<template>
  <div>
    <plan-card v-for="(plan, i) in plans" :key="i" :plan="plan">></plan-card>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import PlanCard from "@/components/complete/planCard.vue";
const api = API;
export default {
  name: "CompletePage",
  components: {
    PlanCard,
  },
  data: () => {
    return {
      plans: [],
    };
  },
  created() {
    this.getCompleteInfo();
  },
  methods: {
    async getCompleteInfo() {
      var tripUrl = document.location.href.split("/")[4];
      console.log(tripUrl);
      var data = await api.getComplete(tripUrl);
      console.log(data);
      this.plans = data.result.planResDTOList;
    },
  },
};
</script>

<style></style>
