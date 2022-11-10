<template>
  <div>
    {{ articleId }}번 게시글
    <comment-list :articleId="articleId"></comment-list>
  </div>
</template>

<script>
// import API from "@/api/RESTAPI";
import CommentList from "@/components/community/CommentList.vue";

// const api = API;
export default {
  name: "CommunityView",
  components: {
    CommentList,
  },
  data() {
    return {
      articleId: 0,
    };
  },
  created() {
    this.articleId = Number(this.$route.params.articleId);
  },
  methods: {},
};
</script>

<style></style>
