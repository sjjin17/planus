<template>
  <div>
    <bucket-card
      v-for="bucket in bucketList"
      :key="bucket.place"
      :bucket="bucket"
      @delBucket="delBucket"
    ></bucket-card>
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
    deletedBucket: Object,
    addedBucket: Object,
  },
  mounted() {
    this.getBucketList();
  },
  watch: {
    deletedBucket() {
      // 삭제 로직

      let item = {
        place: this.deletedBucket.place,
        address: this.deletedBucket.address,
        lat: this.deletedBucket.lat,
        lng: this.deletedBucket.lng,
      };

      for (const idx of this.bucketList.keys()) {
        if (JSON.stringify(this.bucketList[idx]) == JSON.stringify(item)) {
          this.bucketList.splice(idx, 1);
        }
      }
    },
    addedBucket() {
      // 추가 로직
      this.bucketList.push(this.addedBucket);
    },
  },
  methods: {
    async getBucketList() {
      this.bucketList = await api.getBucketList(this.tripId);
    },
    delBucket(bucket) {
      this.$emit("delBucket", bucket);
    },
  },
};
</script>
<style></style>
