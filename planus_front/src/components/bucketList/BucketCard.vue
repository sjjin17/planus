<template>
  <div>
    <v-card outlined color="#B8DBC6" class="my-4">
      <v-icon @click="delClick" class="d-flex justify-end">mdi-close</v-icon>
      <p class="text-h5 text--primary">{{ bucket.place }}</p>
      <div class="d-flex justify-space-between">
        <p>{{ bucket.address }}</p>
        <v-btn depressed elevation="2" color="#4A8072" class="white--text"
          >일정 추가</v-btn
        >
      </div>
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
  methods: {
    delClick() {
      this.$emit("delBucket", this.bucket);
    },
  },
};
</script>
<style></style>
