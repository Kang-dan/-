import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberJoin from "../components/member/MemberJoin.vue";
import MemberJoinWelcome from "../components/member/MemberJoinWelcome.vue";
import AttractionList from "../components/attraction/AttractionList.vue";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const { getMemberInfo } = useMemberStore;

const onlyAuthMember = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { memberInfo, isValidToken } = storeToRefs(memberStore);

  let token = sessionStorage.getItem("accessToken");
  console.log("로그인 처리 전", memberInfo, token);

  if (memberInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await getMemberInfo(token);
  }
  if (!isValidToken || memberInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "member-login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

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
