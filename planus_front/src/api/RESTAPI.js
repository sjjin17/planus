import axios from "axios";

//const baseURL = "https://k7a505.p.ssafy.io/planus";
const baseURL = "http://localhost:8080/planus";

const API = {
  instance: axios.create({
    baseURL,
    headers: {
      "Content-Type": "application/json",
    },
  }),
  async getAllArea() {
    const response = await this.instance.get("/trip/area");
    return response.data;
  },
  async createTrip(admin, areaId, startDate, period) {
    const response = await this.instance.post("/trip", {
      admin: admin,
      areaId: areaId,
      startDate: startDate,
      period: period,
    });
    return response.data;
  },
  async getTripInfo(tripUrl) {
    const response = await this.instance.get("/trip" + "?tripUrl=" + tripUrl);
    return response.data;
  },
  async getMemberList(tripId) {
    const response = await this.instance.get(
      "/trip/member" + "?tripId=" + tripId
    );
    return response.data;
  },
};

export default API;
