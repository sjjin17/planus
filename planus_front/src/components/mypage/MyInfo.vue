<template>
  <v-container>
    <v-row class="d-flex justify-center mt-12">
      <img :src="imgUrl" width="160px" height="160px" />
    </v-row>
    <v-row class="d-flex justify-center mt-10">
      <h3>
        <span class="font-weight-bold" style="color: #4a8072">닉네임</span>
      </h3>
    </v-row>
    <v-row class="d-flex justify-center">
      <v-col cols="4">
        <v-text-field
          v-model="userNickname"
          rounded
          outlined
          :rules="[rules.special, rules.required, rules.space, rules.maxCount]"
          :readonly="!modify"
          dense
          counter
          maxlength="20"
          color="#4a8072"
          class="centered-input font-weight-bold"
          @keyup.enter="saveChange"
        >
        </v-text-field>
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
      validateMessage: "12345",
      rules: {
        space: (value) => {
          return value.indexOf(" ") == -1 || "공백 ㄴㄴ";
        },
        required: (value) => {
          return !!value || "뭐함??";
        },
        special: (value) => {
          return !/[~!@#$%^&*()_+|<>?:{}]/.test(value) || "특수문자 ㄴㄴ";
        },
        maxCount: (value) => {
          return value.length <= 20 || "20자 초과 ㄴㄴ";
        },
      },
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
      if (this.userNickname == this.originNickname) {
        this.toModify();
      } else {
        if (
          this.space(this.userNickname) &&
          this.required(this.userNickname) &&
          this.special(this.userNickname) &&
          this.maxCount(this.userNickname)
        ) {
          var result = await api.changeMyInfo(this.userNickname);
          var token = result.newToken;
          this.$cookies.set("token", token, 30 * 60);
          this.originNickname = this.userNickname;
          this.toModify();
        }
      }
    },
    space: (value) => {
      return value.indexOf(" ") == -1;
    },
    required: (value) => {
      return !!value;
    },
    special: (value) => {
      return !/[~!@#$%^&*()_+|<>?:{}]/.test(value);
    },
    maxCount: (value) => {
      return value.length <= 20;
    },
  },
};
</script>

<style scoped>
.centered-input >>> input {
  text-align: center;
}
</style>
