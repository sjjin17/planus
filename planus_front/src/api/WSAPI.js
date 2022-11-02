import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

//const WS_SERVER_URL = "http://localhost:8080/planus/ws";
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
        this.stomp.subscribe("/topic/planus/{tripId}", callback);
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
  addBucket(tripId, place, address, lat, lng) {
    let bucket = {
      tripId: tripId,
      place: place,
      address: address,
      lat: lat,
      lng: lng,
    };
    this.stomp.send("/app/addBucket", JSON.stringify(bucket));
  },
  delBucket(tripId, place, address, lat, lng) {
    let bucket = {
      tripId: tripId,
      place: place,
      address: address,
      lat: lat,
      lng: lng,
    };
    this.stomp.send("/app/delBucket", JSON.stringify(bucket));
  },
  setPlan(tripId, planId, tripDate, startTime) {
    let plan = {
      tripId: tripId,
      planId: planId,
      tripDate: tripDate,
      startTime: startTime,
    };
    this.stomp.send("app/setPlan", JSON.stringify(plan));
  },

  addTimetable(tripId, planId, hours, minutes, place, lat, lng) {
    let timetable = {
      tripId: tripId,
      planId: planId,
      hours: hours,
      minutes: minutes,
      place: place,
      lat: lat,
      lng: lng,
    };
    this.stomp.send("/app/addTimetable", JSON.stringify(timetable));
  },
  delTimetable(timetableList) {
    this.stomp.send("/app/delTimetable", JSON.stringify(timetableList));
  },
};

export default WSAPI;
