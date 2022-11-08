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
      valueEnum: { WALK: "walking", BUS: "transit", CAR: "driving", NONE: "" },
      textEnum: {
        WALK: "도보",
        BUS: "대중교통",
        CAR: "자동차",
        NONE: "선택안함",
      },
      selectList: [
        { text: "선택안함", value: "NONE" },
        { text: "도보", value: "walking" },
        { text: "대중교통", value: "transit" },
        { text: "자동차", value: "driving" },
      ],
      select: { text: "선택안함", value: "NONE" },
      startTime: 600,
      planList: [
        {
          place: "동대문시장",
          lat: 37.566596,
          lng: 127.007702,
          orders: 1,
          moveTime: 0,
          costTime: 120,
          transit: "NONE",
        },
        {
          place: "고투몰",
          lat: 37.5062379,
          lng: 127.0050378,
          orders: 2,
          moveTime: 0,
          costTime: 180,
          transit: "NONE",
        },
      ],
      moveRoute: "",
      costTime: 0,
    };
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
  },
  methods: {
    search(order, transit) {
      order = 1;
      transit = this.select.value;
      console.log(transit);
      if (transit == "NONE") {
        console.log("선택안함");
      } else if (transit == "transit") {
        axios
          .post(
            "http://localhost:8080/planus/google/direction",
            "https://maps.googleapis.com/maps/api/directions/json?origin=" +
              this.planList[order - 1].lat +
              "," +
              this.planList[order - 1].lng +
              "&destination=" +
              this.planList[order].lat +
              "," +
              this.planList[order].lng +
              "&mode=" +
              transit +
              "&departure_time=now&key="
          )
          .then((res) => {
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
      } else {
        console.log();
      }
    },
  },
};
</script>

<style></style>
