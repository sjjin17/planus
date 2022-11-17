<template>
  <div v-if="article.trip != undefined">
    <v-sheet class="articleDiv">
      <div>
        <div class="articleTitle">
          {{ article.title }}
        </div>
        <div class="articlSubTitle">
          <div>
            {{ article.user.name }}
            <span style="color: rgb(56, 61, 60, 50%); font-weight: 500">
              ({{ article.regDate.split("T")[0] }}
              {{ article.regDate.split("T")[1].split(".")[0] }})
            </span>
          </div>
          <div style="width: 100px">
            <v-icon v-if="isLike === false">mdi-cards-heart-outline</v-icon>
            <v-icon v-else>mdi-heart</v-icon>
            {{ likeCount }}
            <v-icon>mdi-eye</v-icon>
            {{ article.hits }}
          </div>
        </div>
      </div>
      <div style="padding: 4%">
        <v-img :src="article.trip.imageUrl" alt="여행사진" />
      </div>
      <div class="articleContent">
        {{ article.content }}
      </div>
      <div class="justify-center d-flex ma-2">
        <v-icon
          style="font-size: 60px"
          v-if="isLike === false"
          @click="likeArticle"
          >mdi-cards-heart-outline</v-icon
        >
        <v-icon style="font-size: 60px" v-else @click="likeArticle"
          >mdi-heart</v-icon
        >
      </div>
    </v-sheet>
    <div class="articleBtnDiv">
      <v-btn outlined color="#4A8072" @click="goToCommunity">목록으로</v-btn>
      <div>
        <v-btn
          outlined
          class="mr-1"
          color="#4A8072"
          v-if="article.user.userId === userId"
          @click="goToEditArticle"
          >수정</v-btn
        >
        <v-btn
          outlined
          color="#4A8072"
          v-if="article.user.userId === userId"
          @click="changeDialog"
          >삭제</v-btn
        >
      </div>
      <v-btn v-if="isLogin" outlined color="#4A8072" @click="isModal = true"
        >복사하기</v-btn
      >
      <v-dialog v-model="isModal" max-width="450">
        <v-card>
          <v-card-title class="d-flex justify-center modal-title"
            >여행 시작일을 선택해주세요</v-card-title
          >
          <v-row justify="center" class="ma-0 pa-0">
            <v-date-picker
              v-model="startDate"
              :allowed-dates="disablePastDates"
              no-title
              scrollable
              locale="ko-KR"
              :day-format="getDay"
              color="#4a8072"
            ></v-date-picker>
          </v-row>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#4a8072" outlined @click="createTrip">일정생성</v-btn>
            <v-btn color="#4a8072" outlined @click="isModal = false"
              >취소</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="dialog" max-width="290">
        <v-card>
          <v-card-title></v-card-title>
          <v-card-text>정말 삭제하시겠습니까? </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn outlined color="#4a8072" @click="dialog = false">
              취소
            </v-btn>
            <v-btn outlined color="#ff1744" @click="deleteArticle">
              삭제
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
import jwt_decode from "jwt-decode";

export default {
  name: "ArticleDetail",
  data: function () {
    return {
      article: {},
      isLike: "",
      likeCount: 0,
      userId: 0,
      nickname: "",
      dialog: false,
      isModal: false,
      startDate: "",
    };
  },
  props: {
    articleId: Number,
    isLogin: Boolean,
  },
  watch: {
    isLogin(newVal) {
      if (!newVal) this.isLike = false;
      this.decoding();
    },
  },
  methods: {
    async getArticleDetail() {
      this.article = await api.getArticle(this.articleId);

      this.likeCount = this.article.likeCount;
      this.isLike = this.article.like;
    },
    async likeArticle() {
      if (this.isLogin) {
        if (this.isLike) {
          this.isLike = false;
        } else {
          this.isLike = true;
        }

        this.likeCount = await api.likeArticle(this.articleId);
      }
    },
    goToEditArticle() {
      this.$router.push("/editArticle/" + this.articleId);
    },
    goToCommunity() {
      this.$router.push("/community");
    },
    async deleteArticle() {
      this.dialog = false;
      await api.deleteArticle(this.articleId);
      await this.goToCommunity();
    },
    decoding() {
      let token = this.$cookies.get("token");
      if (token) {
        let decode = jwt_decode(token);
        this.nickname = decode.nickname;
        this.userId = Number(decode.userId);
      } else {
        this.nickname = null;
        this.userId = null;
      }
    },
    changeDialog() {
      this.dialog = true;
    },
    async createTrip() {
      const newUrl = await api.copyTrip(
        this.article.trip.tripId,
        this.startDate
      );
      this.$router.push("/plans/" + newUrl);
    },
  },

  async created() {
    await this.getArticleDetail();
    this.decoding();
  },
};
</script>

<style scoped>
.articleTitle {
  padding: 4% 4% 2% 4%;
  font-size: 1.4rem;
  font-weight: 600;
  color: #383d3c;
}
.articlSubTitle {
  padding: 0 4%;
  display: flex;
  justify-content: space-between;
  color: rgb(56, 61, 60, 80%);
  font-weight: 600;
}
.articleBtnDiv {
  width: 70%;
  margin: 2% auto 4%;
  display: flex;
  justify-content: space-between;
}
.articleDiv {
  width: 70%;
  margin: 2% auto 1%;
  border: 4px solid;
  border-radius: 12px;
  border-color: #4a8072;
}
.articleContent {
  padding: 0 4% 4% 4%;
  white-space: pre-line;
  word-wrap: break-word;
  table-layout: fixed;
}
.v-icon.v-icon:after {
  background-color: transparent;
}
</style>
