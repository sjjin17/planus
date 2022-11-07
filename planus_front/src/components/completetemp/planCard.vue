<template>
  <div>
    <div>{{ plan.tripDate[1] }}/{{ plan.tripDate[2] }}</div>
    <time-table-card
      v-for="(timeTable, i) in plan.timetableList"
      :key="i"
      :timeTable="timeTable"
      :startTime="startTime[i]"
      :endTime="endTime[i]"
      :moveTime="plan.timetableList[i].moveTime"
    ></time-table-card>
  </div>
</template>

<script>
import TimeTableCard from "@/components/completetemp/TimeTableCard.vue";
export default {
  name: "PlanCard",
  mounted() {
    this.calculateStartTime();
  },
  data: () => {
    return {
      startTime: [],
      endTime: [],
    };
  },
  props: {
    plan: Object,
  },
  components: {
    TimeTableCard,
  },
  methods: {
    calculateStartTime() {
      this.startTime.push(this.plan.startTime);
      this.endTime.push(
        this.plan.startTime + this.plan.timetableList[0].costTime
      );
      for (var i = 1; i < this.plan.timetableList.length; i++) {
        this.startTime.push(
          this.endTime[i - 1] + this.plan.timetableList[i - 1].moveTime
        );
        this.endTime.push(
          this.startTime[i] + this.plan.timetableList[i - 1].costTime
        );
      }
    },
  },
};
</script>

<style></style>
