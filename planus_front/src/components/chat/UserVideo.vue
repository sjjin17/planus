<template>
  <div v-if="streamManager" style="position: relative">
    <v-icon
      v-if="!streamManager.stream.audioActive"
      style="position: absolute; color: red"
      small
      >mdi-microphone-off</v-icon
    >
    <ov-video :stream-manager="streamManager" :client="clientData" :idx="idx" />
  </div>
</template>

<script>
import OvVideo from "@/components/chat/OvVideo.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    idx: Number,
  },
  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
