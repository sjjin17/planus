<template>
  <v-container>
    <v-row>
      <v-col v-for="(trip, index) in tripList" :key="index" cols="6">
        <v-card outlined class="mx-10 mt-2">
          <v-list-item three-line>
            <v-list-item-avatar tile size="123" class="my-0"
              ><v-img :src="trip.imageUrl"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <div class="text-overline mb-4">{{ trip.startDate }}</div>
              <v-list-item-title class="text-h5 mb-1">
                {{ trip.complete }}
              </v-list-item-title>
              <v-list-item-subtitle>{{ trip.endDate }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-pagination
        v-model="currentPage"
        class="my-4"
        :length="totalPage"
        color="#4a8072"
      ></v-pagination>
    </v-row>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  name: "MadeTripList",

  data() {
    return {
      currentPage: 1,
      tripList: null,
      totalPage: null,
    };
  },
  async created() {
    let res = await api.getMyTripMade(0);
    this.tripList = res.tripList;
    this.totalPage = res.totalPage;
  },
};
</script>

<style scoped>
.v-list-item {
  padding-left: 0;
}
.v-card {
  border-radius: 0;
  border-color: #4a8072 !important;
}
</style>
