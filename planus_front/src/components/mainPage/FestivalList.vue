<template>
  <div>
    <div class="bestAreaHeader">인기지역</div>
    <div>
      <v-row align="center" justify="center">
        <v-col v-for="(area, i) in areaList" :key="i" :area="area" cols="2">
          <v-card>
            <v-img :src="area.imageUrl"> </v-img>
            <div align="center">{{ area.siName }}</div>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <div class="bestAreaHeader">축제정보</div>
  </div>
</template>

<script>
import API from "@/api/RESTAPI";
const api = API;

export default {
  data: () => ({
    areaList: [],
  }),
  created() {
    this.getBestArea();
  },
  methods: {
    async getBestArea() {
      this.res = await api.getBestArea();
      this.areaList = this.res.areaList;
      console.log(this.areaList);
    },
  },
};
</script>

<style>
.bestAreaHeader {
  display: flex;
  padding: 2%;
  justify-content: center;
  font-size: 1.8rem;
  font-weight: 700;
  color: white;
}
</style>
