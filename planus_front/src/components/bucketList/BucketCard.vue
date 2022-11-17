<template>
  <div>
    <v-card outlined color="#b8dbc6" @click="addMarker" class="my-4">
      <v-card-title class="card-title">
        <div class="box">{{ bucket.place }}</div>
        <v-icon @click="delClick">mdi-close</v-icon>
      </v-card-title>

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
import { mapState, mapMutations } from "vuex";
const mapStore = "mapStore";

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
  computed: {
    ...mapState(mapStore, ["spotInfo"]),
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
    ...mapMutations(mapStore, ["SET_SPOT_INFO"]),
    addMarker() {
      this.SET_SPOT_INFO(null);
      this.SET_SPOT_INFO({
        place: this.bucket.place,
        address: this.bucket.address,
        lat: this.bucket.lat,
        lng: this.bucket.lng,
      });
    },
  },
};
</script>
<style scoped>
.box {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 80%;
}
.v-icon.v-icon:after {
  background-color: transparent;
}
.card-title {
  justify-content: space-between;
  padding-bottom: 5px;
  color: #544c4c;
}
</style>
