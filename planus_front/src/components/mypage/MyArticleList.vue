<template>
  <v-container class="made-container">
    <v-row justify="center">
      <v-simple-table height="70vh" class="mt-3">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">여행날짜</th>
              <th class="text-left">여행지역</th>
              <th class="text-left" style="width: 35vw">제목</th>
              <th class="text-left">좋아요</th>
              <th class="text-left">조회수</th>
              <th class="text-left">등록(수정)시간</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="listTable"
              v-for="article in articleList"
              :key="article.articleId"
              @click="goToArticle(article.articleId)"
            >
              <td>
                {{ article.trip.startDate + "~" + article.trip.endDate }}
              </td>
              <td>
                {{ article.trip.participants + "명 " + article.trip.areaList }}
              </td>
              <td>{{ article.title }}</td>
              <td>{{ article.likeCount }}</td>
              <td>{{ article.hits }}</td>
              <td>
                {{
                  article.regDate.split("T")[0] +
                  " " +
                  article.regDate.split("T")[1].split(".")[0]
                }}
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-row>
    <v-spacer />
    <v-row justify="center" class="pagenation-bar">
      <v-pagination
        v-model="currentPage"
        class="my-4"
        :length="totalPage"
        color="#4a8072"
      ></v-pagination>
    </v-row>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  data() {
    return {
      articleList: [],
      currentPage: 1,
      totalPage: 1,
    };
  },
  async created() {
    await this.getArticleList();
  },
  watch: {
    currentPage() {
      this.getArticleList();
    },
  },
  methods: {
    async getArticleList() {
      await api
        .getMyArticle(this.currentPage - 1)
        .then((res) => {
          console.log(res);
          this.articleList = res.articleList;
          this.totalPage = res.totalPage;
        })
        .catch();
    },
    goToArticle(articleId) {
      this.$router.push("/article/" + articleId);
    },
  },
};
</script>

<style>
.made-container {
  height: 100%;
  position: relative;
}
.pagenation-bar {
  width: 100%;
  position: absolute;
  bottom: 0;
}
.listTable {
  cursor: pointer;
}
</style>
