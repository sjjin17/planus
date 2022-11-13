<template>
  <div>
    {{ article.title }}
    {{ article.trip.imageUrl }}
  </div>
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
      console.log(this.article);
    },
  },
  created() {
    this.getArticleDetail();
  },
};
</script>

<style></style>
