<template>
  <v-dialog v-model="dialog">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        depressed
        color="primary"
        white--text
        outlined
        v-bind="attrs"
        v-on="on"
        @click="getMemberList"
      >
        초대
      </v-btn>
    </template>
    <v-card style="margin: auto">
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn depressed color="primary" white--text outlined @click="copyUrl"
          >링크 복사</v-btn
        >
      </v-card-actions>
      <v-card-title class="dialogtitle">참가자 목록</v-card-title>
      <v-card-text v-for="(member, i) in memberList" :key="i" :member="member"
        >{{ member.name }}({{ member.email }})</v-card-text
      >
    </v-card>
  </v-dialog>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  data() {
    return {
      dialog: false,
      memberList: [
        {
          userId: 0,
          name: "",
          email: "",
        },
      ],
    };
  },
  props: {
    tripId: Number,
    tripUrl: String,
  },
  async created() {},
  methods: {
    async getMemberList() {
      console.log(this.tripId);
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
      console.log(this.memberList);
      console.log(this.tripId);
    },
    copyUrl() {
      navigator.clipboard.writeText(window.location.href).then(() => {
        window.alert("링크가 복사되었습니다! 친구를 초대해보세요!");
      });
    },
  },
};
</script>

<style></style>
