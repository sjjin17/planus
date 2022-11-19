<template>
  <v-dialog v-model="dialog" persistent max-width="600">
    <template v-slot:activator="{ on, attrs }">
      <h5
        v-bind="attrs"
        v-on="on"
        style="color: #544c4c"
        class="signOut"
        align="right"
      >
        탈퇴하기&nbsp;&nbsp;&nbsp;
      </h5>
    </template>
    <v-card>
      <v-card-title></v-card-title>
      <v-card-text
        >탈퇴하면 모든 여행기록과 게시글이 사라집니다. 정말
        탈퇴하시겠어요?</v-card-text
      >
      <v-card-actions>
        <v-spacer />
        <v-btn outlined color="#4a8072" @click="dialog = false"> 취소</v-btn>
        <v-btn outlined color="#ff1744" @click="signOut">탈퇴</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "KakaoSignOut",
  data: () => {
    return { dialog: false };
  },
  methods: {
    async signOut() {
      this.res = await api
        .signOut()
        .then(() => this.$cookies.remove("token"))
        .then(() => this.$cookies.remove("refresh"))
        .then(() => this.$router.push("/"));
    },
  },
};
</script>

<style scoped>
.signOut {
  position: relative;
  top: -200px;
}
</style>
