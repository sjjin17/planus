<template>
  <div>
    <v-card></v-card>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  name: "BucketCard",
  data: function () {
    return {
      

    }
  },
  methods: {
    getBucketList: function() {
      this.res = await api.getBucketList(this.tripId);
      console.log(res)

    }
  },
};
</script>
<style></style>
