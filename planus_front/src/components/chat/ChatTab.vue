<template>
  <div style="width: 20%; min-width: 300px; padding-top: 3px; margin-left: 5px">
    <v-icon class="chatButton" v-if="isChatOpen" @click="chatButton"
      >mdi-chevron-double-down</v-icon
    >
    <v-icon class="chatButton" @click="chatButton" v-else>
      mdi-chevron-double-up
    </v-icon>
    <div no-gutters class="chat_body" :style="chatBodyStyle" v-chat-scroll>
      <div class="chat_message" v-for="(chat, idx) in chatList" :key="idx">
        {{ chat }}
      </div>
    </div>
    <!-- 채팅입력 -->
    <div class="form">
      <input
        class="form_input"
        style="background-color: white"
        type="text"
        placeholder="채팅을 입력하세요."
        v-model="message"
        @keyup.enter="sendEvent"
      />
      <div @click="sendEvent" class="form_submit">
        <svg
          width="30"
          height="30"
          viewBox="0 0 68 68"
          fill="#4a8072"
          xmlns="http://www.w3.org/2000/svg"
        >
          <g clip-path="url(#clip0_26_10)">
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M48.0833 19.799C48.619 20.3347 48.806 21.127 48.5665 21.8457L35.8385 60.0294C35.5946 60.7614 34.9513 61.2877 34.1855 61.382C33.4198 61.4763 32.6681 61.1217 32.2539 60.4707L22.593 45.2893L7.41158 35.6285C6.76065 35.2142 6.40604 34.4625 6.50031 33.6968C6.59458 32.931 7.12092 32.2878 7.85287 32.0438L46.0366 19.3159C46.7553 19.0763 47.5476 19.2633 48.0833 19.799ZM26.5903 44.1204L33.3726 54.7782L42.0926 28.6181L26.5903 44.1204ZM39.2642 25.7897L23.7619 41.292L13.1041 34.5097L39.2642 25.7897Z"
              fill=""
            />
          </g>
          <defs>
            <clipPath id="clip0_26_10">
              <rect
                width="48"
                height="48"
                fill="white"
                transform="translate(33.9412) rotate(45)"
              />
            </clipPath>
          </defs>
        </svg>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      isChatOpen: false,
      message: "",
    };
  },
  props: {
    chatList: Array,
  },
  computed: {
    chatBodyStyle() {
      if (this.isChatOpen) {
        return " height: 400px";
      } else {
        return " height: 42px";
      }
    },
  },
  methods: {
    sendEvent() {
      if (this.message !== "") {
        this.$emit("sendMessage", this.message);
        this.message = "";
      }
    },
    chatButton() {
      this.isChatOpen = !this.isChatOpen;
    },
  },
};
</script>

<style scoped>
.chat_body {
  padding-left: 1rem;
  margin: auto;
  overflow-y: auto;
  font-size: 0.9rem;
  font-weight: 6;
}
.chat_body::-webkit-scrollbar {
  color: "#00000000";
  width: 10px;
}
.chat_body::-webkit-scrollbar-thumb {
  background-color: white;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
  border-color: #00000000;
}
.chat_message {
  color: white;
  margin-top: 10px;
  margin-bottom: 10px;
  word-wrap: break-word;
}
.form {
  display: flex;
  justify-content: space-between;
  background-color: white;
  border-radius: 10px;
  margin-left: 5px;
  margin-right: 5px;
  margin-bottom: 5px;
}

.form_input {
  border: none;
  border-radius: 10px;
  padding: 0.5rem;
  font-size: 16px;
  width: calc(100% - 60px);
}

.form_input:focus {
  outline: none;
}

.form_submit {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-right: 5px;
  margin-bottom: 5px;
}

svg {
  transition: 0.3s;
}

svg:hover {
  fill: #3c3c3c;
}
.chatButton {
  margin-top: 10px;
  position: absolute;
  right: 10px;
  color: white;
}
.theme--light.v-icon:focus::after {
  opacity: 0;
}
</style>
