<template>
  <v-container>
    <plan-map />
    <h1>{{ this.tripId }}번 방</h1>
    <div v-for="(member, i) in memberList" :key="i" :member="member">
      {{ member.name }}({{ member.email }})
    </div>
    <bucket-list
      :trip-id="tripId"
    ></bucket-list>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

import PlanMap from "@/components/plans/PlanMap.vue";
import BucketList from "@/components/bucketList/BucketList.vue";

export default {
  name: "PlanView",
  components: {
    PlanMap,
    BucketList,
  },
  data() {
    return {
      userId: 0,
      isMember: false,
      isAdmin: false,
      tripId: 0,
      tripUrl: "",
      result: {
        tripId: 0,
        admin: 0,
        startDate: "",
        period: 0,
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
    this.getCookie();
    await this.getTripInfo();
    await this.getMemberList();
    this.checkAdminAndMember();
  },
  methods: {
    getCookie() {
      let token = this.$cookies.get("token");
      if (token) {
        this.userId = 103;
      } else {
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
      if (this.result.complete) {
        this.$router.push("/complete/" + this.tripUrl);
      }
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
      console.log(this.memberList);
    },
    checkAdminAndMember() {
      if (this.userId == this.result.admin) {
        console.log("방장임");
        this.isAdmin = true;
      } else {
        for (var i = 0; i < this.memberList.length; i++) {
          if (this.userId == this.memberList[i].userId) {
            console.log("참가자임");
            this.isMember = true;
            break;
          }
        }
        if (!this.isMember) {
          window.alert("참가자로 등록합니다!");
        }
      }
    },
  },
};
</script>

<style></style>
