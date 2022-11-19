<template>
  <v-container class="my-container">
    <div class="header">게시글 작성</div>
    <v-row align="center">
      <v-col cols="2" align="center" class="mb-7 box-center"> 제목 </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="title"
          auto-grow
          outlined
          color="#4A8072"
          rows="1"
          row-height="15"
          counter="200"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="2" align="center" class="ma-0 box-center trip"> 여행 </v-col>
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
      <v-col cols="2" align="center" class="ma-0 box-center content">
        내용
      </v-col>
      <v-col cols="10">
        <v-textarea
          v-model="content"
          auto-grow
          outlined
          color="#4A8072"
          rows="3"
          row-height="25"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row class="bottom-button">
      <v-btn class="mr-2" outlined color="#4A8072" @click="goToCommunity"
        >목록으로</v-btn
      >
      <v-btn outlined color="#4A8072" @click="createNewArticle">등록</v-btn>
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
      if (this.title.length > 0 && this.title.length <= 200) {
        this.tripId = this.select.tripId;

        this.articleId = await api.createArticle(
          this.title,
          this.content,
          this.tripId
        );

        this.$router.push("/article/" + this.articleId);
      } else {
        window.alert("제목은 1자 이상 200자 이하로 적어주세요!");
      }
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
.header {
  margin: 4% 5%;
  color: #383d3c;
  font-size: 2.2rem;
  font-weight: 700;
}
.content {
  line-height: 96px;
}
.trip {
  line-height: 70px;
}
.box-center {
  text-align: center;
  color: #383d3c;
  font-size: 1.2rem;
  font-weight: 700;
}
.bottom-button {
  margin-bottom: 4%;
  justify-content: flex-end;
}
</style>
