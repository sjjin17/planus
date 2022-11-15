<template>
  <v-container>
    <div class="font-weight-bold">
      {{ plan.tripDate[1] }} / {{ plan.tripDate[2] }}
    </div>
    <v-row>
      <time-table-card
        v-for="(timeTable, i) in plan.timetableList"
        :key="i"
        :timeTable="timeTable"
        :startTime="startTime[i]"
        :endTime="endTime[i]"
      ></time-table-card>
    </v-row>
  </v-container>
</template>

<script>
import TimeTableCard from "@/components/complete/TimeTableCard.vue";
export default {
  name: "PlanCard",
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
  created() {
    this.calculateStartTime();
  },
};
</script>

<style></style>
