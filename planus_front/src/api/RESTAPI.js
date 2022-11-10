import axios from "axios";
import VueCookies from "vue-cookies";

const baseURL = process.env.VUE_APP_API_URL;

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
baseAxios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.data.status == 403) {
      console.log("권한인증 실패");
      window.location.href = "/login/redirect";
      // temp.$router.push("/login/redirect");
    }
  }
);

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
  async getBucketList(tripId) {
    const response = await this.instance.get("/buckets/" + tripId);
    return response.data;
  },
  async saveBucketList(tripId) {
    const response = await this.instance.post("/buckets/" + tripId);
    return response.data;
  },
  async getMyTripMade(page) {
    const response = await this.instance.get("/mytrip/made", {
      params: { page },
    });
    return response.data;
  },
  async getMyTripShared(page) {
    const response = await this.instance.get("/mytrip/shared", {
      params: { page },
    });
    return response.data;
  },
  async getPlanId(tripId) {
    const response = await this.instance.get("/plans/" + tripId);
    return response.data;
  },
  async getPlanList(planIdList) {
    const response = await this.instance.post("/plans/planlist", planIdList);
    return response.data;
  },
  async savePlanStart(planId) {
    const response = await this.instance.get("/plans/start/" + planId);
    return response.data;
  },
  async savePlan(planIdList) {
    const response = await this.instance.post("/plans", planIdList);
    return response.data;
  },
  async deleteTrip(tripId) {
    const response = await this.instance.delete("/mytrip", {
      params: { tripId },
    });
    return response.data;
  },
  async addComment(articleId, content) {
    const response = await this.instance.post("/comment", {
      articleId: articleId,
      content: content,
    });
    return response.data;
  },
  async modifyComment(commentId, content) {
    const response = await this.instance.put("/comment", {
      commentId: commentId,
      content: content,
    });
    return response.data;
  },
  async getArticleComment(articleId, page) {
    const response = await this.instance.get(
      "/comment" + "?articleId=" + articleId,
      {
        params: { page },
      }
    );
    return response.data;
  },
  async getMyComment(page) {
    const response = await this.instance.get("/comment", {
      params: { page },
    });
    return response.data;
  },
  async delComment(commentId) {
    const response = await this.instance.delete(
      "/comment" + "?commentId=" + commentId
    );
    return response.data;
  },
  async getArticleListByTitle(title, page) {
    const response = await this.instance.get(
      "/articles/title" + "?title=" + title,
      {
        params: { page },
      }
    );
    return response.data;
  },
  async getArticleListByArea(area, page) {
    const response = await this.instance.get(
      "/articles/area" + "?area=" + area,
      {
        params: { page },
      }
    );
    return response.data;
  },
  async getArticleListByAreaLength(area) {
    const response = await this.instance.get(
      "/articles/area/length" + "?area=" + area
    );
    return response.data;
  },
};

export default API;
