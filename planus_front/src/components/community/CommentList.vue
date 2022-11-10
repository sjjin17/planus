<template>
  <div>
    <v-row align="center" justify="center">
      <v-col cols="8">
        <v-textarea
          v-model="commentInput"
          counter="20"
          placeholder="댓글을 입력하세요"
        ></v-textarea>
      </v-col>
      <v-col cols="1">
        <v-btn @click="goComment()">입력</v-btn>
      </v-col>
    </v-row>
    <div v-for="(comment, i) in commentList" :key="i">
      {{ comment.name }}: {{ comment.content }} ({{ comment.regDate }})
      <button v-if="userId == comment.userId">수정</button>
      <button
        v-if="userId == comment.userId"
        @click="delComment(comment.commentId)"
      >
        삭제
      </button>
    </div>
    <v-row justify="center" class="pagenation-bar">
      <v-pagination
        v-model="currentPage"
        class="my-4"
        :length="totalPage"
        color="#4a8072"
      ></v-pagination>
    </v-row>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import jwt_decode from "jwt-decode";

const api = API;
export default {
  components: {},
  props: {
    articleId: Number,
  },
  data() {
    return {
      token: this.$cookies.get("token"),
      nickname: "",
      userId: 0,
      currentPage: 1,
      commentList: [],
      totalPage: 0,
      commentInput: "",
    };
  },
  async created() {
    this.decoding();
    await this.getCommentList(1);
  },
  watch: {
    currentPage(newVal) {
      this.getCommentList(newVal);
      console.log("페이지: " + newVal);
    },
  },
  methods: {
    async addComment() {
      this.res = await api.addComment(this.articleId, this.commentInput);
    },
    async getCommentList(page) {
      this.res = await api.getArticleComment(this.articleId, page - 1);
      this.commentList = this.res.commentPage.commentList;
      this.totalPage = this.res.commentPage.totalPage;
    },
    async delComment(commentId) {
      this.res = await api.delComment(commentId);
      await this.getCommentList(1);
    },
    decoding() {
      if (this.token) {
        let decode = jwt_decode(this.token);
        this.nickname = decode.nickname;
        this.userId = decode.userId;
      }
    },
    async goComment() {
      await this.addComment();
      this.commentInput = "";
      this.getCommentList(1);
    },
  },
};
</script>

<style></style>
