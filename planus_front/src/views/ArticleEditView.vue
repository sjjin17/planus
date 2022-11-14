<template>
  <v-container>
    <v-row>
      <v-col cols="2" class="my-4">
        <p>제목</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="article.title"
          label="제목"
          auto-grow
          outlined
          rows="1"
          row-height="15"
        ></v-textarea>
      </v-col>
      <v-col cols="2" class="my-4">
        <p>여행</p>
      </v-col>
      <v-col cols="10">
        <v-select
          v-model="select"
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
          return-object
        ></v-select>
      </v-col>
      <v-col cols="2" class="my-4">
        <p>내용</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="article.content"
          label="내용"
          auto-grow
          outlined
          rows="3"
          row-height="25"
          shaped
        ></v-textarea>
      </v-col>
      <v-btn @click="updateArticle">제출</v-btn>
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
  },
  async created() {
    this.articleId = Number(this.$route.params.articleId);
    await this.getMyAllTrip();
    await this.getArticleDetail();
  },
};
</script>

<style></style>
