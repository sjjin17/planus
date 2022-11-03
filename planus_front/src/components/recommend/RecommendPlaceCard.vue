<template>
  <v-container>
    <v-card>
      <v-container>
        <v-row class="row-9" align="center">
          <v-col cols="7" align="left">
            <v-row>
              <span class="dark--text">{{ recommendPlace.place }}</span>
              <v-chip class="semidark--text"
                >{{ recommendPlace.contentType }}
              </v-chip>
            </v-row>
            <v-row style="font-size: 0.8rem">{{
              recommendPlace.address
            }}</v-row>
          </v-col>
          <v-col cols="5">
            <v-img :src="recommendPlace.imgUrl" max-> </v-img>
          </v-col>
        </v-row>
        <v-row class="row-3">
          <v-col>
            <v-btn @click="bucketClick">버킷</v-btn>
          </v-col>
          <plan-modal @planSubmit="planSubmit" :fromBucket="false"></plan-modal>
        </v-row>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
import PlanModal from "@/components/recommend/PlanModal.vue";

export default {
  components: { PlanModal },
  data: () => {
    return {};
  },
  comments: {
    PlanModal,
  },
  props: {
    recommendPlace: Object,
  },
  methods: {
    bucketClick() {
      this.$emit(
        "addBucket",
        this.recommendPlace.place,
        this.recommendPlace.address,
        this.recommendPlace.lat,
        this.recommendPlace.lng
      );
    },
    planSubmit(costTime, fromBucket) {
      this.$emit(
        "addTimetable",
        costTime,
        this.recommendPlace.place,
        this.recommendPlace.lat,
        this.recommendPlace.lng,
        fromBucket
      );
    },
  },
};
</script>

<style></style>
