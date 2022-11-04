<template>
  <div>
    <!-- 시작시간은 자정 이전이지만 끝시간이 자정 이후일 때만 띄우기 -->
    <div
      v-if="
        this.calTime[this.timetable.orders - 1] < 1440 &&
        this.calTime[this.timetable.orders - 1] + this.timetable.costTime > 1440
      "
    >
      0시 이후의 일정입니다.
    </div>
    <v-card>
      <v-card-title>{{ this.timetable.place }}</v-card-title>
      <v-icon>mdi-close</v-icon>
      <v-card-text>
        <div>
          {{ Math.floor(this.calTime[this.timetable.orders - 1] / 60) }} :
          {{ this.calTime[this.timetable.orders - 1] % 60 }} ~
          {{
            Math.floor(
              (this.calTime[this.timetable.orders - 1] +
                this.timetable.costTime) /
                60
            )
          }}
          :
          {{
            (this.calTime[this.timetable.orders - 1] +
              this.timetable.costTime) %
            60
          }}
          ( {{ Math.floor(this.timetable.costTime / 60) }} 시간
          {{ this.timetable.costTime % 60 }} 분 )
          <v-icon small @click.native="clickChangeCostTime">mdi-pencil</v-icon>
          <v-dialog v-model="dialog">
            <form>
              <v-text-field outlined v-model="costHour"></v-text-field>
              시간
              <v-text-field outlined v-model="costMin"></v-text-field>
              분
              <v-btn @click="changeCostTime(costHour, costMin)">등록</v-btn>
            </form>
          </v-dialog>
        </div>
      </v-card-text>
    </v-card>
    <div>
      {{ this.timetable.transit }}
    </div>
  </div>
</template>

<script>
export default {
  name: "TimetableCard",
  data() {
    return {
      endTime: 0,
      dialog: false,
      costHour: 0,
      costMin: 0,
    };
  },
  props: {
    timetable: Object,
    calTime: Array,
  },
  mounted() {
    console.log("props로 받은 calTime : " + this.calTime);
    // this.endTime = this.startTime + this.timetable.costTime;
    // this.$emit("changeCalTime", this.endTime);
    this.costHour = Math.floor(this.timetable.costTime / 60);
    this.costMin = this.timetable.costTime % 60;
  },
  methods: {
    clickChangeCostTime() {
      this.dialog = true;
    },
    changeCostTime(costHour, costMin) {
      let changedCost = parseInt(costHour) * 60 + parseInt(costMin);
      let newTimetable = {
        orders: this.timetable.orders,
        place: this.timetable.place,
        lat: this.timetable.lat,
        lng: this.timetable.lng,
        costTime: changedCost,
        moveTime: this.timetable.moveTime,
        transit: this.timetable.transit,
      };
      this.dialog = false;
      this.$emit("setTimetable", newTimetable);
    },
  },
};
</script>

<style></style>
