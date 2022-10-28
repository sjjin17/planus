<template>
  <div>
    <recommend-place-card
      v-for="recommendPlace in recommendList"
      :key="recommendPlace.recommendId"
      :recommendPlace="recommendPlace"
    ></recommend-place-card>
    <v-container>
      <v-row justify="center">
        <v-col cols="8">
          <v-container class="max-width">
            <v-pagination
              v-model="page"
              class="my-4"
              :length="length"
              @input="click"
            ></v-pagination>
          </v-container>
        </v-col>
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
    };
  },
  props: {
    lat: Number,
    lng: Number,
    size: Number,
    length: Number,
  },
  created() {
    this.getRecommend(this.lat, this.lng, this.page - 1, this.size);
  },
  methods: {
    async getRecommend(lat, lng, page, size) {
      await api.getRecommend(lat, lng, page, size).then((res) => {
        this.recommendList = res.recommendList;
      });
    },
    click() {
      this.getRecommend(this.lat, this.lng, this.page - 1, this.size);
    },
  },
};
</script>

<style></style>
