<template>
  <div>
    <v-col
      >{{ timeTable.place }} 시작시간:
      {{
        parseInt(startTime / 60)
          .toString()
          .padStart(2, 0)
      }}:{{ (startTime % 60).toString().padStart(2, 0) }} 종료시간:
      {{
        parseInt(endTime / 60)
          .toString()
          .padStart(2, 0)
      }}:{{ (endTime % 60).toString().padStart(2, 0) }} 이동시간:
      {{ moveTime }}</v-col
    >
  </div>
</template>

<script>
export default {
  name: "TimeTableCard",
  props: {
    timeTable: Object,
    startTime: Number,
    endTime: Number,
    moveTime: Number,
  },
};
</script>

<style></style>
