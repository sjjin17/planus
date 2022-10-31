<template>
  <div class="home">
    <h1>{{ this.tripId }}번 방</h1>
    <h1>완료 페이지입니다.</h1>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  name: "CompleteView",
  components: {},
  data() {
    return {
      tripId: 0,
      tripUrl: "",
      result: {
        tripId: 0,
        admin: 0,
        startDate: "",
        period: 0,
        memberOrAdmin: 0,
        complete: false,
        imageUrl: "",
        tripArea: [
          {
            areaId: 0,
            doName: "",
            siName: "",
            lat: 0,
            lng: 0,
          },
        ],
      },
      memberList: [
        {
          userId: 0,
          name: "",
          email: "",
        },
      ],
    };
  },
  async created() {
    this.tripUrl = this.$route.params.tripUrl;
    await this.getTripInfo();
    await this.getMemberList();
  },
  methods: {
    async getTripInfo() {
      this.res = await api.getTripInfo(this.tripUrl).catch(() => {
        window.alert("존재하지 않는 url입니다!");
        this.$router.push("/");
      });
      this.result = this.res.result;
      this.tripId = this.result.tripId;
      if (!this.result.complete) {
        this.$router.push("/plans/" + this.tripUrl);
      }
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
    },
  },
};
</script>
