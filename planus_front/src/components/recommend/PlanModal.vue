<template>
  <v-col>
    <v-dialog v-model="dialog" max-width="300px">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          width="100%"
          small
          color="#FF1744"
          class="white--text ma-0"
          v-bind="attrs"
          v-on="on"
        >
          일정에 추가
        </v-btn>
      </template>
      <v-card>
        <v-card-text class="pa-3">
          <v-container>
            <v-row class="pa-0">
              <v-col class="ma-0 pa-0" cols="3">
                <v-text-field
                  class="centered-input"
                  type="number"
                  required
                  v-model="hours"
                  :min="0"
                  :max="11"
                  outlined
                  hide-details
                  color="#4A8072"
                ></v-text-field>
              </v-col>
              <v-col class="ma-0 pa-0" cols="2" style="align-self: center">
                <h2>시간</h2>
              </v-col>
              <v-col class="ma-0 pa-0" cols="3">
                <v-text-field
                  class="centered-input"
                  outlined
                  type="number"
                  required
                  v-model="minutes"
                  :min="0"
                  :max="59"
                  color="#4A8072"
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col class="ma-0 pa-0" style="align-self: center">
                <h2>분</h2>
              </v-col>
              <v-col class="ma-0 pa-0" style="align-self: center">
                <v-btn
                  color="#4A8072"
                  @click="submit"
                  :disabled="!timeFlag"
                  style="color: white"
                >
                  등록
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-col>
</template>
<script>
export default {
  data: () => ({
    dialog: false,
    hours: 2,
    minutes: 0,
  }),
  props: {
    fromBucket: Boolean,
  },
  computed: {
    timeFlag() {
      if (String(this.hours).length > 2 || String(this.minutes).length > 2) {
        return false;
      }
      if (this.hours == 0 && this.minutes == 0) {
        return false;
      }
      if (
        !Number.isInteger(Number(this.hours)) ||
        !Number.isInteger(Number(this.minutes))
      ) {
        return false;
      }
      if (!this.hours && !this.minutes) {
        return false;
      }
      if (this.hours < 0 || this.hours > 11) {
        return false;
      }
      if (this.minutes < 0 || this.minutes > 59) {
        return false;
      }
      if (this.hours === "" || this.minutes === "") {
        return false;
      }
      return true;
    },
  },
  watch: {
    dialog(newData) {
      if (!newData) {
        this.hours = 2;
        this.minutes = 0;
      }
    },
  },
  methods: {
    submit() {
      let costTime = this.hours * 60 + this.minutes * 1;
      this.$emit("planSubmit", costTime, this.fromBucket);
      this.dialog = false;
    },
  },
};
</script>

<style>
.centered-input input {
  text-align: center;
}
</style>
