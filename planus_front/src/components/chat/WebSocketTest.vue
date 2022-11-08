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
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
const GOOGLE_KEY = process.env.VUE_APP_GOOGLE_MAP_KEY;
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
    };
  },
  components: {},
  methods: {
    search(order, transit) {
      order = 1;
      transit = this.select.value;
      console.log(transit);
      if (transit == "NONE") {
        console.log("선택안함");
      } else {
        console.log(this.planList[order - 1].lat, this.planList[order - 1].lng);
        console.log(this.planList[order].lat, this.planList[order].lng);
        axios
          .get(
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
              "&departure_time=now&key=" +
              GOOGLE_KEY,
            {
              headers: {
                "Accept-Language": "ko",
              },
            }
          )
          .then((res) => {
            console.log(res.data.routes[0]);
          });
      }
    },
  },
};
</script>

<style></style>
