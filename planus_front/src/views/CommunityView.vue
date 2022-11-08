<template>
  <div>
    <search-article></search-article>
    <v-row>
      <v-col v-for="(article, i) in articleList" :key="i" cols="6">
        <v-card
          outlined
          class="mx-10 mt-2"
          @click="goToArticle(article.articleId)"
          height="19vh"
        >
          <v-list-item>
            <v-list-item-avatar tile class="my-0 img-avatar"
              ><v-img :src="article.imageUrl"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <div class="text-overline mb-4 d-flex justify-space-between">
                <span class="txt_line"
                  >{{ article.period }}박{{ article.period + 1 }}일</span
                >
              </div>
              <v-list-item-title class="text-h5 mb-1">
                {{ article.title }}
              </v-list-item-title>
              <v-list-item-subtitle
                class="d-flex align-end flex-column my-0 py-0"
              >
                <span v-for="(area, idx) in article.areaList" :key="idx + 'a'"
                  >{{ area }} |
                </span>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
    <v-spacer></v-spacer>
    <v-row justify="center" class="pagenation-bar">
      <v-pagination
        v-model="currentPage"
        class="my-4"
        :length="totalPage"
        color="#4a8072"
      ></v-pagination>
    </v-row>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
import SearchArticle from "@/components/community/SearchArticle.vue";

const api = API;
export default {
  name: "CommunityView",
  components: {
    SearchArticle,
  },
  data() {
    return {
      isSearchByTitle: true,
      title: "",
      area: [3, 4],
      currentPage: 1,
      articleList: [],
      totalPage: 0,
    };
  },
  async created() {
    await this.getArticleListByTitle(1);
  },
  watch: {
    currentPage(newVal) {
      if (this.isSearchByTitle) {
        this.getArticleListByTitle(newVal);
      } else {
        this.getArticleListByArea(newVal);
      }
      console.log("페이지: " + newVal);
    },
  },
  methods: {
    async getArticleListByTitle(page) {
      this.res = await api.getArticleListByTitle(this.title, page - 1);
      this.articleList = this.res.searchList.articleList;
      this.totalPage = this.res.searchList.totalPage;
      console.log("글목록: " + this.articleList);
      console.log("페이지수: " + this.totalPage);
    },
    async getArticleListByArea(page) {
      this.res = await api.getArticleListByArea(this.area, page - 1);
      this.articleList = this.res.searchList.articleList;
      this.totalPage = this.res.searchList.totalPage;
      console.log("글목록: " + this.articleList);
      console.log("페이지수: " + this.totalPage);
    },
    goToArticle(articleId) {
      this.$router.push("/article/" + articleId);
    },
  },
};
</script>

<style></style>
