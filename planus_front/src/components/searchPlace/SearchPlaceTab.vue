<template>
  <div>
    <v-container class="mx-0 px-0 search-tap">
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
      v-for="(searchedPlace, index) in searchedResults"
      :key="index + 's'"
      :searchedPlace="searchedPlace"
      @addBucket="addBucket"
      @addTimetable="addTimetable"
      :fromBucket="false"
    ></search-place-card>
    <v-container>
      <infinite-loading v-if="isSearched" @infinite="getNextResults">
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
      searchedResults: [],
      nextPageToken: null,
      isSearched: false,
    };
  },
  props: {
    mapLat: Number,
    mapLng: Number,
  },
  methods: {
    async searchPlace() {
      this.isSearched = false;
      this.keyword.trim();
      console.log("검색");
      if (this.keyword.length == 0) return;
      let data = await api.googleApi(
        `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${this.mapLat},${this.mapLng}&radius=50000&keyword=${this.keyword}&key=`
      );
      console.log(data);
      if (data.next_page_token) this.nextPageToken = data.next_page_token;
      this.searchedResults = data.results;
      this.isSearched = true;
      // axios
      //   .post(
      //     process.env.VUE_APP_API_URL + "/google",
      //     `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${this.mapLat},${this.mapLng}&radius=50000&keyword=${this.keyword}&key=`
      //   )
      //   .then((res) => {
      //     console.log(res.data);
      //     if (res.data.next_page_token)
      //       this.nextPageToken = res.data.next_page_token;
      //     this.searchedResults = res.data.results;
      //     this.isSearched = true;
      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });
    },
    getNextResults($state) {
      if (!this.isSearched) return;
      axios
        .post(
          process.env.VUE_APP_API_URL + "/google",
          `https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=${this.nextPageToken}&key=`
        )
        .then((res) => {
          console.log(res.data);
          let tmp = this.searchedResults;
          res.data.results.forEach((element) => {
            tmp.push(element);
          });
          this.searchedResults = tmp;
          if ($state) {
            if (!res.data.next_page_token) {
              this.nextPageToken = null;
              $state.complete();
              console.log("a");
            } else {
              this.nextPageToken = res.data.next_page_token;
              $state.loaded();
              console.log("b");
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    async loadRecommend() {
      this.lat = this.mapLat;
      this.lng = this.mapLng;
      await this.getPageLength();
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
.search-tap {
  position: sticky;
  top: 0;
  z-index: 1;
  background-color: white;
}
</style>
