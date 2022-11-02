<template>
  <v-container>
    <v-card>
      <v-container>
        <v-row class="pt-1 row-9" align="center">
          <v-col cols="5">
            <v-img :src="recommendPlace.imgUrl" class="ml-1" max-> </v-img>
          </v-col>
          <v-col cols="7" align="left">
            <v-row>
              <v-col cols="9">
                <h3 class="ml-3">
                  <span class="dark--text">{{ recommendPlace.place }}</span>
                </h3>
              </v-col>
              <v-col cols="3">
                <span class="semidark--text"
                  >{{ recommendPlace.contentType }}
                </span>
              </v-col>
            </v-row>
            <v-row>{{ recommendPlace.address }}</v-row>
          </v-col>
        </v-row>
        <v-row class="row-3">
          <v-btn @click="bucketClick">버킷</v-btn>
          <plan-modal @planSubmit="planSubmit"></plan-modal>
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
    planSubmit(hours, minutes) {
      this.$emit(
        "addTimetable",
        hours,
        minutes,
        this.recommendPlace.place,
        this.recommendPlace.lat,
        this.recommendPlace.lng
      );
    },
  },
};
</script>

<style></style>
