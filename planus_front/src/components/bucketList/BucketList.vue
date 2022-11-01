<template>
  <div>
    <bucket-card></bucket-card>
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

  methods: {
    getBucketList: function () {
      api.getBucketList(this.tripId).then((res) => {
        console.log(res);
      });
    },
  },
};
</script>
<style></style>
