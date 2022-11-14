<template>
  <v-dialog v-model="dialog" max-width="300px">
    <template v-slot:activator="{ on, attrs }">
      <v-icon small v-bind="attrs" v-on="on">mdi-pencil</v-icon>
    </template>
    <v-container style="background-color: white">
      <v-row class="pa-3">
        <v-col class="ma-0 pa-0" cols="3">
          <v-text-field
            type="number"
            :min="0"
            :max="11"
            outlined
            hide-details
            v-model="costHour"
          ></v-text-field>
        </v-col>
        <v-col class="ma-0 pa-0" cols="2" style="align-self: center"
          ><h3>시간</h3></v-col
        >
        <v-col class="ma-0 pa-0" cols="3">
          <v-text-field
            type="number"
            :min="0"
            :max="59"
            outlined
            hide-details
            v-model="costMin"
          ></v-text-field>
        </v-col>
        <v-col class="ma-0 pa-0" style="align-self: center"><h3>분</h3></v-col>
        <v-col class="ma-0 pa-0" style="align-self: center"
          ><v-btn
            color="#4A8072"
            @click="changeCostTime(costHour, costMin)"
            style="color: white"
            >수정</v-btn
          ></v-col
        >
      </v-row>
    </v-container>
  </v-dialog>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    costHour: 0,
    costMin: 0,
  }),
  props: {
    timetable: Object,
  },
  mounted() {
    this.costHour = Math.floor(this.timetable.costTime / 60);
    this.costMin = this.timetable.costTime % 60;
  },
  methods: {
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
        moveRoute: this.timetable.moveRoute,
      };
      this.dialog = false;
      this.$emit("changeCostTime", newTimetable);
    },
  },
};
</script>

<style></style>
