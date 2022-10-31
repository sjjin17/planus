<template>
  <div>
    <chat-tab :chatList="chatList" @sendMessage="sendChat"></chat-tab>
    <recommend-place-tab
      :lat="lat"
      :lng="lng"
      :size="size"
      :length="pageLength"
      @addBucket="addBucket"
    ></recommend-place-tab>
  </div>
</template>

<script>
import ChatTab from "./ChatTab.vue";
import RecommendPlaceTab from "../recommend/RecommendPlaceTab.vue";
import WSAPI from "@/api/WSAPI";

const ws = WSAPI;
export default {
  data: () => {
    return {
      tripId: 1,
      userName: "김",
      message: "",
      chatList: [],
      lat: 37.5168415735,
      lng: 127.0341090296,
      size: 10,
      pageLength: 0,
      bucketList: [],
    };
  },
  mounted() {
    this.connect();
  },
  components: {
    ChatTab,
    RecommendPlaceTab,
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
        case 2:
          console.log(content);
        // this.bucketList.push(content);
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
    addBucket(place, address, lat, lng) {
      if (this.userName) {
        if (ws.stomp && ws.stomp.connected) {
          ws.addBucket(this.tripId, place, address, lat, lng);
        }
      }
    },
  },
};
</script>

<style></style>
