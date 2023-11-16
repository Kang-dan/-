<script setup>
import { RouterLink, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const router = useRouter();
function moveMain() {
  // router.replace({ path: "/" }); // 현재 라우트를 대체
  // router.push({ path: "/", replace: true }); // 위와 같음
  router.push({ name: "main" }); // 새 히스토리 항목을 푸시
}

const logout = () => {
  memberStore.memberLogout(memberInfo.value.memberId)
}
</script>

<template>
  <button v-if="isLogin" @click.prevent="logout()">로그아웃</button>
</template>

<style scoped></style>
