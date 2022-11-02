<template>
  <v-col>
    <v-dialog v-model="dialog" max-width="600px">
      <template v-slot:activator="{ on, attrs }">
        <v-btn style="margin: 0" v-bind="attrs" v-on="on"> 일정에 추가 </v-btn>
      </template>
      <v-card>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                  type="number"
                  required
                  v-model="hours"
                  :min="0"
                  :max="11"
                ></v-text-field>
              </v-col>
              <v-col> 시간 </v-col>
              <v-col>
                <v-text-field
                  type="number"
                  required
                  v-model="minutes"
                  :min="0"
                  :max="59"
                ></v-text-field>
              </v-col>
              <v-col> 분 </v-col>
              <v-col>
                <v-btn
                  color="blue darken-1"
                  @click="submit"
                  :disabled="!timeFlag"
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
      this.$emit("planSubmit", costTime);
      this.dialog = false;
    },
  },
};
</script>

<style></style>
