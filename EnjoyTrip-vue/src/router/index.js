import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberJoin from "../components/member/MemberJoin.vue";
import MemberJoinWelcome from "../components/member/MemberJoinWelcome.vue";
import AttractionList from "../components/attraction/AttractionList.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: HomeView,
    },
    {
      path: "/parsing",
      name: "parsing",
      component: () => import("@/views/DBParsingView.vue"),
    },
    {
      path: "/member",
      name: "member",
      component: () => import("@/views/MemberView.vue"),
      children: [
        {
          path: "login",
          name: "member-login",
          component: MemberLogin,
        },
        {
          path: "regist",
          name: "member-join",
          component: MemberJoin,
        },
        {
          path: "regist/welcome",
          name: "member-join-welcome",
          component: MemberJoinWelcome,
        },
      ],
    },
    {
      path: "/attraction",
      name: "attraction",
      component: () => import("@/views/AttractionView.vue"),
      children: [
        {
          path: "list/:sidoCode", //sidoCode로 화면 넘김
          name: "attraction-list",
          component: AttractionList,
        },
      ],
    },
  ],
});

export default router;
