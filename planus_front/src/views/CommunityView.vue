<template>
  <div>
    <navi-bar @isLoginChange="isLoginChange"></navi-bar>
    <br />
    <v-dialog v-model="alert" max-width="450">
      <v-card>
        <v-card-title></v-card-title>
        <v-card-text color="white"> 로그인 후 이용해주세요</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#4a8072" outlined @click="alert = false">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <search-article @goSearch="goSearch"></search-article>
    <v-container class="made-container">
      <v-row>
        <v-col v-for="(article, i) in articleList" :key="i" cols="6">
          <v-card
            outlined
            width="100%"
            class="mt-2"
            @click="goToArticle(article.articleId)"
            style="border-color: #4a8072"
          >
            <v-list-item class="pa-0">
              <v-list-item-avatar tile class="my-0 img-avatar" size="50%">
                <v-img height="200" :src="article.imageUrl"></v-img>
              </v-list-item-avatar>
              <v-list-item-content class="pa-2">
                <div>
                  <v-list-item-title>
                    <v-card-title class="pa-0 textCutting articleTitle">
                      {{ article.title }}
                    </v-card-title>
                  </v-list-item-title>
                  <v-list-item-subtitle
                    style="color: rgb(56, 61, 60, 50%); font-weight: 600"
                  >
                    <v-icon small>mdi-heart</v-icon>
                    <span>{{ article.likes }}</span
                    >&nbsp;
                    <v-icon small>mdi-eye</v-icon>
                    <span>{{ article.hits }}</span>
                  </v-list-item-subtitle>
                </div>
                <v-chip-group>
                  <v-chip
                    color="#4a8072"
                    text-color="white"
                    v-for="(area, idx) in article.areaList"
                    :key="idx + 'a'"
                    >{{ area }}
                  </v-chip>
                </v-chip-group>
                <div class="textCutting articleNickName">
                  {{ article.name }}
                </div>
                <div class="textCutting articleRegDate">
                  ({{ article.regDate.split("T")[0] }}
                  {{ article.regDate.split("T")[1].split(".")[0] }})
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col class="text-right">
          <v-btn outlined color="#4A8072" @click="goToNewArticle()"
            >게시글 작성</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
    <v-spacer></v-spacer>
    <br />
    <v-row justify="center">
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
import NaviBar from "@/components/nav/NaviBar.vue";
import API from "@/api/RESTAPI";
import SearchArticle from "@/components/community/SearchArticle.vue";
const api = API;
export default {
  name: "CommunityView",
  components: {
    SearchArticle,
    NaviBar,
  },
  data() {
    return {
      isSearchByTitle: true,
      currentPage: 1,
      searchData: "",
      articleList: [],
      totalPage: 0,
      token: this.$cookies.get("refresh"),
      alert: false,
      isLogin: false,
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
      if (!this.isLogin) {
        this.alert = !this.alert;
      } else {
        this.$router.push("/newArticle");
      }
    },
    isLoginChange(isLogin) {
      this.isLogin = isLogin;
    },
  },
};
</script>

<style scoped>
.made-container {
  padding-top: 6px !important;
  height: 100%;
  position: relative;
}
.textCutting {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 80%;
}
.articleTitle {
  display: block;
  font-size: 1.2rem;
  font-weight: 700;
  color: #383d3c;
}
.articleNickName {
  text-align: right;
  color: rgb(56, 61, 60, 80%);
  font-weight: 600;
}
.articleRegDate {
  text-align: right;
  color: rgb(56, 61, 60, 50%);
  font-weight: 500;
}
</style>
