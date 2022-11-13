<template>
  <v-container>
    <h1>마이페이지</h1>
    <v-container class="d-flex justify-center">
      <v-sheet height="80vh" class="content">
        <router-view style="width: 80vw; max-width: 80vw" />
      </v-sheet>
      <div style="padding-top: 3%">
        <v-btn-toggle v-model="tab" mandatory style="display: grid">
          <v-btn
            class="rounded-btn"
            x-large
            v-for="(item, index) in items"
            :value="item"
            :key="index"
            :color="tab == item ? '#4A8072' : 'white'"
            :class="tab == item ? 'white--text' : 'gray--text'"
            id="btn-style"
            :style="
              items.length - 1 == index
                ? 'border-bottom-width: 4px;'
                : index == 0
                ? 'border-top-width:4px'
                : ''
            "
          >
            {{ item.title }}
          </v-btn>
        </v-btn-toggle>
      </div>
    </v-container>
  </v-container>
</template>

<script>
export default {
  name: "MypageView",
  components: {},
  data() {
    return {
      items: [
        { title: "정보수정", link: "/mypage/myinfo" },
        { title: "내가만든 일정", link: "/mypage/mytrip" },
        { title: "공유받은 일정", link: "/mypage/sharedtrip" },
        { title: "내가 쓴 글", link: "/mypage/myarticle" },
        { title: "좋아요한 글", link: "/mypage/mylike" },
        { title: "내가 쓴 댓글", link: "/mypage/mycomment" },
      ],
      tab: null,
    };
  },
  watch: {
    tab: function (newVal) {
      this.$router.push(newVal.link).catch(() => {});
    },
  },
  created() {},
};
</script>

<style scoped>
.theme--light.v-btn--active:hover::before,
.theme--light.v-btn--active::before {
  opacity: 0;
}
.v-btn-toggle > .v-btn.v-btn {
  border-top-left-radius: 0;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 10px;
  opacity: 1;
}
.v-application--is-ltr .v-btn-toggle {
  border-top-left-radius: 0;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 10px;
}
.content {
  border: solid;
  border-width: 5px;
  border-color: #4a8072;
  border-radius: 10px;
}
#btn-style {
  width: max-content;
  border-width: 2px;
  border-right-width: 4px;
  border-left-width: 0;
  border-color: #4a8072 !important;
  width: 120px;
}
</style>
