import Vue from "vue";
import VueRouter from "vue-router";

import HomeView from "../views/HomeView.vue";
import MypageView from "../views/MypageView.vue";
import PlanView from "../views/PlansView.vue";
import Recommend from "@/components/recommend/RecommendPlace.vue";
import getKakaoToken from "../components/KakaoLogin/GetKakaoToken.vue";
import NotLoginRedirect from "@/components/KakaoLogin/NotLoginRedirect.vue";
import CompleteView from "../views/CompleteView.vue";
import Chat from "@/components/chat/WebSocketTest.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "homeview", component: HomeView },
  {
    path: "/mypage",
    name: "mypage",
    component: MypageView,
  },
  {
    path: "/plans/:tripUrl",
    name: "plans",
    component: PlanView,
  },
  {
    path: "/login/getkakaotoken",
    name: "getKakaoToken",
    component: getKakaoToken,
  },
  {
    path: "/login/redirect",
    name: "NotLoginRedirect",
    component: NotLoginRedirect,
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
    // 임시
    path: "/recommend",
    name: "recommend",
    component: Recommend,
  },
  {
    path: "/complete/:tripUrl",
    name: "complete",
    component: CompleteView,
  },
  {
    //임시
    path: "/chat",
    name: "chat",
    component: Chat,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
