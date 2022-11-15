<template>
  <div style="position: relative; display: flex; justify-content: center">
    <div class="areaBtn">
      <v-btn
        x-small
        class="ma-1 white--text"
        v-for="(area, areaIdx) in tripArea"
        :key="areaIdx"
        :color="area.siName == areaName ? '#FF1744' : '#544C4C'"
        @click="clickLocation(area, 12)"
        >{{ area.siName }}</v-btn
      >
    </div>
    <gmap-map
      :zoom="zoom"
      :center="center"
      style="width: 40%; height: 40vh"
      @zoom_changed="getCurrentZoom"
      @center_changed="getCurrentCenter"
    >
      <div v-for="(plan, index) in completeList" :key="index + 't'">
        <gmap-marker
          :key="index + 'p'"
          v-for="(m, index) in plan.timetableList"
          :position="m"
          :label="{
            text: m.place.substring(0, 2),
            color: '#FF1744',
            fontWeight: 'bold',
            fontSize: '10px',
          }"
          :icon="{
            url: require('@/assets/PlanMarker.png'),
            scaledSize: { width: 30, height: 30 },
            labelOrigin: { x: 15, y: -10 },
          }"
          id="plan"
        ></gmap-marker>
        <gmap-polyline
          v-if="plan.timetableList"
          :path.sync="plan.timetableList"
          :options="{ strokeColor: '#FF1744', strokeWeight: 1 }"
        >
        </gmap-polyline>
      </div>
    </gmap-map>
  </div>
</template>

<script>
export default {
  name: "PlanMap",

  data() {
    return {
      center: {},
      zoom: 11,
      nowCenter: {},
      areaName: null,
    };
  },
  props: {
    tripArea: Array,
    completeList: Array,
  },
  methods: {
    getCurrentCenter(center) {
      this.nowCenter = { lat: center.lat(), lng: center.lng() };
      this.$emit("getCenter", center.lat(), center.lng());
    },
    getCurrentZoom(zoom) {
      this.zoom = zoom;
    },
    clickLocation(loc, zoom) {
      this.areaName = loc.siName;
      setTimeout(() => {
        this.center = this.nowCenter;
      }, 50);
      setTimeout(() => {
        this.center = loc;
        this.nowCenter = loc;
        this.zoom = zoom;
      }, 50);
    },
  },
  watch: {
    tripArea(newVal) {
      this.areaName = newVal[0].siName;
    },
    completeList(newVal) {
      let lat = 0;
      let lng = 0;
      newVal.forEach((element) => {
        let tmpLat = 0;
        let tmpLng = 0;
        element.timetableList.forEach((e) => {
          tmpLat += e.lat;
          tmpLng += e.lng;
        });
        lat += tmpLat / element.timetableList.length;
        lng += tmpLng / element.timetableList.length;
      });
      lat /= newVal.length;
      lng /= newVal.length;

      this.center = { lat, lng };
      this.nowCenter = this.center;
    },
  },
  created() {
    if (this.tripArea.length) {
      this.areaName = this.tripArea[0].siName;
      this.center = this.tripArea[0];
      this.nowCenter = this.tripArea[0];
    }
    if (this.timetableList) {
      this.planList = this.timetableList;
    }
  },
};
</script>

<style scoped>
.areaBtn {
  position: absolute;
  z-index: 1;
}
</style>
