<template>
  <div>
    <v-btn
      icon
      @click="recommendClick"
      style="
        position: absolute;
        right: 15px;
        top: 5px;
        z-index: 1;
        background-color: #b8dbc6;
      "
    >
      <v-icon>mdi-cached</v-icon>
    </v-btn>
    <recommend-place-card
      v-for="recommendPlace in recommendList"
      :key="recommendPlace.recommendId"
      :memberOrAdmin="memberOrAdmin"
      :recommendPlace="recommendPlace"
      @addBucket="addBucket"
      @addTimetable="addTimetable"
      :fromBucket="false"
    ></recommend-place-card>
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
import RecommendPlaceCard from "@/components/recommend/RecommendPlaceCard.vue";

const api = API;
export default {
  components: {
    RecommendPlaceCard,
    InfiniteLoading,
  },
  data: () => {
    return {
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
    memberOrAdmin: Number,
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
    recommendClick() {
      this.$emit("recommendClick");
    },
  },
};
</script>

<style scoped></style>
