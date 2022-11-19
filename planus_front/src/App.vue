<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  data: () => ({
    //
  }),
};
</script>

<style>
table {
  table-layout: fixed !important;
  border-color: #b8dbc6;
  padding-left: 1vw;
  padding-right: 1vw;
}
</style>
