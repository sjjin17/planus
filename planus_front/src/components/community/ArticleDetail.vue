<template>
  <v-container>
    <v-sheet color="white" elevation="1" height="1000" rounded width="1500">
      {{ article.title }}
      <hr />
      <div class="img-center">
        <img :src="article.trip.imageUrl" alt="여행사진" />
      </div>
      <p style="white-space: pre">
        {{ article.content }}
      </p>
    </v-sheet>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "ArticleDetail",
  data: function () {
    return {
      article: {},
    };
  },
  props: {
    articleId: Number,
  },
  methods: {
    async getArticleDetail() {
      this.article = await api.getArticle(this.articleId);
    },
  },
  created() {
    this.getArticleDetail();
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
</style>
