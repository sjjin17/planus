<template>
  <div>
    <v-select :items="searchBy" label="검색방법"></v-select>
    <v-text-field dense></v-text-field>
    <v-btn>검색</v-btn>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchBy: ["제목", "지역"],
    };
  },
};
</script>

<style></style>
