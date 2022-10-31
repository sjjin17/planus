<template>
  <chat-tab :chatList="chatList" @sendMessage="sendChat"></chat-tab>
</template>

<script>
import ChatTab from "./ChatTab.vue";
import WSAPI from "@/api/WSAPI";
const ws = WSAPI;
export default {
  data: () => {
    return {
      tripId: 1,
      userName: "김",
      message: "",
      chatList: [],
    };
  },
  mounted() {
    this.connect();
  },
  components: {
    ChatTab,
  },
  methods: {
    connect() {
      ws.connect(this.tripId, this.userName, this.onSocketReceive);
    },
    async onSocketReceive(result) {
      const content = JSON.parse(result.body);
      switch (content.action) {
        case 1:
          this.chatList.push(content.userName + ": " + content.chatMsg);
          break;
      }
    },
    sendChat(message) {
      if (this.userName) {
        if (ws.stomp && ws.stomp.connected) {
          ws.chat({
            userName: this.userName,
            chatMsg: message,
            tripId: this.tripId,
          });
        }
      }
    },
  },
};
</script>

<style></style>
