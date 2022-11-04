<template>
  <div>
    <div>
      <form>
        <v-text-field outlined v-model="startHour" type="number"></v-text-field>
        시
        <v-text-field outlined v-model="startMin" type="number"></v-text-field>
        분
        <v-icon @click="changeStartTime(plan, startHour, startMin)"
          >mdi-pencil</v-icon
        >
      </form>
    </div>
    <timetable-card
      v-for="timetable in timetableList"
      :key="timetable.orders"
      :timetable="timetable"
      :calTime="calTime"
      :startTime="startTime"
      @changeCalTime="changeCalTime"
      @setTimetable="setTimetable"
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
      calTime: [],

      startHour: 0,
      startMin: 0,
    };
  },
  props: {
    tripId: Number,
    plan: Object,
  },
  async created() {
    await this.getPlanList([this.plan.planId]);
  },
  watch: {
    startTime(newStartTime) {
      newStartTime;
      this.calculateCalTime(this.calTime, this.timetableList, this.startTime);
      //re-rendering을 위해 배열 splice 필요
      this.calTime.splice(0, 1, this.startTime);
    },
    timetableList: {
      handler() {
        console.log("timetableList에 변경사항 있음");
        this.calculateCalTime(this.calTime, this.timetableList, this.startTime);
        //re-rendering을 위해 배열 splice 필요
        this.calTime.splice(0, 1, this.startTime);
      },
      deep: true,
    },
  },
  methods: {
    async getPlanList(planIdList) {
      api.getPlanList(planIdList).then((res) => {
        console.log(res.planList[0]);
        this.timetableList = res.planList[0].timetableList;
        this.startTime = res.planList[0].startTime;
        // this.calTime = this.startTime;
        this.startHour = Math.floor(this.startTime / 60);
        this.startMin = this.startMin % 60;

        //timetableList 정렬
        this.sortTimetableList(this.timetableList);

        //calTime 계산
        this.calculateCalTime(this.calTime, this.timetableList, this.startTime);
      });
    },
    changeCalTime(endTime) {
      this.calTime = endTime;
    },
    sortTimetableList(timetableList) {
      timetableList.sort(function (a, b) {
        return a.orders - b.orders;
      });
    },
    calculateCalTime(calTime, timetableList, startTime) {
      for (let i = 0; i < timetableList.length; i++) {
        if (i == 0) {
          calTime[0] = startTime;
        } else {
          calTime[i] = calTime[i - 1] + timetableList[i - 1].costTime;
        }
      }
    },
    changeStartTime(plan, startHour, startMin) {
      this.startTime = parseInt(startHour) * 60 + parseInt(startMin);
      let newPlan = {
        planId: plan.planId,
        startTime: this.startTime,
        tripDate: plan.tripDate,
      };
      console.log(newPlan);
      //emit으로 newPlan을 PlansView로 보내기
      this.$emit("setPlan", newPlan);
    },
    setTimetable(newTimetable) {
      //emit으로 올라온 newTimetable의 costTime 값으로 timetable 값을 수정
      this.$emit("setTimetable", newTimetable, this.plan.planId);
      //해당 timetable 객체의 costTime을 수동으로 바꿔줌 .. ...왜..?
      this.timetableList[newTimetable.orders - 1].costTime =
        newTimetable.costTime;
    },
  },
};
</script>

<style></style>
