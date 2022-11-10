<template>
  <v-container class="pa-1">
    <v-card color="#B8DBC6" class="mx-0 my-1" @click="addMarker">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6 mb-1">
            {{ searchedPlace.place }}
          </v-list-item-title>
          <v-list-item-subtitle>{{
            searchedPlace.address
          }}</v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar tile size="80"
          ><v-img v-if="searchedPlace.photo" :src="searchedPlace.photo" />
        </v-list-item-avatar>
      </v-list-item>

      <v-card-actions>
        <v-row>
          <v-col>
            <v-btn
              small
              color="#4A8072"
              class="white--text ma-0"
              @click.stop="bucketClick"
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
import Black from "@/assets/default_image.png";

export default {
  components: { PlanModal },
  data: () => {
    return {
      blackImage: Black,
    };
  },
  comments: {
    PlanModal,
  },
  props: {
    searchedPlace: Object,
  },
  methods: {
    bucketClick() {
      this.$emit("removeMarker");
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
    addMarker() {
      this.$emit(
        "addMarker",
        this.searchedPlace.place,
        this.searchedPlace.address,
        this.searchedPlace.lat,
        this.searchedPlace.lng
      );
    },
  },
};
</script>

<style></style>
