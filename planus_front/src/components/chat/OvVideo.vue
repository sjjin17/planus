<template>
  <div id="userBtn">
    <audio :ref="client" :id="client" style="display: none"></audio>
    <v-btn
      disabled
      fab
      small
      style="
        margin: 3px 0px 0px 10px;
        font-size: 1.5rem;
        color: #544c4c !important;
      "
      :style="talkingCss"
    >
      {{ client.slice(0, 1) }}
    </v-btn>
    <div id="userBtn-content">{{ client }}</div>
  </div>
</template>

<script>
export default {
  name: "OvVideo",

  data() {
    return {
      backgroundColor: [
        "#B8DBC6",
        "#FFB4C2",
        "#FFFFFF",
        "#9CFFE6",
        "#FFD0C2",
        "#E6FFCF",
        "F6A8FF",
        "#DFFFC2",
        "#BFF2E2",
        "#FFF3CF",
      ],
      talkingCss:
        "border-color: #544C4C; border-style: groove; border-radius:100px;",
    };
  },
  props: {
    streamManager: Object,
    client: String,
    idx: Number,
  },
  computed: {
    backgroundColorCSS() {
      return (
        "background-color :" + this.backgroundColor[this.idx] + " !important;"
      );
    },
  },
  mounted() {
    this.init();
    this.talkingCss += this.backgroundColorCSS;
  },
  methods: {
    async init() {
      await this.streamManager.addVideoElement(this.$refs[this.client]);
      this.streamManager.on("publisherStartSpeaking", () => {
        this.isTalking(true);
      });
      this.streamManager.on("publisherStopSpeaking", () => {
        this.isTalking(false);
      });
    },
    isTalking(boolean) {
      if (boolean) {
        this.talkingCss =
          "border-color: #FF1744; border-style: groove; border-radius:100px;" +
          this.backgroundColorCSS;
      } else {
        this.talkingCss =
          "border-color: #544C4C; border-style: groove; border-radius:100px;" +
          this.backgroundColorCSS;
      }
    },
  },
};
</script>
<style scoped>
#userBtn {
  position: relative;
  display: inline-block;
}

#userBtn-content {
  display: none !important;
  position: absolute;
  z-index: 1; /*다른 요소들보다 앞에 배치*/
}

#userBtn:hover #userBtn-content {
  display: block !important;
}
</style>
