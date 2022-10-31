<template>
  <v-container>
    <plan-map />
    <h1>{{ this.tripId }}번 방</h1>
    <div v-for="(member, i) in memberList" :key="i" :member="member">
      {{ member.name }}({{ member.email }})
    </div>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

import PlanMap from "@/components/plans/PlanMap.vue";

export default {
  name: "PlanView",
  components: {
    PlanMap,
  },
  data() {
    return {
      tripId: 0,
      tripUrl: "",
      memberOrAdmin: 0,
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
    this.isLogin();
    await this.getTripInfo();
    await this.getMemberList();
  },
  methods: {
    isLogin() {
      if (!this.$cookies.get("token")) {
        window.alert("로그인 해주세요!");
        this.$router.push("/");
      }
    },
    async getTripInfo() {
      this.res = await api.getTripInfo(this.tripUrl).catch(() => {
        window.alert("존재하지 않는 url입니다!");
        this.$router.push("/");
      });
      this.result = this.res.result;
      this.tripId = this.result.tripId;
      this.memberOrAdmin = this.result.memberOrAdmin;
      if (this.result.complete) {
        this.$router.push("/complete/" + this.tripUrl);
      } else {
        switch (this.memberOrAdmin) {
          case 0:
            this.addMember();
            break;
          case 1:
            console.log("참가자입니다.");
            break;
          case 2:
            console.log("방장입니다.");
            break;
        }
      }
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
      console.log(this.memberList);
    },
    async addMember() {
      this.res = await api.addMember(this.tripId);
      if (this.res.memberId == -2) {
        window.alert("이미 참가자로 등록되어있습니다!");
        this.$router.push("/");
      } else if (this.res.memberId == -1) {
        window.alert("정원 10명이 마감되어 참가할 수 없습니다!");
        this.$router.push("/");
      } else {
        console.log("참가자로 등록합니다.");
      }
      console.log(this.res.memberId);
    },
  },
};
</script>

<style></style>
