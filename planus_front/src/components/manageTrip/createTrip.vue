<template>
  <div class="createTripDiv">
    <v-row>
      <v-col cols="4">
        <v-menu
          ref="dateMenu"
          v-model="dateMenu"
          :close-on-content-click="false"
          :return-value.sync="dates"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-combobox
              v-model="dates"
              label="여행 일정 선택"
              prepend-icon="mdi-calendar"
              multiple
              chips
              dense
              outlined
              clearable
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-combobox>
          </template>
          <v-date-picker v-model="dates" range no-title scrollable>
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="dateMenu = false"> 취소 </v-btn>
            <v-btn text color="primary" @click="$refs.dateMenu.save(dates)">
              선택
            </v-btn>
          </v-date-picker>
        </v-menu>
      </v-col>
      <v-col cols="4"><v-btn @click="getAllArea">여행지 선택</v-btn></v-col>
      <v-col cols="2"><v-btn @click="createTrip">시작하기</v-btn></v-col>
    </v-row>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  components: {},
  props: {},
  data: () => ({
    startDate: "2022-10-27",
    period: 4,
    areaId: [1, 2],
    tripId: 0,
    tripUrl: "",
    areaList: [
      {
        areaId: 0,
        doName: "",
        siName: "",
      },
    ],
    dates: [],
    dateMenu: false,
  }),
  created() {},
  computed: {},
  methods: {
    isLogin() {
      if (!this.$cookies.get("token")) {
        window.alert("로그인 해주세요!");
      }
    },
    async getAllArea() {
      this.res = await api.getAllArea();
      this.areaList = this.res.areaList;
      console.log(this.areaList);
    },
    async createTrip() {
      this.isLogin();
      this.res = await api.createTrip(this.startDate, this.period, this.areaId);
      this.tripId = this.res.result.tripId;
      this.tripUrl = this.res.result.tripUrl;
      this.$router.push("/plans/" + this.tripUrl);
    },
  },
};
</script>

<style>
.createTripDiv {
  margin: 0 auto;
  width: 70%;
}
</style>
