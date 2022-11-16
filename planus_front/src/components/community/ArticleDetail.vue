<template>
  <v-container v-if="article.trip != undefined">
    <v-row class="top-button">
      <v-btn outlined color="#4A8072" @click="goToCommunity">목록으로</v-btn>

      <v-btn outlined color="#4A8072">복사하기</v-btn>
    </v-row>
    <br />
    <v-row align="center" justify="center">
      <v-sheet
        color="white"
        elevation="1"
        height="auto"
        rounded
        width="1200"
        class="my-3"
      >
        <v-col>
          <div class="title" style="position: relative">
            {{ article.title }}
            <div class="like-see">
              <v-icon v-if="isLike === false" @click="likeArticle"
                >mdi-cards-heart-outline</v-icon
              >

              <v-icon v-else @click="likeArticle">mdi-heart</v-icon>
              {{ likeCount }}

              <v-icon>mdi-eye</v-icon>
              {{ article.hits }}
            </div>
          </div>
        </v-col>

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
        <p>
          {{ article.content }}
        </p>
      </v-sheet>
    </v-row>

    <v-row class="d-flex justify-center my-10">
      <v-btn
        outlined
        color="#4A8072"
        v-if="article.user.userId === userId"
        @click="goToEditArticle"
        class="mx-4"
        >수정</v-btn
      >
      <v-btn
        outlined
        color="#4A8072"
        v-if="article.user.userId === userId"
        @click="changeDialog"
        >삭제</v-btn
      >
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
    </v-row>
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
      dialog: false,
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
      this.dialog = false;
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
    changeDialog() {
      this.dialog = true;
    },
  },

  async created() {
    await this.getArticleDetail();
    this.decoding();
  },
};
</script>

<style scoped>
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
.top-button {
  justify-content: space-between;
}
.bottomm-button {
  row-gap: 20px;
}

.like-see {
  position: absolute;
  right: 0px;
  top: 0px;
}
.v-icon.v-icon:after {
  background-color: transparent;
}
p {
  white-space: pre-line;
  word-wrap: break-word;
  table-layout: fixed;
  padding-left: 5px;
  padding-right: 5px;
}
</style>
