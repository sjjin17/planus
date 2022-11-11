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
        hide-details
        id="search-bar"
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
      <infinite-loading v-if="nextPageToken" @infinite="getNextResults">
        <div slot="no-results"></div>
        <div slot="no-more"></div>
      </infinite-loading>
    </v-container>
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";
import SearchPlaceCard from "@/components/searchPlace/searchPlaceCard.vue";
import { mapMutations } from "vuex";
const mapStore = "mapStore";

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
    };
  },
  props: {
    mapLat: Number,
    mapLng: Number,
  },
  methods: {
    ...mapMutations(mapStore, ["SET_SPOT_INFO"]),
    async searchPlace() {
      this.keyword = this.keyword.trim();
      if (this.keyword.length == 0) return;

      this.nextPageToken = null;
      this.searchedResults = [];
      this.SET_SPOT_INFO(null);

      axios
        .post(
          process.env.VUE_APP_API_URL + "/google",
          `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${this.mapLat},${this.mapLng}&radius=50000&keyword=${this.keyword}&key=`
        )
        .then((res) => {
          document.getElementById("search-bar").blur();
          let searchedResults = [];

          res.data.results.forEach((item) => {
            searchedResults.push({
              place: item.name,
              address: item.plus_code
                ? item.plus_code.compound_code.split(" ")[
                    item.plus_code.compound_code.split(" ").length - 1
                  ] +
                  " " +
                  item.vicinity
                : item.vicinity,
              lat: item.geometry.location.lat,
              lng: item.geometry.location.lng,
              photo: item.photos
                ? "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                  item.photos[0].photo_reference +
                  "&key=" +
                  process.env.VUE_APP_GOOGLE_MAP_KEY
                : "",
            });
          });

          this.searchedResults = searchedResults;
          if (res.data.next_page_token)
            this.nextPageToken = res.data.next_page_token;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getNextResults($state) {
      if (!this.nextPageToken) return;

      axios
        .post(
          process.env.VUE_APP_API_URL + "/google",
          `https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=${this.nextPageToken}&key=`
        )
        .then((res) => {
          let nextResults = this.searchedResults;

          res.data.results.forEach((item) => {
            nextResults.push({
              place: item.name,
              address: item.plus_code
                ? item.plus_code.compound_code.split(" ")[
                    item.plus_code.compound_code.split(" ").length - 1
                  ] +
                  " " +
                  item.vicinity
                : item.vicinity,
              lat: item.geometry.location.lat,
              lng: item.geometry.location.lng,
              photo: item.photos
                ? "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                  item.photos[0].photo_reference +
                  "&key=" +
                  process.env.VUE_APP_GOOGLE_MAP_KEY
                : "",
            });
          });

          this.searchedResults = nextResults;

          if ($state) {
            if (!res.data.next_page_token) {
              this.nextPageToken = null;
              $state.complete();
            } else {
              this.nextPageToken = res.data.next_page_token;
              $state.loaded();
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
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
