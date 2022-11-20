<template>
  <v-dialog v-model="dialog" max-width="900">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        depressed
        color="#4A8072"
        outlined
        v-bind="attrs"
        v-on="on"
        @click="getMemberList"
        class="mr-1"
      >
        초대
      </v-btn>
    </template>
    <v-card style="margin: auto">
      <v-card-actions>
        <v-spacer></v-spacer>
      </v-card-actions>
      <v-card-title class="dialogtitle">
        <v-row>
          <v-col style="font-size: 1.2rem; font-weight: 700; color: #383d3c"
            >참가자 목록
          </v-col>
          <v-col class="text-right" cols="2">
            <v-btn icon large>
              <img
                class="share"
                src="@/assets/kakaoLogo.png"
                @click="kakaoShare"
              />
            </v-btn>
            &nbsp;
            <v-btn icon large>
              <img class="share" src="@/assets/link.png" @click="copyUrl" />
            </v-btn>
          </v-col>
        </v-row>
      </v-card-title>
      <v-card-text v-for="(member, i) in memberList" :key="i" :member="member">
        <v-row align="center">
          <v-col>
            <v-btn
              depressed
              rounded
              color="#4A8072"
              v-if="admin == member.userId"
              ><span class="font-weight-bold" style="color: white"
                >방장</span
              ></v-btn
            >
            <v-btn
              depressed
              rounded
              color="#544C4C"
              v-else-if="admin == userId"
              @click="changeAdmin(member.userId)"
              ><span class="font-weight-bold" style="color: white"
                >방장변경</span
              ></v-btn
            >
          </v-col>
          <v-col>
            <div
              style="
                color: rgb(56, 61, 60, 80%);
                font-size: 1rem;
                font-weight: 600;
              "
            >
              {{ member.name }}
            </div>
          </v-col>
          <v-col cols="4">
            <div
              style="color: rgb(56, 61, 60, 50%); font-weight: 600"
              v-show="member.email != null"
            >
              ({{ member.email }})
            </div>
          </v-col>
          <v-col>
            <v-btn
              depressed
              rounded
              color="#4A8072"
              v-if="connector.includes(member.userId + '')"
              ><span class="font-weight-bold" style="color: white"
                >접속</span
              ></v-btn
            >
            <v-btn
              class="font-weight-bold"
              depressed
              rounded
              outlined
              color="#4A8072"
              white--text
              v-else
              >접속안함</v-btn
            >
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  data() {
    return {
      dialog: false,
      memberList: [
        {
          userId: 0,
          name: "",
          email: "",
        },
      ],
    };
  },
  props: {
    tripId: Number,
    userId: Number,
    tripUrl: String,
    admin: Number,
    connector: Array,
  },
  created() {},
  methods: {
    async getMemberList() {
      this.$emit("getConnector");
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
    },
    copyUrl() {
      navigator.clipboard.writeText(window.location.href).then(() => {
        window.alert("링크가 복사되었습니다! 친구를 초대해보세요!");
      });
    },
    changeAdmin(newAdminId) {
      this.$emit("changeAdmin", newAdminId);
    },
    kakaoShare() {
      let imgUrl =
        "https://cdn.pixabay.com/photo/2017/12/15/13/51/polynesia-3021072_960_720.jpg";
      let pgUrl = window.location.href;
      this.$kakao.Link.sendDefault({
        objectType: "feed",
        content: {
          title: "planus",
          description: "친구와 함께 여행일정 계획하러가기!!",
          imageUrl: imgUrl,
          link: {
            mobileWebUrl: pgUrl,
            webUrl: pgUrl,
          },
        },
        buttons: [
          {
            title: "일정 짜러가기",
            link: {
              mobileWebUrl: pgUrl,
              webUrl: pgUrl,
            },
          },
        ],
      });
    },
  },
};
</script>

<style>
.share {
  width: 45px;
  height: 45px;
  border-radius: 24px;
  cursor: pointer;
}
</style>
