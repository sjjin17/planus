<template>
  <div>
    <bucket-card></bucket-card>
  </div>
</template>

<script>
// import api from "@/api/RESTAPI";
// const api = API;

import BucketCard from "@/components/bucketList/BucketCard.vue";
export default {
  name: "BucketList",
  components: {
    BucketCard,
  },
  data: function () {
    return {
      
    }
  },
  props: {
    tripId: Number,
  },

  methods: {},
};
</script>
<style></style>
