<template>
  <div>
    <search-article @goSearch="goSearch"></search-article>
    <v-row>
      <v-col v-for="(article, i) in articleList" :key="i" cols="6">
        <v-card
          outlined
          class="mx-10 mt-2"
          @click="goToArticle(article.articleId)"
          style="border-color: #4a8072"
        >
          <v-row>
            <v-col cols="3">
              <v-img :src="article.imageUrl"> </v-img>
            </v-col>
            <v-col>
              <v-row>
                <v-col>
                  {{ article.title }}
                </v-col>
                <v-col cols="2">
                  <v-icon>mdi-eye</v-icon>{{ article.hits }}
                </v-col>
                <v-col cols="2">
                  <v-icon>mdi-heart</v-icon>{{ article.likes }}
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-chip-group column>
                    <v-chip
                      v-for="(area, idx) in article.areaList"
                      :key="idx + 'a'"
                      >{{ area }}
                    </v-chip>
                  </v-chip-group>
                </v-col>
                <v-col cols="6">
                  {{ article.period }}박 {{ article.period + 1 }}일
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  {{ article.name }} ({{ article.regDate.split("T")[0] }}
                  {{ article.regDate.split("T")[1].split(".")[0] }})
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-spacer></v-spacer>
    <v-btn @click="goToNewArticle()">게시글 작성</v-btn>
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
      currentPage: 1,
      searchData: "",
      articleList: [],
      totalPage: 0,
    };
  },
  async created() {
    await this.getArticleListByTitle(1);
  },
  watch: {
    currentPage(newVal) {
      console.log("currentPage: " + newVal);
      if (this.isSearchByTitle) {
        this.getArticleListByTitle(newVal);
      } else {
        this.getArticleListByArea(newVal);
      }
    },
  },
  methods: {
    async getArticleListByTitle(page) {
      this.res = await api.getArticleListByTitle(this.searchData, page - 1);
      this.articleList = this.res.searchList.articleList;
      this.totalPage = this.res.searchList.totalPage;
    },
    async getArticleListByArea(page) {
      this.res = await api.getArticleListByArea(this.searchData, page - 1);
      this.articleList = this.res.articleList;
    },
    async getArticleListByAreaLength() {
      this.res = await api.getArticleListByAreaLength(this.searchData);
      this.totalPage = this.res.length;
    },
    goToArticle(articleId) {
      this.$router.push("/article/" + articleId);
    },
    async goSearch(val, data) {
      this.searchData = data;
      if (val == 1) {
        this.isSearchByTitle = true;
        this.getArticleListByTitle(1);
      } else {
        this.isSearchByTitle = false;
        this.getArticleListByAreaLength();
        this.getArticleListByArea(1);
      }
    },
    goToNewArticle() {
      this.$router.push("/newArticle");
    },
  },
};
</script>

<style></style>
