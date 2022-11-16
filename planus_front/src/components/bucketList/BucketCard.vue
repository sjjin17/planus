<template>
  <div>
    <v-card outlined color="#b8dbc6" class="my-4">
      <!-- <i @click="delClick" class="fa-solid fa-xmark d-flex justify-end"></i> -->
      <v-icon @click="delClick" class="d-flex justify-end">mdi-close</v-icon>
      <v-card-title
        class="text-h5 font-weight-bold box"
        style="color: #544c4c"
        >{{ bucket.place }}</v-card-title
      >
      <v-card-text class="box">{{ bucket.address }}</v-card-text>
      <v-row class="ma-0 my-0">
        <v-spacer />
        <plan-modal
          class="d-flex justify-end col-6"
          v-if="memberOrAdmin == 2"
          @planSubmit="planSubmit"
          :fromBucket="true"
        ></plan-modal>
      </v-row>
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

    planSubmit(costTime, fromBucket) {
      this.$emit(
        "addTimetable",
        costTime,
        this.bucket.place,
        this.bucket.lat,
        this.bucket.lng,
        fromBucket,
        this.bucket.address
      );
    },
  },
};
</script>
<style scoped>
.v-card {
  height: 100%;
}
.v-icon {
  float: right;
}
.box {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.v-icon.v-icon:after {
  background-color: transparent;
}
</style>
