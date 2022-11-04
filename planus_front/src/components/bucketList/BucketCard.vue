<template>
  <div>
    <v-card
      outlined
      color="#b8dbc6"
      class="my-4"
      :class="[isPlan == false ? 'isBucket' : 'toPlan']"
    >
      <v-icon @click="delClick" class="d-flex justify-end">mdi-close</v-icon>
      <p class="text-h5 text--primary">{{ bucket.place }}</p>
      <div class="d-flex justify-space-between">
        <p>{{ bucket.address }}</p>
        <plan-modal
          v-show="memberOrAdmin == 2"
          @planSubmit="planSubmit"
          :fromBucket="true"
        ></plan-modal>
      </div>
    </v-card>
  </div>
</template>

<script>
import PlanModal from "@/components/recommend/PlanModal.vue";

export default {
  name: "BucketCard",
  data: function () {
    return {
      isPlan: false,
    };
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

    planSubmit(costTime, fromBucket, isClick) {
      this.isPlan = isClick;
      this.$emit(
        "addTimetable",
        costTime,
        this.bucket.place,
        this.bucket.lat,
        this.bucket.lng,
        fromBucket,
        this.bucket.address,
        isClick
      );
    },
  },
};
</script>
<style>
.toPlan {
  background-color: grey;
  opacity: 0.7;
}
.isBucket {
  background-color: #b8dbc6;
}
/* 
      :style="[
        isPlan == false
          ? { background: '#B8DBC6' }
          : { backgroud: rgba(184, 219, 198, 0.5) },
      ]" */
</style>
