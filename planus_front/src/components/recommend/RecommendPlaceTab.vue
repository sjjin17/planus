<template>
  <div>
    <recommend-place-card
      v-for="recommendPlace in recommendList"
      :key="recommendPlace.recommendId"
      :recommendPlace="recommendPlace"
      @addBucket="addBucket"
      @addTimetable="addTimetable"
      :fromBucket="false"
    ></recommend-place-card>
    <v-container>
      <v-row>
        <v-container d-flex>
          <v-col cols="6">
            <v-pagination
              v-model="page"
              :length="pageLength"
              @input="click"
            ></v-pagination>
          </v-col>
          <v-col cols="6"> {{ page }}/{{ pageLength }} </v-col>
        </v-container>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import RecommendPlaceCard from "@/components/recommend/RecommendPlaceCard.vue";

const api = API;
export default {
  components: {
    RecommendPlaceCard,
  },
  data: () => {
    return {
      recommendList: [],
      page: 1,
      pageLength: 0,
    };
  },
  props: {
    lat: Number,
    lng: Number,
    size: Number,
    isRecommendClick: Boolean,
  },
  mounted() {
    this.loadRecommend();
  },
  watch: {
    isRecommendClick() {
      this.loadRecommend();
    },
  },
  methods: {
    async loadRecommend() {
      await this.getPageLength();
      this.getRecommend(this.lat, this.lng, this.page - 1, this.size);
    },
    async getPageLength() {
      let data = await api.getRecommendPageLength(
        this.lat,
        this.lng,
        this.size
      );
      this.pageLength = data.pageLength;
    },
    async getRecommend(lat, lng, page, size) {
      await api.getRecommend(lat, lng, page, size).then((res) => {
        this.recommendList = res.recommendList;
      });
    },
    click() {
      this.getRecommend(this.lat, this.lng, this.page - 1, this.size);
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

<style scoped>
.v-pagination__item {
  display: none !important;
}
.v-pagination__more {
  display: none !important;
}
</style>
