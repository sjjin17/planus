<template>
  <recommend-place-tab
    :lat="lat"
    :lng="lng"
    :size="size"
    :length="pageLength"
  ></recommend-place-tab>
</template>

<script>
import RecommendPlaceTab from "@/components/recommend/RecommendPlaceTab.vue";
import API from "@/api/RESTAPI";
const api = API;
export default {
  data: () => {
    return {
      lat: 37.5168415735,
      lng: 127.0341090296,
      size: 10,
      pageLength: 0,
    };
  },
  components: {
    RecommendPlaceTab,
  },
  created() {
    this.getPageLength();
  },
  methods: {
    async getPageLength() {
      let data = await api.getRecommendPageLength(
        this.lat,
        this.lng,
        this.size
      );
      this.pageLength = data.pageLength;
    },
  },
};
</script>

<style></style>
