import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberJoin from "../components/member/MemberJoin.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: HomeView,
    },
    {
      path: "/member/login",
      name: "member-login",
      component: MemberLogin,
    },
    {
      path: "/member/regist",
      name: "member-join",
      component: MemberJoin,
    },
  ],
});

export default router;
