<template>
  <div>
    <v-card>
      <v-card-title>{{ this.timetable.place }}</v-card-title>
      <v-card-text>
        {{ this.timetable.costTime }}
        {{ this.startTime }}
        {{ this.endTime }}
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "TimetableCard",
  data() {
    return {
      endTime: 0,
    };
  },
  props: {
    timetable: Object,
    startTime: Number,
  },

  mounted() {
    this.endTime = this.startTime + this.timetable.costTime;
    this.$emit("changeCalTime", this.endTime);
  },
};
</script>

<style></style>
