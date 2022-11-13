<template>
  <div style="position: relative">
    <div class="mapAreaBtn">
      <v-btn
        v-for="(area, areaIdx) in tripArea"
        :key="areaIdx"
        @click="clickLocation(area, 12)"
        >{{ area.siName }}</v-btn
      >
    </div>
    <gmap-map
      :zoom="zoom"
      :center="center"
      style="width: 100%; height: 85vh"
      @zoom_changed="getCurrentZoom"
      @center_changed="getCurrentCenter"
    >
      <gmap-marker
        :icon="{
          url: require('@/assets/BucketMarker.png'),
          scaledSize: { width: 40, height: 40 },
          labelOrigin: { x: 20, y: -10 },
        }"
        :key="index + 'b'"
        v-for="(m, index) in bucketList"
        :position="m"
        :label="{
          text: m.place.substring(0, 2),
          color: '#4A8072',
          fontWeight: 'bold',
        }"
        @click="clickLocation(m, 15)"
        id="bucket"
      ></gmap-marker>
      <gmap-marker
        :key="index + 'p'"
        v-for="(m, index) in timetableList"
        :position="m"
        :label="{
          text: m.place.substring(0, 2),
          color: '#FF1744',
          fontWeight: 'bold',
        }"
        @click="clickLocation(m, 15)"
        :icon="{
          url: require('@/assets/PlanMarker.png'),
          scaledSize: { width: 40, height: 40 },
          labelOrigin: { x: 20, y: -10 },
        }"
        id="plan"
      ></gmap-marker>
      <gmap-polyline
        v-if="planList"
        :path.sync="planList"
        :options="{ strokeColor: '#FF1744', strokeWeight: 2 }"
      >
      </gmap-polyline>
      <gmap-marker
        :icon="{
          url: require('@/assets/InfoMarker.png'),
          scaledSize: { width: 40, height: 40 },
        }"
        v-if="spotInfo"
        :position="spotInfo"
        @click="clickLocation(spotInfo, 15), (isInfo = !isInfo)"
        id="bucket"
      >
        <gmap-info-window
          v-if="spotInfo"
          :opened="isInfo"
          @closeclick="isInfo = false"
        >
          <h3>{{ spotInfo.place }}</h3>
          <h6>{{ spotInfo.address }}</h6>
        </gmap-info-window>
      </gmap-marker>
    </gmap-map>
  </div>
</template>

<script>
import imgpath from "@/assets/logo.png";
import { mapState, mapMutations } from "vuex";
const mapStore = "mapStore";

export default {
  name: "PlanMap",

  data() {
    return {
      img: {
        url: imgpath,
        scaledSize: { width: 30, height: 30 },
      },
      center: {},
      zoom: 12,
      nowCenter: {},
      planList: [],
      isInfo: false,
    };
  },
  props: {
    tripArea: Array,
    bucketList: Array,
    timetableList: Array,
  },
  computed: {
    ...mapState(mapStore, ["spotInfo"]),
  },
  methods: {
    ...mapMutations(mapStore, ["SET_SPOT_INFO"]),
    clickLocation(loc, zoom) {
      setTimeout(() => {
        this.center = this.nowCenter;
      }, 50);
      setTimeout(() => {
        this.center = loc;
        this.zoom = zoom;
      }, 50);
    },
    getCurrentCenter(center) {
      this.nowCenter = { lat: center.lat(), lng: center.lng() };
      this.$emit("getCenter", center.lat(), center.lng());
    },
    getCurrentZoom(zoom) {
      this.zoom = zoom;
    },
  },
  watch: {
    spotInfo(newVal) {
      if (!newVal) {
        this.isInfo = false;
        return;
      }
      this.isInfo = true;
      this.clickLocation(newVal, 15);
    },
    tripArea(newVal) {
      this.center = newVal[0];
      this.nowCenter = newVal[0];
    },
    timetableList(newVal) {
      this.planList = newVal;
    },
  },
  destroyed() {
    this.SET_SPOT_INFO(null);
  },
};
</script>

<style>
a[href^="http://maps.google.com/maps"]
{
  display: none !important;
}
a[href^="https://maps.google.com/maps"]
{
  display: none !important;
}
.gmnoprint a,
.gmnoprint span,
.gm-style-cc {
  display: none;
}
.gmnoprint div {
  background: none !important;
}
.gmnoprint div {
  display: none;
}
.gm-fullscreen-control {
  display: none;
}
.mapAreaBtn {
  width: 80px;
  position: absolute;
  left: 10px;
  top: 10px;
  z-index: 1;
}
</style>
