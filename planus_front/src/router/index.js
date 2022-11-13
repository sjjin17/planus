import Vue from "vue";
import VueRouter from "vue-router";
// import CompletePage from "@/components/completetemp/CompletePage.vue";
import HomeView from "../views/HomeView.vue";
import MypageView from "../views/MypageView.vue";
import PlanView from "../views/PlansView.vue";
import Recommend from "@/components/recommend/RecommendPlace.vue";
import getKakaoToken from "../components/KakaoLogin/GetKakaoToken.vue";
import CompleteView from "../views/CompleteView.vue";
import Chat from "@/components/chat/WebSocketTest.vue";
import CommunityView from "../views/CommunityView.vue";
import ArticleView from "../views/ArticleView.vue";
import TokenTestButton from "@/components/KakaoLogin/TokenTestButton";
import ArticleNewView from "@/views/ArticleNewView";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "homeview", component: HomeView },
  {
    path: "/mypage",
    name: "mypage",
    component: MypageView,
    redirect: "/mypage/myinfo",
    children: [
      {
        path: "myinfo",
        name: "myinfo",
        component: () => import("@/components/mypage/MyInfoFix.vue"),
      },
      {
        path: "mytrip",
        name: "mytrip",
        component: () => import("@/components/mypage/MadeTripList.vue"),
      },
      {
        path: "sharedtrip",
        name: "sharedtrip",
        component: () => import("@/components/mypage/SharedTripList.vue"),
      },
      {
        path: "myarticle",
        name: "myarticle",
        component: () => import("@/components/mypage/MyArticleList.vue"),
      },
      {
        path: "mylike",
        name: "mylike",
        component: () => import("@/components/mypage/MyLikeList.vue"),
      },
      {
        path: "mycomment",
        name: "mycomment",
        component: () => import("@/components/mypage/MyCommentList.vue"),
      },
    ],
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
  // {
  //   path: "/completetest/:url",
  //   name: "completePage",
  //   component: CompletePage,
  // },
  {
    path: "/community",
    name: "community",
    component: CommunityView,
  },

  {
    path: "/article/:articleId",
    name: "article",
    component: ArticleView,
  },
  {
    path: "/test",
    name: "test",
    component: TokenTestButton,
  },
  {
    path: "/newArticle",
    name: "newArticle",
    component: ArticleNewView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
