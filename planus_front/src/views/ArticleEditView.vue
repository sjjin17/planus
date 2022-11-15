<template>
  <v-container>
    <h1>게시글 수정 페이지</h1>
    <br />

    <v-row align="center">
      <v-col cols="2" align="center" class="ma-0 box-center font-weight-bold">
        제목
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="article.title"
          auto-grow
          outlined
          color="#4A8072"
          rows="1"
          row-height="15"
          hide-details
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="2"
        align="center"
        class="ma-0 box-center trip font-weight-bold"
      >
        <p>여행</p>
      </v-col>
      <v-col cols="10">
        <v-select
          v-model="select"
          prepend-inner-icon="mdi-airplane"
          :items="myAllTrip"
          :item-text="
            (item) =>
              item.startDate +
              '~' +
              item.endDate +
              ' ' +
              item.areaList +
              ' ' +
              item.participants +
              '명'
          "
          item-value="tripId"
          :rules="[(v) => !!v || 'Item is required']"
          label="여행을 선택하세요"
          color="#4A8072"
          hide-details
          return-object
        ></v-select>
        <img :src="select.imageUrl" alt="" />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="2"
        align="center"
        class="ma-0 box-center content font-weight-bold"
      >
        <p>내용</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="article.content"
          auto-grow
          outlined
          color="#4A8072"
          rows="3"
          row-height="25"
          shaped
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row class="bottom-button">
      <v-btn class="mr-2" outlined color="#4A8072" @click="goToCommunity"
        >목록으로</v-btn
      >

      <v-btn outlined color="#4A8072" @click="updateArticle">제출</v-btn>
    </v-row>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  name: "ArticleEditView",
  data: function () {
    return {
      article: {},
      articleId: 0,
      title: "",
      content: "",
      tripId: "",
      myAllTrip: [],
      select: {},
    };
  },
  methods: {
    async getArticleDetail() {
      this.article = await api.getArticle(this.articleId);
      this.select = this.article.trip;
    },
    async getMyAllTrip() {
      this.myAllTrip = await api.getMyAllTrip();
    },
    async updateArticle() {
      this.tripId = this.select.tripId;

      await api.updateArticle(
        this.articleId,
        this.article.title,
        this.article.content,
        this.tripId
      );
      this.$router.push("/article/" + this.articleId);
    },
    goToCommunity() {
      this.$router.push("/community");
    },
  },
  async created() {
    this.articleId = Number(this.$route.params.articleId);
    await this.getMyAllTrip();
    await this.getArticleDetail();
  },
};
</script>

<style>
.bottom-button {
  justify-content: flex-end;
}
</style>
