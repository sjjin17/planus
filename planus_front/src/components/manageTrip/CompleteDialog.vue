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
  },
  methods: {
    saveTrip() {
      api.saveBucketList(this.tripId);
      // 전체 일정 저장 api 연결
    },
  },
};
</script>

<style></style>
