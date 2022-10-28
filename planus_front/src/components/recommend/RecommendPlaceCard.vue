<template>
  <v-container>
    <v-card>
      <v-container>
        <v-row class="pt-1 row-9" align="center">
          <v-col cols="5">
            <v-img :src="recommendPlace.imgUrl" class="ml-1" max-> </v-img>
          </v-col>
          <v-col cols="7" align="left">
            <v-row>
              <v-col cols="9">
                <h3 class="ml-3">
                  <span class="dark--text">{{ recommendPlace.place }}</span>
                </h3>
              </v-col>
              <v-col cols="3">
                <span class="semidark--text"
                  >{{ recommendPlace.contentType }}
                </span>
              </v-col>
            </v-row>
            <v-row>{{ recommendPlace.address }}</v-row>
          </v-col>
        </v-row>
        <v-row class="row-3">
          <v-btn>아</v-btn>
          <v-btn>가</v-btn>
        </v-row>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {};
  },
  props: {
    recommendPlace: Object,
  },
};
</script>

<style></style>
