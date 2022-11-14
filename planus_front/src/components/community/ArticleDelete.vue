<template>
  <v-dialog v-model="dialog" persistent max-width="300">
    <template v-slot:activator="{ on, attrs }">
      <h5 v-bind="attrs" v-on="on" style="color: #544c4c">삭제</h5>
    </template>
    <v-card>
      <v-card-title></v-card-title>
      <v-card-text>정말로 삭제하시겠습니까?</v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn outlined color="#4a8072" @click="dialog = false"> 취소</v-btn>
        <v-btn color="#ff1744" @click="deleteArticle"
          ><span style="color: white">삭제</span></v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "ArticleDelete",
  data: () => {
    return { dialog: false };
  },
  props: {
    articleId: Number,
  },
  methods: {
    deleteArticle() {
      this.$emit("deleteArticle", this.articleId);
    },
  },
};
</script>

<style></style>
