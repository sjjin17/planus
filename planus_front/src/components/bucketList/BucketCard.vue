<template>
  <div>
    <v-card>
      {{ bucket.place }}
      {{ bucket.address }}
    </v-card>
  </div>
</template>

<script>
export default {
  name: "BucketCard",
  data: function () {
    return {};
  },
  props: {
    bucket: Object,
  },
  methods: {},
};
</script>
<style></style>
