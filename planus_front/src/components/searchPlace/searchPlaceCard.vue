<template>
  <v-container class="pa-1">
    <v-card color="#B8DBC6" class="mx-0 my-1">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6 mb-1">
            {{ recommendPlace.place }}
          </v-list-item-title>
          <v-list-item-subtitle>{{
            recommendPlace.address
          }}</v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar tile size="80"
          ><v-img :src="recommendPlace.imgUrl" />
        </v-list-item-avatar>
      </v-list-item>

      <v-card-actions>
        <v-row>
          <v-col>
            <v-btn
              small
              color="#4A8072"
              class="white--text ma-0"
              @click="bucketClick"
              >버킷리스트 추가</v-btn
            >
          </v-col>
          <v-col>
            <plan-modal
              @planSubmit="planSubmit"
              :fromBucket="false"
            ></plan-modal>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import PlanModal from "@/components/searchPlace/PlanModal.vue";

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
