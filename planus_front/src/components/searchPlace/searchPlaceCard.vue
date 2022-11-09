<template>
  <v-container class="pa-1">
    <v-card color="#B8DBC6" class="mx-0 my-1">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6 mb-1">
            {{ searchedPlace.name }}
          </v-list-item-title>
          <v-list-item-subtitle>{{
            searchedPlace.vicinity
          }}</v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar tile size="80"
          ><v-img :src="searchedPlace.photos[0]" />
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
    searchedPlace: Object,
  },
  methods: {
    bucketClick() {
      this.$emit(
        "addBucket",
        this.searchedPlace.place,
        this.searchedPlace.address,
        this.searchedPlace.lat,
        this.searchedPlace.lng
      );
    },
    planSubmit(costTime, fromBucket) {
      this.$emit(
        "addTimetable",
        costTime,
        this.searchedPlace.place,
        this.searchedPlace.lat,
        this.searchedPlace.lng,
        fromBucket
      );
    },
  },
};
</script>

<style></style>
