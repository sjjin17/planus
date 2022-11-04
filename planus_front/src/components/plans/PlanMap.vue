<template>
  <div style="position: relative">
    <div class="mapAreaBtn">
      <v-btn
        v-for="(area, areaIdx) in tripArea"
        :key="areaIdx"
        @click="center = area"
        >{{ area.siName }}</v-btn
      >
    </div>
    <gmap-map
      :zoom="10"
      :center="center"
      style="width: 100%; height: 600px"
      @bounds_changed="moveCenter"
    >
      <gmap-marker
        :icon="img"
        :key="index + 'i'"
        v-for="(m, index) in locationMarkers"
        :position="m"
        :label="m.label"
        @click="center = m"
      ></gmap-marker>
      <gmap-marker
        :key="idx + 'd'"
        v-for="(m, idx) in locPlaces"
        :position="m"
        :label="m.label"
        @click="center = m"
      ></gmap-marker>
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
      locationMarkers: [
        { label: "C", name: "코엑스몰", lat: 37.5115557, lng: 127.0595261 },
        { label: "G", name: "고투몰", lat: 37.5062379, lng: 127.0050378 },
        { label: "D", name: "동대문시장", lat: 37.566596, lng: 127.007702 },
        { label: "I", name: "IFC몰", lat: 37.5251644, lng: 126.9255491 },
      ],
      locPlaces: [
        {
          label: "L",
          name: "롯데월드타워몰",
          lat: 37.5125585,
          lng: 127.1025353,
        },
        { label: "M", name: "명동지하상가", lat: 37.563692, lng: 126.9822107 },
        { label: "T", name: "타임스퀘어", lat: 37.5173108, lng: 126.9033793 },
      ],
      existingPlace: null,
    };
  },
  props: {
    tripArea: Array,
  },
  methods: {
    moveCenter(newCoordinates) {
      if (!newCoordinates) return;
      this.$emit(
        "getCenter",
        newCoordinates.eb.center(),
        newCoordinates.Ha.center()
      );
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
.gm-style div {
  color: white !important;
}
.mapAreaBtn {
  width: 80px;
  position: absolute;
  left: 10px;
  top: 10px;
  z-index: 1;
}
</style>
