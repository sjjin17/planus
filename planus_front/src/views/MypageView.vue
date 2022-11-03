<template>
  <v-container>
    <h1>마이페이지</h1>
    <div style="display: flex; justify-content: end; padding-right: 10%">
      <v-btn-toggle v-model="tab">
        <v-btn
          class="rounded-btn"
          x-large
          v-for="(item, index) in items"
          :value="item"
          :key="index"
          :color="tab == item ? '#4A8072' : 'white'"
          :class="tab == item ? 'white--text' : 'gray--text'"
          id="btn-style"
        >
          {{ item.title }}
        </v-btn>
      </v-btn-toggle>
    </div>
    <v-sheet height="80vh" width="100%" class="content">
      <router-view />
    </v-sheet>
  </v-container>
</template>

<script>
export default {
  name: "MypageView",
  components: {},
  data() {
    return {
      items: [
        { title: "정보수정", link: "/mypage/myinfofix" },
        { title: "내가만든 일정", link: "/mypage/made" },
        { title: "공유받은", link: "/mypage/shared" },
      ],
      tab: null,
    };
  },
  watch: {
    tab: function (newVal) {
      this.$router.push(newVal.link).catch(() => {});
    },
  },
  created() {
    this.tab = this.items[0];
  },
};
</script>

<style scoped>
.theme--light.v-btn--active:hover::before,
.theme--light.v-btn--active::before {
  opacity: 0;
}
.v-btn-toggle > .v-btn.v-btn {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
  opacity: 1;
}
.v-application--is-ltr .v-btn-toggle {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}
.content {
  border: solid;
  border-width: 5px;
  border-color: #4a8072;
  border-radius: 10px;
}
#btn-style {
  border-width: 5px;
  border-bottom-width: 0;
  border-color: #4a8072 !important;
}
</style>
