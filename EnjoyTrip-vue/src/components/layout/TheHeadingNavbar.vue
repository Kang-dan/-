<script setup>
import { RouterLink, useRouter } from "vue-router";

const router = useRouter();
function moveMain() {
  // router.replace({ path: "/" }); // 현재 라우트를 대체
  // router.push({ path: "/", replace: true }); // 위와 같음
  router.push({ name: "main" }); // 새 히스토리 항목을 푸시
}
</script>

<template>
  
</template>

<style scoped></style>
