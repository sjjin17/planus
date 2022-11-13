<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">제목</th>
            <th class="text-left">좋아요</th>
            <th class="text-left">조회수</th>
            <th class="text-left">수정시간</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="article in articleList" :key="article.articleId">
            <td>{{ article.title }}</td>
            <td>{{ article.likeCount }}</td>
            <td>{{ article.hits }}</td>
            <td>{{ article.regDate }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
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
    await this.getLikeList();
  },
  watch: {
    currentPage() {
      this.getLikeList();
    },
  },
  methods: {
    async getLikeList() {
      await api.getMyLikedArticle(this.currentPage - 1).then((res) => {
        this.articleList = res.articleList;
        this.totalPage = res.totalPage;
      });
    },
  },
};
</script>

<style></style>
