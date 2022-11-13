<template>
  <v-container class="made-container">
    <v-row justify="center" height="70vh" class="mt-3">
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left" style="width: 50vw">댓글</th>
              <th class="text-left">등록(수정)시간</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="listTable"
              v-for="comment in commentList"
              :key="comment.commentId"
            >
              <td>{{ comment.content }}</td>
              <td>
                {{
                  comment.regDate.split("T")[0] +
                  " " +
                  comment.regDate.split("T")[1].split(".")[0]
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
      commentList: [],
      currentPage: 1,
      totalPage: 1,
    };
  },
  async created() {
    await this.getMyCommentList();
  },
  watch: {
    currentPage() {
      this.getMyCommentList();
    },
  },
  methods: {
    async getMyCommentList() {
      await api.getMyComment(this.currentPage - 1).then((res) => {
        console.log(res);
        this.commentList = res.commentPage.commentList;
        this.totalPage = res.commentPage.totalPage;
      });
    },
    goToArticle(articleId) {
      this.$router.push("/article/" + articleId);
    },
  },
};
</script>

<style></style>
