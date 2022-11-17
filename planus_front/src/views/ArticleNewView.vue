<template>
  <v-container class="my-container">
    <h1>게시글 작성 페이지</h1>

    <br />
    <v-row align="center">
      <v-col cols="2" align="center" class="ma-0 box-center font-weight-bold">
        제목
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="title"
          auto-grow
          outlined
          color="#4A8072"
          rows="1"
          row-height="15"
          hide-details
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="2"
        align="center"
        class="ma-0 box-center trip font-weight-bold"
      >
        <p>여행</p>
      </v-col>
      <v-col cols="10">
        <v-select
          v-model="select"
          prepend-inner-icon="mdi-airplane"
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
          color="#4A8072"
          hide-details
          return-object
        ></v-select>
        <v-img :src="select.imageUrl" alt="" />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="2"
        align="center"
        class="ma-0 box-center content font-weight-bold"
      >
        <p>내용</p>
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="content"
          auto-grow
          outlined
          color="#4A8072"
          rows="3"
          row-height="25"
          shaped
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row class="bottom-button">
      <v-btn class="mr-2" outlined color="#4A8072" @click="goToCommunity"
        >목록으로</v-btn
      >

      <v-btn outlined color="#4A8072" @click="createNewArticle">제출</v-btn>
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
      this.articleId = await api.createArticle(
        this.title,
        this.content,
        this.tripId
      );
      this.$router.push("/article/" + this.articleId);
    },
    async getMyAllTrip() {
      this.myAllTrip = await api.getMyAllTrip();
    },
    goToCommunity() {
      this.$router.push("/community");
    },
  },

  created() {
    this.getMyAllTrip();
  },
};
</script>
<style scoped>
.content {
  line-height: 96px;
}
.trip {
  line-height: 70px;
}
.box-center {
  text-align: center;
  color: #544c4c;
}
.my-container {
  overflow: hidden;
}
.bottom-button {
  justify-content: flex-end;
}
.img {
  width: auto;
  height: auto;
  max-width: 1px;
  max-height: 1px;
}
</style>
