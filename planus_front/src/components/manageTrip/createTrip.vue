<template>
  <div class="createTripDiv">
    <v-row>
      <v-col cols="4"
        ><v-menu
          ref="areaMenu"
          v-model="areaMenu"
          :close-on-content-click="false"
          :return-value.sync="areas"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-combobox
              v-model="areas"
              label="여행지 선택"
              multiple
              chips
              dense
              outlined
              clearable
              readonly
              v-bind="attrs"
              v-on="on"
              ><template v-slot:selection="{ attrs, area }">
                <span v-bind="attrs">{{ area }}</span>
              </template></v-combobox
            >
          </template>
          <v-card v-model="areaMenu">
            <v-card-title
              ><v-tabs v-model="tabs" fixed-tabs>
                <v-tab style="padding: 0">서울/인천</v-tab>
                <v-tab>경기도</v-tab>
                <v-tab>강원도</v-tab>
                <v-tab>충청도</v-tab>
                <v-tab>경상도</v-tab>
                <v-tab>전라도</v-tab>
                <v-tab>제주도</v-tab>
              </v-tabs>
            </v-card-title>
            <v-card-text
              ><v-tabs-items v-model="tabs"
                ><v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup1"
                    :key="i"
                    :area="area"
                    @click="addArea(area)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup2"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup3"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup4"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup5"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup6"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item>
                <v-tab-item>
                  <span
                    v-for="(area, i) in areaGroup7"
                    :key="i"
                    :area="area"
                    @click="addArea(area.areaId)"
                  >
                    {{ area.siName }}
                  </span>
                </v-tab-item></v-tabs-items
              ></v-card-text
            >
            <!-- <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="areaMenu = false">
                취소
              </v-btn>
              <v-btn text color="primary" @click="$refs.areaMenu.save(areas)">
                선택
              </v-btn>
            </v-card-actions> -->
          </v-card>
        </v-menu>
      </v-col>
      <v-col cols="6">
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
            >
            </v-combobox>
          </template>
          <v-date-picker
            v-model="dates"
            :allowed-dates="disablePastDates"
            range
            no-title
            scrollable
            @input="dateSort"
          >
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="dateMenu = false"> 취소 </v-btn>
            <v-btn text color="primary" @click="$refs.dateMenu.save(dates)">
              선택
            </v-btn>
          </v-date-picker>
        </v-menu>
      </v-col>
      <v-col cols="2"><v-btn @click="createTrip">일정 짜기</v-btn></v-col>
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
    startDate: "",
    period: 0,
    areaId: [1, 2],
    tripId: 0,
    tripUrl: "",
    areaGroup1: [],
    areaGroup2: [],
    areaGroup3: [],
    areaGroup4: [],
    areaGroup5: [],
    areaGroup6: [],
    areaGroup7: [],
    tabs: 0,
    dates: [],
    areas: [],
    dateMenu: false,
    areaMenu: false,
  }),
  created() {
    this.getAllArea();
  },
  computed: {},
  watch: {},
  methods: {
    isLogin() {
      if (!this.$cookies.get("token")) {
        window.alert("로그인 해주세요!");
      }
    },
    async getAllArea() {
      this.res = await api.getAllArea();
      let res = this.res.areaList;
      console.log(res);
      this.areaGroup1 = res.slice(0, 2);
      this.areaGroup2 = res.slice(2, 33);
      this.areaGroup3 = res.slice(33, 51);
      this.areaGroup4 = res.slice(51, 79);
      this.areaGroup5 = res.slice(79, 123);
      this.areaGroup6 = res.slice(123, 160);
      this.areaGroup7 = res.slice(160);
    },
    async createTrip() {
      this.isLogin();

      this.startDate = this.dates[0];

      if (this.dates.length < 2) {
        this.period = 0;
      } else {
        let a = new Date(this.dates[0]);
        let b = new Date(this.dates[1]);
        this.period = (b - a) / 86400000;
      }

      console.log(this.startDate);
      console.log(this.period);
      console.log(this.areas);

      // this.res = await api.createTrip(this.startDate, this.period, this.areaId);
      // this.tripId = this.res.result.tripId;
      // this.tripUrl = this.res.result.tripUrl;
      // this.$router.push("/plans/" + this.tripUrl);
    },
    addArea(area) {
      if (!this.areas.includes(area)) {
        this.areas.push(area);
      }
      console.log(this.areas);
    },
    disablePastDates(val) {
      return val >= new Date().toISOString().substr(0, 10);
    },
    dateSort() {
      if (this.dates.length > 1) {
        this.dates.sort();
      }
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
