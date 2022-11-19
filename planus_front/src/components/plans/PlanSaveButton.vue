<template>
  <v-btn @click="saveAll">mysql에 저장</v-btn>
</template>

<script>
import API from "@/api/RESTAPI";

const api = API;

export default {
  props: {
    tripId: Number,
    planIdList: Array,
  },
  methods: {
    async saveAll() {
      let savePlanIdList = [];
      this.planIdList.forEach((p) => {
        savePlanIdList.push(p.planId);
      });
      await api.savePlan(savePlanIdList);
    },
  },
};
</script>

<style></style>
