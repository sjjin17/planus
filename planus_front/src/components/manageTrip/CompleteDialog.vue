<template>
  <div>
    <v-btn
      depressed
      color="#4A8072"
      outlined
      @click="saveTrip"
      v-if="memberOrAdmin == 2"
      >완료</v-btn
    >
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "CompleteDialog",
  data: function () {
    return {};
  },
  props: {
    tripId: Number,
    tripUrl: String,
    planIdList: Array,
    memberOrAdmin: Number,
  },
  methods: {
    async saveTrip() {
      await api.saveBucketList(this.tripId);
      // 전체 일정 저장 api 연결 - complete = true; (redis에서 삭제)
      let savePlanIdList = [];
      this.planIdList.forEach((plan) => {
        savePlanIdList.push(plan.planId);
      });
      await api.savePlan(this.tripId, savePlanIdList, true);
      //웹소켓으로 요청보내기
      this.$emit("completeTrip");
    },
  },
};
</script>

<style></style>
