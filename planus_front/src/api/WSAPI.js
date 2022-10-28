import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

// const WS_SERVER_URL = "http://localhost:8080/planus/ws";
const WS_SERVER_URL = "https://k7a505.p.ssafy.io/planus/ws";

const WSAPI = {
  socket: null,
  stomp: null,
  connect(tripId, userName, callback) {
    this.socket = new SockJS(WS_SERVER_URL);
    this.stomp = Stomp.over(this.socket);
    this.stomp.debug = () => {
      return;
    };
    this.stomp.connect(
      {},
      () => {
        this.stomp.subscribe("/topic/planus/{trip_id}", callback);
        this.enter({ tripId, userName });
      },
      (error) => {
        console.log(error);
        this.connect = false;
      }
    );
  },
  enter(message) {
    this.stomp.send("/app/enter", JSON.stringify(message));
  },
  chat(message) {
    this.stomp.send("/app/chat", JSON.stringify(message));
  },
};

export default WSAPI;
