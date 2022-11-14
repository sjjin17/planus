<template>
  <div>
    <v-btn @click="saveTrip">완료</v-btn>
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
  },
  methods: {
    saveTrip() {
      api.saveBucketList(this.tripId);
      // 전체 일정 저장 api 연결 - complete = true; (redis에서 삭제)
      let savePlanIdList = [];
      this.planIdList.forEach((plan) => {
        savePlanIdList.push(plan.planId);
      });
      api.savePlan(this.tripId, savePlanIdList, true);
      //router.push - CompleteView로 보내면 PlansView로 되돌아옴
      this.$router.go();
    },
  },
};
</script>

<style></style>
