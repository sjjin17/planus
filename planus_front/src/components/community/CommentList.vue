<template>
  <div>
    <v-row align="center" justify="center">
      <v-col cols="8">
        <v-textarea
          v-model="commentInput"
          counter="200"
          placeholder="댓글을 입력하세요"
          outlined
          no-resize
        ></v-textarea>
      </v-col>
      <v-col cols="1">
        <v-btn class="addCommentBtn" @click="goComment()">입력</v-btn>
      </v-col>
    </v-row>
    <div v-for="(comment, i) in commentList" :key="i">
      <v-row align="center" justify="center">
        <v-col cols="8">
          <div class="commentNameLine">
            <div>
              {{ comment.name }} ({{ comment.regDate.split("T")[0] }}
              {{ comment.regDate.split("T")[1].split(".")[0] }})
            </div>
            <div v-if="userId == comment.userId">
              <button
                v-if="isEditing == comment.commentId"
                @click="modifyComment(comment.commentId)"
              >
                저장
              </button>
              <button
                v-else
                @click="goEdit(comment.commentId, comment.content)"
              >
                수정
              </button>
              <span> | </span>
              <button
                v-if="userId == comment.userId"
                @click="
                  dialog = true;
                  delCommentId = comment.commentId;
                "
              >
                삭제
              </button>
            </div>
          </div>
          <v-textarea
            v-model="commentInput2"
            counter="200"
            value="commentInput2"
            outlined
            no-resize
            v-if="isEditing == comment.commentId"
          ></v-textarea>
          <span v-else>{{ comment.content }}</span>
        </v-col>
      </v-row>
      <v-divider style="margin: 1% 12%"></v-divider>
    </div>
    <v-row justify="center" class="pagenation-bar">
      <v-pagination
        v-model="currentPage"
        class="my-4"
        :length="totalPage"
        color="#4a8072"
      ></v-pagination>
    </v-row>
    <v-dialog v-model="dialog" persistent max-width="300">
      <v-card>
        <v-card-title></v-card-title>
        <v-card-text>정말로 삭제하시겠습니까?</v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn outlined color="#4a8072" @click="dialog = false"> 취소</v-btn>
          <v-btn color="#ff1744" @click="delComment()"
            ><span style="color: white">삭제</span></v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
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
      commentInput2: "",
      isEditing: 0,
      delCommentId: 0,
      dialog: false,
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
      if (this.commentInput.length > 0 && this.commentInput.length <= 200) {
        this.res = await api.addComment(this.articleId, this.commentInput);
        this.commentInput = "";
      }
    },
    async modifyComment(commentId) {
      if (this.commentInput2.length > 0 && this.commentInput2.length <= 200) {
        this.res = await api.modifyComment(commentId, this.commentInput2);
        this.commentInput2 = "";
        this.isEditing = 0;
        this.getCommentList(this.currentPage);
      }
    },
    async getCommentList(page) {
      this.res = await api.getArticleComment(this.articleId, page - 1);
      this.commentList = this.res.commentPage.commentList;
      this.totalPage = this.res.commentPage.totalPage;
    },
    async delComment() {
      this.res = await api.delComment(this.delCommentId);
      this.dialog = false;
      await this.getCommentList(1);
    },
    decoding() {
      if (this.token) {
        let decode = jwt_decode(this.token);
        this.nickname = decode.nickname;
        this.userId = decode.userId;
      }
    },
    goEdit(commentId, content) {
      this.isEditing = commentId;
      this.commentInput2 = content;
    },
    async goComment() {
      await this.addComment();
      this.getCommentList(1);
    },
  },
};
</script>

<style>
.addCommentBtn {
  margin-bottom: 22px;
  height: 60px !important;
  background-color: #4a8072 !important;
  font-size: 1.2rem !important;
  font-weight: 700;
  color: white !important;
}
.commentNameLine {
  display: flex;
  justify-content: space-between;
}
</style>
