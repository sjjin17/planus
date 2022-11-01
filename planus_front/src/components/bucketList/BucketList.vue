<template>
  <div>
    <bucket-card
      v-for="bucket in bucketList"
      :key="bucket.place"
      :bucket="bucket"
    ></bucket-card>
    <h1>{{ tripId }}</h1>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

import BucketCard from "@/components/bucketList/BucketCard.vue";
export default {
  name: "BucketList",
  components: {
    BucketCard,
  },
  data: function () {
    return {
      bucketList: [],
    };
  },
  props: {
    tripId: Number,
  },
  watch: {
    tripId() {
      this.getBucketList();
    },
  },
  methods: {
    async getBucketList() {
      this.bucketList = await api.getBucketList(this.tripId);
      console.log(this.bucketList);
    },
  },
};
</script>
<style></style>
