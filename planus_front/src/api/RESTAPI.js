import axios from "axios";
import VueCookies from "vue-cookies";

const baseURL = "https://k7a505.p.ssafy.io/planus";
// const baseURL = "http://localhost:8080/planus";

const baseAxios = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

baseAxios.interceptors.request.use((request) => {
  if (VueCookies.get("token") != null) {
    request.headers.Authorization = "Bearer " + VueCookies.get("token");
  }
  return request;
});

const API = {
  instance: baseAxios,
  async getAllArea() {
    const response = await this.instance.get("/trip/area");
    return response.data;
  },
  async createTrip(startDate, period, areaId) {
    const response = await this.instance.post("/trip", {
      startDate: startDate,
      period: period,
      areaId: areaId,
    });
    return response.data;
  },
  async getTripInfo(tripUrl) {
    const response = await this.instance.get("/trip" + "?tripUrl=" + tripUrl);
    return response.data;
  },
  async addMember(tripId) {
    const response = await this.instance.post(
      "/trip/member" + "?tripId=" + tripId
    );
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
  async signOut() {
    const response = await this.instance.get("/login/signout");
    return response.data;
  },
  async getMyInfo() {
    const response = await this.instance.get("/mypage");
    return response.data.result;
  },

  async changeMyInfo(newNickname) {
    const response = await this.instance.put("/mypage", {
      nickname: newNickname,
    });
    return response.data;
  },
};

export default API;
