<template>
  <div>
    <h1>{{ this.tripId }}번 방</h1>
    <invite-dialog
      :tripId="tripId"
      :tripUrl="tripUrl"
      :admin="admin"
      :connector="connector"
      @getConnector="getConnector"
    ></invite-dialog>
    <div>
      <v-tabs v-model="planTabs" fixed-tabs>
        <v-tab v-for="plan in planIdList" :key="plan.planId"
          >{{ plan.tripDate[1] }} / {{ plan.tripDate[2] }}</v-tab
        >
      </v-tabs>
    </div>
    <v-container d-flex style="margin: 0; max-width: 100%">
      <v-container
        style="width: 20%; margin: 0; min-width: 300px; max-height: 100%"
      >
        <v-tabs v-model="tabs" fixed-tabs>
          <v-tab style="padding: 0">장소검색</v-tab>
          <v-tab style="padding: 0">버킷리스트</v-tab>
          <v-tab style="padding: 0" @click="recommendClick">추천관광지 </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs">
          <v-tab-item>
            <div>장소검색 컴포넌트</div>
          </v-tab-item>
          <v-tab-item>
            <bucket-list
              :tripId="tripId"
              @delBucket="delBucket"
              :deletedBucket="deletedBucket"
              :addedBucket="addedBucket"
              :memberOrAdmin="memberOrAdmin"
              @addTimetable="addTimetable"
              :addedTimetable="addedTimetable"
              style="overflow-y: scroll; height: 500px"
            ></bucket-list>
          </v-tab-item>
          <v-tab-item>
            <recommend-place-tab
              :lat="lat"
              :lng="lng"
              :size="size"
              :isRecommendClick="isRecommendClick"
              @addBucket="addBucket"
              @addTimetable="addTimetable"
            ></recommend-place-tab>
          </v-tab-item>
        </v-tabs-items>
        <chat-tab
          :chatList="chatList"
          @sendMessage="sendChat"
          style="
            position: absolute;
            left: 0;
            bottom: 0;
            background-color: #4a8072;
          "
        />
      </v-container>
      <plan-map
        style="width: 60%; background-color: blue"
        @getCenter="getCenter"
      />
      <v-container
        style="width: 20%; margin: 0; min-width: 300px; max-height: 100%"
      >
        <v-tabs-items v-model="planTabs">
          <v-tab-item v-for="plan in planIdList" :key="plan.planId">
            <plan-list
              :plan="plan"
              :tripId="tripId"
              @setPlan="setPlan"
              @setTimetable="setTimetable"
            ></plan-list>
          </v-tab-item>
        </v-tabs-items>
      </v-container>
    </v-container>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import WSAPI from "@/api/WSAPI";
import RecommendPlaceTab from "@/components/recommend/RecommendPlaceTab.vue";
import PlanMap from "@/components/plans/PlanMap.vue";
import InviteDialog from "@/components/manageTrip/inviteDialog.vue";
import BucketList from "@/components/bucketList/BucketList.vue";
import jwt_decode from "jwt-decode";
import ChatTab from "@/components/chat/ChatTab.vue";
import PlanList from "@/components/plans/PlanList.vue";

