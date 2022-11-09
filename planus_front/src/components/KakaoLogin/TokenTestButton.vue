<template>
  <div>
    <button @click="test">테스트하기</button>
  </div>
</template>

<script>
// import axios from "axios";
// import VueCookies from "vue-cookies";

// axios.interceptors.request.use(async (request) => {
//   if (VueCookies.get("token") != null) {
//     request.headers.Authorization = "Bearer " + VueCookies.get("token");
//     return request;
//   } else {
//     if (VueCookies.get("refresh") != null) {
//       var temp = axios.create();
//       await temp
//         .patch("http://localhost:8080/planus/login", {
//           refreshToken: VueCookies.get("refresh"),
//         })
//         .then((res) => {
//           VueCookies.set("token", res.data.newToken);
//           request.headers.Authorization = "Bearer " + VueCookies.get("token");
//         })
//         .catch((error) => console.log(error));
//     }
//   }
//   return request;
// });

export default {
  // name: "TokenTestButton.vue",
  // methods: {
  //   test() {
  //     // var temp = this;
  //     axios.defaults.headers.common["Authorization"] =
  //       "Bearer " + this.$cookies.get("token");
  //     axios
  //       //로컬
  //       .get("http://localhost:8080/planus/login/test")
  //       .then((res) => {
  //         console.log(res);
  //       })
  //       .catch((error) => {
  //         console.log(error);
  //       });
  //   },
  //   testRefresh() {
  //     axios
  //       .patch("http://localhost:8080/planus/login")
  //       .then((res) => console.log(res))
  //       .catch((error) => console.log(error));
  //   },
  // },
};
</script>

<style></style>
