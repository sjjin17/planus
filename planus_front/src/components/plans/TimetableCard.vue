<template>
  <div>
    <!-- 시작시간은 자정 이전이지만 끝시간이 자정 이후일 때만 띄우기 -->
    <!-- 이전 일정은 자정 전에 끝났는데 이동시간 땜에 시작 시간이 자정 이후가 되면 어떡하져..? -->
    <!-- 일정이 딱 0시에 끝나면 이거 안 띄워짐 -->
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
      <v-icon @click="delTimetable">mdi-close</v-icon>
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
            :disabled="!searchClick"
          ></v-select>
        </v-col>
        <v-col
          ><v-btn v-if="searchClick" @click="search">검색</v-btn
          ><v-btn v-if="!searchClick" @click="searchClick = !searchClick"
            >수정</v-btn
          ></v-col
        >
      </v-row>
      <div v-if="timetable.moveRoute">
        <div v-for="(route, idx) in routeToJsonList" :key="idx + route">
          {{ route.text }}
          <span style="color: red">
            {{ route.duration }}
          </span>
        </div>
      </div>
    </v-container>
    <div v-if="timetable.moveTime">{{ this.timetable.moveTime }} 분 소요</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "TimetableCard",
  data() {
    return {
      endTime: 0,
      dialog: false,
      costHour: 0,
      costMin: 0,

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
      searchClick: false,

      //transit, moveRoute, moveTime 순서
      newData: undefined,

      newTransit: "",
      newMoveRoute: "",
      newMoveTime: 0,
    };
  },
  props: {
    timetable: Object,
    calTime: Array,

    //다음 Timetable의 Lat, Lng
    //plan의 tripdate
    nextLat: Number,
    nextLng: Number,
    tripDate: Array,
  },
  mounted() {
    this.costHour = Math.floor(this.timetable.costTime / 60);
    this.costMin = this.timetable.costTime % 60;

    //timetable의 transit에 따라 select 값을 변경..아마도
    this.selectList.forEach((select) => {
      if (select.value == this.valueEnum[this.timetable.transit]) {
        this.select = select;
        // console.log(this.select);
      }
    });
  },
  computed: {
    //timetable의 moveRoute 쪼개기..?아마도
    routeList() {
      let list = this.timetable.moveRoute.split("/");
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
        moveRoute: this.timetable.moveRoute,
      };
      this.dialog = false;
      this.$emit("setTimetable", newTimetable);
    },
    delTimetable() {
      this.$emit("delTimetable", this.timetable.orders);
    },
    changeTransit() {
      if (this.newTransit != "BUS") {
        this.newMoveRoute = "";
      }
      let newTimetable = {
        orders: this.timetable.orders,
        place: this.timetable.place,
        lat: this.timetable.lat,
        lng: this.timetable.lng,
        costTime: this.timetable.costTime,
        moveTime: this.newData[2],
        transit: this.newData[0],
        moveRoute: this.newData[1],
      };
      this.$emit("setTimetable", newTimetable);
    },
    //경로검색
    async search() {
      this.searchClick = !this.searchClick;
      let transit = this.select.value;

      if (transit == "NONE") {
        console.log("선택안함");
        this.newData = ["NONE", "", 0];
      } else if (transit == "transit") {
        await this.googleSearch(transit);
        // console.log(this.newTransit);
      } else {
        await this.naverSearch();
      }

      //transit 변경 웹소켓
      this.changeTransit();
      //calTime 재계산
    },
    async googleSearch(transit) {
      await axios
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
          let steps = data.steps;
          let move = "";
          //이동 시간 아마도
          let newMoveTime = 0;
          steps.forEach((step) => {
            newMoveTime += step.duration.value;
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

          newMoveTime = Math.ceil(newMoveTime / 60);
          let newMoveRoute = move;
          let newTransit = "BUS";

          let result = [newTransit, newMoveRoute, newMoveTime];
          this.newData = result;
          return result;
        });
    },
    async naverSearch() {
      await axios
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

          let newMoveTime = Math.ceil(
            res.data.route.traoptimal[0].summary.duration / 60000
          );
          let newMoveRoute = "";
          let newTransit = "CAR";

          let result = [newTransit, newMoveRoute, newMoveTime];
          this.newData = result;
          return result;
        });
    },
  },
};
</script>

<style></style>