const ws = WSAPI;
const api = API;
export default {
  name: "PlanView",
  components: {
    PlanMap,
    InviteDialog,
    RecommendPlaceTab,
    BucketList,
    ChatTab,
    PlanList,
  },
  data() {
    return {
      tabs: null,
      dialog: false,
      tripId: 0,
      tripUrl: "",
      admin: 0,
      memberOrAdmin: 0,
      lat: 37.5168415735,
      lng: 127.0341090296,
      size: 5,
      token: "Bearer " + this.$cookies.get("token"),
      nickname: "",
      userId: 0,
      chatList: [],
      connector: [],
      isRecommendClick: false,
      deletedBucket: {},
      addedBucket: {},
      // plan
      addedTimetable: {},

      planIdList: [],
      planTabs: null,
    };
  },
  async created() {
    this.tripUrl = this.$route.params.tripUrl;
    await this.getTripInfo();
    await this.getPlanId(this.tripId);
    this.decoding();
  },
  methods: {
    async getTripInfo() {
      let data = await api.getTripInfo(this.tripUrl).catch(() => {
        window.alert("존재하지 않는 url입니다!");
        this.$router.push("/");
      });
      let result = data.result;
      this.tripId = result.tripId;
      this.admin = result.admin;
      this.memberOrAdmin = result.memberOrAdmin;
      if (result.complete) {
        this.$router.push("/complete/" + this.tripUrl);
      } else {
        switch (this.memberOrAdmin) {
          case -1:
            window.alert(
              "일정짜기가 진행중인 경우 로그인한 사용자만 입장 가능합니다!"
            );
            this.$router.push("/");
            break;
          case 0:
            this.addMember();
            break;
          case 1:
            console.log("참가자입니다.");
            this.connect();
            break;
          case 2:
            console.log("방장입니다.");
            this.connect();
            break;
        }
      }
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
        this.connect();
      }
      console.log(this.res.memberId);
    },
    connect() {
      ws.connect(this.tripId, this.token, this.onSocketReceive);
    },
    async onSocketReceive(result) {
      const content = JSON.parse(result.body);
      switch (content.action) {
        case 0:
          this.connector = content.connector;
          break;
        case 1:
          this.chatList.push(content.userName + ": " + content.chatMsg);
          break;
        case 2:
          console.log(content);
          // TODO: 버킷리스트 추가
          this.addedBucket = {
            place: content.place,
            address: content.address,
            lat: content.lat,
            lng: content.lng,
          };
          break;
        case 3:
          console.log(content);
          // TODO: 버킷리스트 삭제
          this.deletedBucket = {
            place: content.place,
            address: content.address,
            lat: content.lat,
            lng: content.lng,
          };
          break;
        case 4:
          console.log(content);
          // TODO: 일정(plan)변경
          break;
        case 5:
          console.log(content);
          // TODO: 일정(timetable)추가
          this.addedTimetable = {
            costTime: content.costTime,
            place: content.place,
            lat: content.lat,
            lng: content.lng,
            fromBucket: content.fromBucket,
            address: content.address,
          };
          break;
        case 6:
          console.log(content);
          // TODO: 일정(timetable)삭제
          break;
      }
    },
    getConnector() {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.getConnector({
            tripId: this.tripId,
            token: this.token,
          });
        }
      }
    },
    sendChat(message) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.chat({
            userName: this.nickname,
            chatMsg: message,
            tripId: this.tripId,
          });
        }
      }
    },
    addBucket(place, address, lat, lng) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.addBucket(this.tripId, place, address, lat, lng);
        }
      }
    },
    addTimetable(costTime, place, lat, lng, fromBucket, address) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.addTimetable(
            this.tripId,
            this.planId,
            costTime,
            place,
            lat,
            lng,
            fromBucket,
            address
          );
        }
      }
    },
    decoding() {
      let decode = jwt_decode(this.token);
      this.nickname = decode.nickname;
      this.userId = decode.userId;
    },
    getCenter(lat, lng) {
      this.lat = lat;
      this.lng = lng;
    },
    recommendClick() {
      this.isRecommendClick = !this.isRecommendClick;
    },
    delBucket(bucket) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.delBucket(
            this.tripId,
            bucket.place,
            bucket.address,
            bucket.lat,
            bucket.lng
          );
        }
      }
    },
    async getPlanId(tripId) {
      await api.getPlanId(tripId).then((res) => {
        this.planIdList = res.planIdList;
      });
      console.log(this.planIdList);
    },
    setPlan(newPlan) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.setPlan(
            this.tripId,
            newPlan.planId,
            newPlan.tripDate,
            newPlan.startTime
          );
          console.log("새Plan" + newPlan.planId);
        }
      }
    },
    setTimetable(newTimetable, planId) {
      if (this.token) {
        if (ws.stomp && ws.stomp.connected) {
          ws.setTimetable(
            this.tripId,
            planId,
            newTimetable.place,
            newTimetable.lat,
            newTimetable.lng,
            newTimetable.orders,
            newTimetable.costTime,
            newTimetable.moveTime,
            newTimetable.transit,
            false
          );
        }
      }
    },
  },
};
</script>

<style>
.v-slide-group__prev {
  display: none !important;
}
.v-slide-group__next {
  display: none !important;
}
</style>
