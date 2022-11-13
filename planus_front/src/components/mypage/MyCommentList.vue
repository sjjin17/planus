<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">댓글</th>
            <th class="text-left">수정시간</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in commentList" :key="comment.commentId">
            <td>{{ comment.content }}</td>
            <td>{{ comment.regDate }}</td>
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
        this.commentList = res.commentPage.commentList;
        this.totalPage = res.commentPage.totalPage;
      });
    },
  },
};
</script>

<style></style>
