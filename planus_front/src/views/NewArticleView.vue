<template>
  <v-container>
    <p>제목</p>
    <v-textarea
      label="제목"
      auto-grow
      outlined
      rows="1"
      row-height="15"
    ></v-textarea>
    <v-select
      v-model="select"
      :items="items"
      :rules="[(v) => !!v || 'Item is required']"
      label="Trip"
      required
    ></v-select>
    <v-textarea
      label="내용"
      auto-grow
      outlined
      rows="3"
      row-height="25"
      shaped
    ></v-textarea>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  name: "NewArticleView",
  data() {
    return {
      articleId: "",
    };
  },
  method: {
    async createNewArticle() {
      this.articleId = await api.getBucketList(this.tripId);
    },
  },
};
</script>

<style></style>
