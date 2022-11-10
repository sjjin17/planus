<template>
  <v-container>
    <v-row class="d-flex justify-center mt-12">
      <img :src="imgUrl" width="160px" height="160px" />
    </v-row>
    <v-row class="d-flext justify-center mt-10">
      <h3>
        <span class="font-weight-bold" style="color: #4a8072">닉네임</span>
      </h3>
    </v-row>
    <v-row class="d-flex justify-center">
      <v-col cols="4">
        <v-text-field
          v-if="modify"
          v-model="userNickname"
          rounded
          outlined
          dense
          color="#4a8072"
          class="centered-input font-weight-bold"
        >
        </v-text-field>
        <v-text-field
          v-else
          :placeholder="userNickname"
          rounded
          outlined
          disabled
          dense
          color="#4a8072"
          class="centered-input font-weight-bold"
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="d-flex justify-center">
      <v-btn v-if="modify" @click="saveChange" color="#4A8072"
        ><span class="font-weight-bold" style="color: white">저장</span></v-btn
      >
      <v-btn v-else @click="toModify" color="#4A8072"
        ><span class="font-weight-bold" style="color: white">수정</span></v-btn
      >
    </v-row>
  </v-container>
</template>

<script>
import API from "@/api/RESTAPI";
import jwt_decode from "jwt-decode";
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
      imgUrl: "",
    };
  },
  methods: {
    async getMyInfo() {
      await api.dummy();
      var jwt = jwt_decode(this.$cookies.get("token"));
      console.log(jwt);
      this.userNickname = jwt.nickname;
      this.originNickname = jwt.nickname;
      this.imgUrl = jwt.imageUrl;
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
        this.originNickname = this.userNickname;
      }
      this.toModify();
    },
  },
};
</script>

<style scoped>
.centered-input >>> input {
  text-align: center;
}
</style>
