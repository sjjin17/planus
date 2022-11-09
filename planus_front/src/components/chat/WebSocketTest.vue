<template>
  <div>
    <v-container fluid>
      <v-row align="center">
        <v-col cols="6">
          <v-select
            v-model="select"
            :items="selectList"
            item-text="text"
            item-value="value"
            label="Select"
            return-object
            single-line
          ></v-select>
        </v-col>
        <v-col><v-btn @click="search">검색</v-btn></v-col>
      </v-row>
      <div v-if="moveRoute">
        <div v-for="(route, idx) in routeToJsonList" :key="idx + route">
          {{ route.text }}
          <span style="color: red">
            {{ route.duration }}
          </span>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      valueEnum: {
        BUS: "transit",
        CAR: "driving",
        NONE: "NONE",
      },
      selectList: [
        { text: "선택안함", value: "NONE" },
        { text: "대중교통", value: "transit" },
        { text: "자동차", value: "driving" },
      ],
      select: { text: "선택안함", value: "NONE" },
      startTime: 600,
      timetable: {
        place: "동대문시장",
        lat: 37.566596,
        lng: 127.007702,
        orders: 1,
        moveTime: 0,
        costTime: 120,
        transit: "CAR",
      },
      nextLat: 37.5424878528,
      nextLng: 126.9927953989,
      moveRoute: "",
      calTime: [480, 600],
      tripDate: [2022, 11, 30],
    };
  },
  mounted() {
    this.selectList.forEach((select) => {
      if (select.value == this.valueEnum[this.timetable.transit]) {
        this.select = select;
        console.log(this.select);
      }
    });
  },
  computed: {
    routeList() {
      let list = this.moveRoute.split("/");
      console.log(list);
      let last = list[list.length - 2].split(" ");
      list.pop();
      list.pop();
      list.push("다음위치까지 " + last[last.length - 1]);
      return list;
    },
    routeToJsonList() {
      let array = [];
      this.routeList.forEach((route) => {
        let text = route.split(":");
        array.push({ text: text[0], duration: text[1] });
      });
      return array;
    },
    transitTime() {
      return (
        new Date(
          this.tripDate[0] +
            "-" +
            this.tripDate[1] +
            "-" +
            this.tripDate[2] +
            "."
        ).getTime() /
          1000 +
        this.calTime[this.timetable.orders - 1] * 60
      );
    },
  },
  methods: {
    search() {
      let transit = this.select.value;
      console.log(transit);
      if (transit == "NONE") {
        console.log("선택안함");
      } else if (transit == "transit") {
        this.googleSearch(transit);
      } else {
        this.naverSearch();
      }
    },
    googleSearch(transit) {
      axios
        .post(
          process.env.VUE_APP_API_URL + "/google",
          "https://maps.googleapis.com/maps/api/directions/json?origin=" +
            this.timetable.lat +
            "," +
            this.timetable.lng +
            "&destination=" +
            this.nextLat +
            "," +
            this.nextLng +
            "&mode=" +
            transit +
            "&departure_time=" +
            this.transitTime +
            "&key="
        )
        .then((res) => {
          if (!res.data.routes[0]) {
            console.log("결과없음");
            return;
          }
          let data = res.data.routes[0].legs[0];
          this.costTime = Math.ceil(data.duration.value / 60);
          let steps = data.steps;
          console.log(steps);
          let move = "";
          steps.forEach((step) => {
            if (step.transit_details) {
              let detail = step.transit_details;
              let sub = "";
              sub += detail.line.vehicle.name;
              if (detail.line.short_name) {
                sub += detail.line.short_name;
              } else {
                sub += detail.line.name;
              }
              sub += " ";
              sub +=
                detail.departure_stop.name +
                " - " +
                detail.arrival_stop.name +
                ":" +
                step.duration.text +
                "/";
              move += sub;
            } else {
              move += step.html_instructions + ":" + step.duration.text + "/";
            }
          });
          this.moveRoute = move;
        });
    },
    naverSearch() {
      axios
        .post(
          process.env.VUE_APP_API_URL + "/naver",
          "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=" +
            this.timetable.lng +
            "," +
            this.timetable.lat +
            "&goal=" +
            this.nextLng +
            "," +
            this.nextLat
        )
        .then((res) => {
          console.log(res.data.route.traoptimal[0].summary.duration / 60000);
          console.log(
            Math.ceil(res.data.route.traoptimal[0].summary.duration / 60000)
          );
        });
    },
  },
};
</script>

<style></style>
