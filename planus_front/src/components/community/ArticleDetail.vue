<template>
  <v-container v-if="article.trip != undefined">
    <v-icon v-if="isLike === false" @click="likeArticle"
      >mdi-cards-heart-outline</v-icon
    >
    <v-icon v-if="isLike" @click="likeArticle">mdi-heart</v-icon>
    {{ likeCount }}
    <v-btn>복사하기</v-btn>

    <v-sheet color="white" elevation="1" height="auto" rounded width="1000">
      <div class="title">
        {{ article.title }}
      </div>
      <hr />
      <div class="d-flex flex-row-reverse">
        <h5>
          {{
            nickname +
            " " +
            article.regDate.split("T")[0] +
            " " +
            article.regDate.split("T")[1].split(".")[0]
          }}
        </h5>
      </div>

      <div class="img-center">
        <img :src="article.trip.imageUrl" alt="여행사진" />
      </div>
      <p style="white-space: pre-line">
        {{ article.content }}
      </p>
    </v-sheet>
    <div class="d-flex justify-center">
      <v-btn v-if="article.user.userId === userId" @click="goToEditArticle"
        >수정</v-btn
      >
      <v-btn v-if="article.user.userId === userId" @click="deleteArticle"
        >삭제</v-btn
      >
    </div>
    <v-btn class="d-flex flex-row-reverse" @click="goToCommunity"
      >목록으로</v-btn
    >
  </v-container>
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
      token: this.$cookies.get("token"),
      userId: 0,
      nickname: "",
    };
  },
  props: {
    articleId: Number,
  },
  methods: {
    async getArticleDetail() {
      this.article = await api.getArticle(this.articleId);

      this.likeCount = this.article.likeCount;
      this.isLike = this.article.like;
    },
    async likeArticle() {
      if (this.isLike) {
        this.isLike = false;
      } else {
        this.isLike = true;
      }

      this.likeCount = await api.likeArticle(this.articleId);
    },
    goToEditArticle() {
      this.$router.push("/editArticle/" + this.articleId);
    },
    goToCommunity() {
      this.$router.push("/community");
    },
    async deleteArticle() {
      await api.deleteArticle(this.articleId);
      await this.goToCommunity();
    },
    decoding() {
      if (this.token) {
        let decode = jwt_decode(this.token);
        this.nickname = decode.nickname;
        this.userId = Number(decode.userId);
      }
    },
  },

  async created() {
    await this.getArticleDetail();
    this.decoding();
  },
};
</script>

<style>
.img-center {
  text-align: center;
}
hr {
  width: 100%;
  color: grey;
}
.title {
  text-align: center;
}
</style>
