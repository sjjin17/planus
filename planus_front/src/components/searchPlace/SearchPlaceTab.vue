<template>
  <div>
    <v-container class="mx-0 px-0">
      <v-text-field
        v-model="keyword"
        outlined
        label="여행지 검색"
        clearable
        color="#544C4C"
        append-icon="mdi-magnify"
        @click:append="searchPlace"
        @keydown.enter.prevent="searchPlace"
        @focus="getCenter"
        hide-details
      ></v-text-field>
    </v-container>
    <search-place-card
      v-for="recommendPlace in recommendList"
      :key="recommendPlace.recommendId"
      :recommendPlace="recommendPlace"
      @addBucket="addBucket"
      @addTimetable="addTimetable"
      :fromBucket="false"
    ></search-place-card>
    <v-container>
      <infinite-loading @infinite="getRecommend">
        <div slot="no-results"></div>
        <div slot="no-more"></div>
      </infinite-loading>
    </v-container>
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import API from "@/api/RESTAPI";
import axios from "axios";
import SearchPlaceCard from "@/components/searchPlace/searchPlaceCard.vue";

const api = API;
export default {
  components: {
    SearchPlaceCard,
    InfiniteLoading,
  },
  data: () => {
    return {
      keyword: null,
      recommendList: [],
      page: 0,
      pageLength: 0,
    };
  },
  props: {
    mapLat: Number,
    mapLng: Number,
    size: Number,
    isRecommendClick: Boolean,
  },
  mounted() {
    this.loadRecommend();
  },
  watch: {
    isRecommendClick() {
      this.recommendList = [];
      this.page = 0;
      this.loadRecommend();
      this.getRecommend();
    },
  },
  methods: {
    searchPlace() {
      this.keyword.trim();
      console.log("검색");
      if (this.keyword.length == 0) return;
      axios
        .post(
          process.env.VUE_APP_API_URL + "/google",
          `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${this.mapLat},${this.mapLng}&radius=50000&keyword=${this.keyword}&key=`
        )
        .then((res) => {
          console.log(res.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getCenter() {
      console.log("포커스");
    },
    async loadRecommend() {
      this.lat = this.mapLat;
      this.lng = this.mapLng;
      await this.getPageLength();
    },
    async getPageLength() {
      let data = await api.getRecommendPageLength(
        this.lat,
        this.lng,
        this.size
      );
      this.pageLength = data.pageLength;
    },
    async getRecommend($state) {
      await api
        .getRecommend(this.lat, this.lng, this.page, this.size)
        .then((res) => {
          let recommendList = this.recommendList;
          res.recommendList.forEach((recommend) => {
            recommendList.push(recommend);
          });
          this.recommendList = recommendList;
          this.page += 1;
          if ($state) {
            if (this.pageLength < this.page) {
              $state.complete();
            } else {
              $state.loaded();
            }
          }
        });
    },
    click() {
      this.getRecommend(this.lat, this.lng, this.page, this.size);
    },
    addBucket(place, address, lat, lng) {
      this.$emit("addBucket", place, address, lat, lng);
    },
    addTimetable(costTime, place, lat, lng, fromBucket) {
      this.$emit("addTimetable", costTime, place, lat, lng, fromBucket);
    },
  },
};
</script>

<style scoped></style>
