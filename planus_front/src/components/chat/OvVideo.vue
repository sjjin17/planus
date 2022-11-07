<template>
  <div>
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
    talkingCss() {
      return (
        "border-color: #544C4C; border-style: groove; border-radius:100px;" +
        this.backgroundColorCSS
      );
    },
  },
  mounted() {
    this.init();
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
          "border-color: #FF1744; border-style: groove; border-radius:100px;";
      } else {
        this.talkingCss =
          "border-color: #544C4C; border-style: groove; border-radius:100px;";
      }
    },
  },
};
</script>
