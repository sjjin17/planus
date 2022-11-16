<template>
  <v-container>
    <h1>완료페이지</h1>
    <v-sheet id="capture" height="75vh" class="content pa-4" justify="center">
      <complete-map
        :tripArea="tripInfo.tripArea"
        :completeList="completeList"
      ></complete-map>
      <complete-page :completeList="completeList"></complete-page>
    </v-sheet>
    <v-container class="d-flex justify-center">
      <v-btn outlined large class="mx-4" color="#4a8072" @click="captureImg"
        >저장</v-btn
      >
      <v-btn outlined large class="mx-4" color="#4a8072" @click="shareTrip"
        >공유</v-btn
      >
      <v-btn outlined large class="mx-4" color="#4a8072">복사</v-btn>
    </v-container>
  </v-container>
</template>

<script>
import html2canvas from "html2canvas";
import API from "@/api/RESTAPI";
import CompletePage from "@/components/complete/CompletePage.vue";
import CompleteMap from "@/components/complete/CompleteMap.vue";
const api = API;

import Vue from "vue";
import Kakaosdk from "vue-kakao-sdk";

const apiKey = process.env.VUE_APP_KAKAO_JS_KEY;
Vue.use(Kakaosdk, { apiKey });

export default {
  name: "CompleteView",
  components: {
    CompletePage,
    CompleteMap,
  },
  data() {
    return {
      tripUrl: "",
      tripInfo: {
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
      completeList: [],
    };
  },
  async created() {
    this.tripUrl = this.$route.params.tripUrl;
    await this.getTripInfo();
    await this.getMemberList();
    await this.getCompleteInfo();
  },
  methods: {
    async getTripInfo() {
      this.res = await api.getTripInfo(this.tripUrl).catch(() => {
        window.alert("존재하지 않는 url입니다!");
        this.$router.push("/");
      });
      this.tripInfo = this.res.result;
      if (!this.tripInfo.complete) {
        this.$router.push("/plans/" + this.tripUrl);
      }
    },
    async getMemberList() {
      this.res = await api.getMemberList(this.tripInfo.tripId);
      this.memberList = this.res.memberList;
    },
    async getCompleteInfo() {
      this.res = await api.getComplete(this.tripUrl);
      this.completeList = this.res.result.planResDTOList;
    },
    captureImg() {
      html2canvas(document.getElementById("capture"), {
        backgroundColor: null,
        useCORS: true,
      }).then((canvas) => {
        this.saveAs(canvas.toDataURL("image/png"), "이미지.png");
      });
    },
    saveAs(uri, filename) {
      let link = document.createElement("a");
      if (typeof link.download === "string") {
        link.href = uri;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else {
        window.open(uri);
      }
    },
    shareTrip() {
      let imgUrl = this.tripInfo.imageUrl;
      let pgUrl = window.location.href;

      if (this.tripInfo.imageUrl == null) {
        imgUrl =
          "http://img.segye.com/content/image/2022/02/23/20220223514051.jpg";
      }

      this.$kakao.Link.sendDefault({
        objectType: "feed",
        content: {
          title: "Planus",
          description: "Planus에서 완성된 여행 일정을 확인해 보세요!",
          imageUrl: imgUrl,
          link: {
            mobileWebUrl: imgUrl,
            webUrl: imgUrl,
          },
        },
        buttons: [
          {
            title: "일정 확인하기",
            link: {
              mobileWebUrl: pgUrl,
              webUrl: pgUrl,
            },
          },
        ],
      });
    },
  },
  // mounted() {
  //   html2canvas(document.getElementById("capture")).then((canvas) => {
  //     // var base64image = canvas.toDataURL("image/png");
  //     // window.open(base64image, "_blank");
  //     this.saveAs(canvas.toDataURL("image/png"), "이미지.png");
  //   });
  // },
};
</script>

<style scoped>
.content {
  border: solid;
  border-width: 5px;
  border-color: #4a8072;
  border-radius: 10px;
  overflow-y: scroll;
}
.content::-webkit-scrollbar {
  color: #544c4c;
  width: 10px;
}
.content::-webkit-scrollbar-thumb {
  background-color: #544c4c;
  border-radius: 10px;
}
</style>
