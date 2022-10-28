import Vue from "vue";
import VueRouter from "vue-router";

import HomeView from "../views/HomeView.vue";
import kakaoRedirect from "../components/KakaoLogin/KakaoRedirect.vue";
import MypageView from "../views/MypageView.vue";
import PlanView from "../views/PlansView.vue";
import Recommend from "@/components/recommend/RecommendPlace.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "homeview", component: HomeView },
  {
    path: "/mypage",
    name: "mypage",
    component: MypageView,
  },
  {
    path: "/login/oauth2/kakao",
    name: "kakaoRedirect",
    component: kakaoRedirect,
  },
  {
<<<<<<< HEAD
    path: "/plans/:tripUrl",
=======
    path: "/plans",
>>>>>>> ad30b8bf4bf0a983636928a092ce701f6cf4b381
    name: "plans",
    component: PlanView,
  },
  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  // },
  {
    path: "/recommend",
    name: "recommend",
    component: Recommend,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
