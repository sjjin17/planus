import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const WS_SERVER_URL = "http://localhost:8080/planus/ws";

const WSAPI = {
  socket: null,
  stomp: null,
  connect(trip_id, user_name, callback) {
    this.socket = new SockJS(WS_SERVER_URL);
    this.stomp = Stomp.over(this.socket);
    this.stomp.debug = () => {
      return;
    };
    this.stomp.connect(
      {},
      () => {
        this.stomp.subscribe("/topic/planus/{trip_id}", callback);
        this.enter({ trip_id, user_name });
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
