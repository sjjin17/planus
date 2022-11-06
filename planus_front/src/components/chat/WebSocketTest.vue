<template>
  <div>
    <voice-chat :tripId="tripId" :userId="userId"></voice-chat>
  </div>
</template>

<script>
import VoiceChat from "@/components/chat/VoiceChat.vue";

export default {
  data() {
    return {
      tripId: 1,
      userId: Math.floor(Math.random() * 10),
    };
  },
  components: {
    VoiceChat,
  },
};
</script>

<style></style>
