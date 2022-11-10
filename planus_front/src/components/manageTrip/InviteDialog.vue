<template>
  <v-dialog v-model="dialog">
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        depressed
        color="primary"
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
        <v-btn depressed color="primary" outlined @click="copyUrl"
          >링크 복사</v-btn
        >
      </v-card-actions>
      <v-card-title class="dialogtitle">참가자 목록</v-card-title>
      <v-card-text v-for="(member, i) in memberList" :key="i" :member="member">
        <v-row>
          <v-col>
            <v-btn
              depressed
              rounded
              color="primary"
              white--text
              v-if="admin == member.userId"
              >방장</v-btn
            >
            <v-btn depressed rounded white--text v-else>방장변경</v-btn>
          </v-col>
          <v-col>
            <div class="memberName">{{ member.name }}</div>
          </v-col>
          <v-col>
            <div class="memberEmail" v-show="member.email != null">
              ({{ member.email }})
            </div>
          </v-col>
          <v-col>
            <v-btn
              depressed
              rounded
              color="primary"
              white--text
              v-if="connector.includes(member.userId + '')"
              >접속</v-btn
            >
            <v-btn depressed rounded white--text v-else>접속안함</v-btn>
          </v-col>
        </v-row>
      </v-card-text>
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
    admin: Number,
    connector: Array,
  },
  created() {},
  methods: {
    async getMemberList() {
      this.$emit("getConnector");
      this.res = await api.getMemberList(this.tripId);
      this.memberList = this.res.memberList;
      console.log(this.memberList);
      console.log(this.connector);
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
