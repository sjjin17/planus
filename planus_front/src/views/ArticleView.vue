<template>
  <div>
    <navi-bar @isLoginChange="isLoginChange"></navi-bar>
    <article-detail :articleId="articleId" :isLogin="isLogin"></article-detail>
    <comment-list :articleId="articleId" :isLogin="isLogin"></comment-list>
  </div>
</template>

<script>
import NaviBar from "@/components/nav/NaviBar.vue";
import CommentList from "@/components/community/CommentList.vue";
import ArticleDetail from "@/components/community/ArticleDetail.vue";
export default {
  name: "CommunityView",
  components: {
    CommentList,
    ArticleDetail,
    NaviBar,
  },
  data() {
    return {
      articleId: 0,
      isLogin: false,
    };
  },
  created() {
    this.articleId = Number(this.$route.params.articleId);
  },
  methods: {
    isLoginChange(isLogin) {
      this.isLogin = isLogin;
    },
  },
};
</script>

<style></style>
