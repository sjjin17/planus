<template>
  <v-container>
    <v-card>
      <v-container>
        <v-row class="row-9" align="center">
          <v-col cols="7" align="left">
            <v-row style="margin: -6px" id="hovering">
              <div class="dark--text textCutting">
                {{ recommendPlace.place }}
              </div>
              <div id="hovering-content" style="margin-top: -28px">
                {{ recommendPlace.place }}
              </div>
              <v-chip class="semidark--text"
                >{{ recommendPlace.contentType }}
              </v-chip>
            </v-row>
            <v-row
              id="hovering"
              style="font-size: 0.8rem; margin: -6px; margin-top: 12px"
            >
              <div class="textCutting">
                {{ recommendPlace.address }}
              </div>
              <div id="hovering-content" style="margin-top: -20px">
                {{ recommendPlace.address }}
              </div>
            </v-row>
          </v-col>
          <v-col cols="5" class="pa-1">
            <v-img :src="recommendPlace.imgUrl" style="max-height: 100px">
            </v-img>
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

<style>
.textCutting {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
}
#hovering {
  width: inherit;
  position: relative;
  display: inline-block;
}

#hovering-content {
  display: none !important;
  position: absolute;
  z-index: 1; /*다른 요소들보다 앞에 배치*/
  width: max-content;
  background-color: #4a8072;
  color: white;
  border-radius: 10px;
  padding: 3px;
}

#hovering:hover #hovering-content {
  display: block !important;
}
</style>
