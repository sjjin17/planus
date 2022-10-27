import Vue from "vue";
import VueRouter from "vue-router";

import HomeView from "../views/HomeView.vue";
import kakaoRedirect from "../components/KakaoLogin/KakaoRedirect.vue";
import MypageView from "../views/MypageView.vue";
import PlanView from "../views/PlansView.vue";

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
    path: "/plans",
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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
