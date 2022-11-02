<template>
  <div>
    <br />
    <br />
    <v-text-field v-if="modify" v-model="userNickname"> </v-text-field>
    <v-text-field v-else disabled :placeholder="userNickname"></v-text-field>
    <br />
    <br />
    <v-btn v-if="modify" @click="saveChange">저장</v-btn>
    <v-btn v-else @click="toModify">수정</v-btn>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;
export default {
  name: "MyInfo",
  created() {
    this.getMyInfo();
  },
  data: () => {
    return {
      originNickname: "",
      userNickname: "",
      modify: false,
    };
  },
  methods: {
    async getMyInfo() {
      var result = await api.getMyInfo();
      this.userNickname = result.nickname;
      this.originNickname = result.nickname;
    },
    toModify() {
      this.modify = !this.modify;
    },

    async saveChange() {
      if (this.userNickname != this.originNickname) {
        var result = await api.changeMyInfo(this.userNickname);
        console.log(result);
        var token = result.newToken;
        this.$cookies.set("token", token);
      }
      this.toModify();
    },
  },
};
</script>

<style></style>
