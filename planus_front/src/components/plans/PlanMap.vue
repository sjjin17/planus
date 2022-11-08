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
          text: m.name.substring(0, 2),
          color: '#4A8072',
          fontWeight: 'bold',
        }"
        @click="clickLocation(m, 15)"
        id="bucket"
      ></gmap-marker>
      <gmap-marker
        :key="index + 'p'"
        v-for="(m, index) in planList"
        :position="m"
        :label="{
          text: m.name.substring(0, 2),
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
        :path.sync="planList"
        :options="{ strokeColor: '#FF1744', strokeWeight: 2 }"
      >
      </gmap-polyline>
    </gmap-map>
  </div>
</template>

<script>
import imgpath from "@/assets/logo.png";

export default {
  name: "PlanMap",

  data() {
    return {
      img: {
        url: imgpath,
        scaledSize: { width: 30, height: 30 },
      },
      center: { lat: 37.5400456, lng: 126.9921017 },
      zoom: 12,
      nowCenter: {},
      bucketList: [
        { label: "C", name: "코엑스몰", lat: 37.5115557, lng: 127.0595261 },
        { label: "G", name: "고투몰", lat: 37.5062379, lng: 127.0050378 },
        { label: "D", name: "동대문시장", lat: 37.566596, lng: 127.007702 },
        { label: "I", name: "IFC몰", lat: 37.5251644, lng: 126.9255491 },
      ],
      planList: [
        {
          label: "L",
          name: "롯데월드타워몰",
          lat: 37.5125585,
          lng: 127.1025353,
        },
        { label: "M", name: "명동지하상가", lat: 37.563692, lng: 126.9822107 },
        { label: "T", name: "타임스퀘어", lat: 37.5173108, lng: 126.9033793 },
      ],
    };
  },
  props: {
    tripArea: Array,
  },
  methods: {
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
      this.nowCenter = center;
      this.$emit("getCenter", center.lat(), center.lng());
    },
    getCurrentZoom(zoom) {
      this.zoom = zoom;
    },
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
