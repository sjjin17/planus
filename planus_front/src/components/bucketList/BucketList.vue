<template>
  <div>
    <bucket-card
      v-for="bucket in bucketList"
      :key="bucket.place"
      :bucket="bucket"
      :memberOrAdmin="memberOrAdmin"
      @delBucket="delBucket"
      @addTimetable="addTimetable"
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
    memberOrAdmin: Number,
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
      // 중복이 없으면 추가해줘야 함 => 수정 필요
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
    addTimetable(address, place, lat, lng, fromBucket) {
      this.$emit("addTimetable", address, place, lat, lng, fromBucket);
    },
  },
};
</script>
<style></style>
