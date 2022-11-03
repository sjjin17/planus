<template>
  <div>
    <div>출발시간 : {{ startTime / 60 }} 시 {{ startTime % 60 }} 분</div>
    <timetable-card
      v-for="timetable in timetableList"
      :key="timetable.orders"
      :timetable="timetable"
      :startTime="calTime"
      @changeCalTime="changeCalTime"
    >
    </timetable-card>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import TimetableCard from "@/components/plans/TimetableCard.vue";

const api = API;
export default {
  components: {
    TimetableCard,
  },
  data() {
    return {
      timetableList: [],
      startTime: 0,
      calTime: 0,
    };
  },
  props: {
    tripId: Number,
    plan: Object,
  },
  async created() {
    await this.getPlanList([this.plan.planId]);
  },
  methods: {
    async getPlanList(planIdList) {
      api.getPlanList(planIdList).then((res) => {
        console.log(res.planList[0]);
        this.timetableList = res.planList[0].timetableList;
        this.startTime = res.planList[0].startTime;
        this.calTime = this.startTime;

        console.log(this.startTime);
        console.log(this.timetableList);
      });
    },
    changeCalTime(endTime) {
      console.log(endTime);
      this.calTime = endTime;
    },
  },
};
</script>

<style></style>
