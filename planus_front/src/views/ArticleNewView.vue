<template>
  <v-container>
    <v-row>
      <v-col cols="2" class="my-4">
        <p>제목</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="title"
          label="제목"
          auto-grow
          outlined
          rows="1"
          row-height="15"
        ></v-textarea>
      </v-col>
      <v-col cols="2" class="my-4">
        <p>여행</p>
      </v-col>
      <v-col cols="10">
        <v-select
          v-model="select"
          :items="myAllTrip"
          :item-text="
            (item) =>
              item.startDate +
              '~' +
              item.endDate +
              ' ' +
              item.areaList +
              ' ' +
              item.participants +
              '명'
          "
          item-value="tripId"
          :rules="[(v) => !!v || 'Item is required']"
          label="여행을 선택하세요"
          return-object
        ></v-select>
      </v-col>
      <v-col cols="2" class="my-4">
        <p>내용</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="content"
          label="내용"
          auto-grow
          outlined
          rows="3"
          row-height="25"
          shaped
        ></v-textarea>
      </v-col>
      <v-btn @click="createNewArticle">제출</v-btn>
    </v-row>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "ArticleNewView",
  data() {
    return {
      articleId: "",
      title: "",
      content: "",
      tripId: "",
      myAllTrip: [],
      select: {},
    };
  },
  methods: {
    async createNewArticle() {
      this.tripId = this.select.tripId;
      console.log(this.select);
      this.articleId = await api.createArticle(
        this.title,
        this.content,
        this.tripId
      );
      this.$router.push("/article/" + this.articleId);
    },
    async getMyAllTrip() {
      this.myAllTrip = await api.getMyAllTrip();
      console.log(this.myAllTrip);
    },
  },

  created() {
    this.getMyAllTrip();
  },
};
</script>
<style></style>
