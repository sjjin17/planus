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
  async getTripInfo(tripId) {
    const response = await this.instance.get("/trip" + "?tripId=" + tripId);
    return response.data;
  },
  async getMemberList(tripId) {
    const response = await this.instance.get(
      "/trip/member" + "?tripId=" + tripId
    );
    return response.data;
  },
  async getRecommend(lat, lng, page, size) {
    const response = await this.instance.get("/recommend", {
      params: { lat: lat, lng: lng, page: page, size: size },
    });
    return response.data;
  },
  async getRecommendPageLength(lat, lng, size) {
    const response = await this.instance.get("/recommend/length", {
      params: { lat: lat, lng: lng, size: size },
    });
    return response.data;
  },
};

export default API;
