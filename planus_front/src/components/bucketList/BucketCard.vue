<template>
  <div>
    <v-card outlined color="#B8DBC6" class="my-4">
      <v-icon @click="delClick" class="d-flex justify-end">mdi-close</v-icon>
      <p class="text-h5 text--primary">{{ bucket.place }}</p>
      <div class="d-flex justify-space-between">
        <p>{{ bucket.address }}</p>
        <v-btn
          depressed
          elevation="2"
          color="#4A8072"
          class="white--text"
          v-show="memberOrAdmin == 2"
          @click="movePlan"
          >일정 추가</v-btn
        >
      </div>
    </v-card>
    <plan-modal @planSubmit="planSubmit"></plan-modal>
  </div>
</template>

<script>
import PlanModal from "@/components/recommend/PlanModal.vue";

export default {
  name: "BucketCard",
  data: function () {
    return {};
  },
  components: {
    PlanModal,
  },
  props: {
    bucket: Object,
    memberOrAdmin: Number,
  },
  methods: {
    delClick() {
      this.$emit("delBucket", this.bucket);
    },

    planSubmit(costTime, fromBucket) {
      fromBucket = true;
      this.$emit(
        "addTimetable",
        costTime,
        this.bucket.address,
        this.bucket.place,
        this.bucket.lat,
        this.bucket.lng,
        fromBucket
      );
      console.log(fromBucket);
    },
  },
};
</script>
<style></style>
