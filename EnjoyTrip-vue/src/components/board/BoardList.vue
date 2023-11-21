<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { boardList, boardWrite, boardView, boardUpdateHit } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const router = useRouter();
const route = useRoute();

const boards = ref([{}]);

const getBoardList = () => {
  boardList(
    route.params.sidoCode,
    ({ data }) => {
      boards.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

onMounted(() => {
  getBoardList();
});
</script>

<template>

  <div>
    test보드{{ route.params.sidoCode }}
    <div v-for="board in boards" :key="board.index">
      글번호 : {{ board.boardNo }} 글제목 : {{ board.boardTitle }}
    </div>
  </div>
</template>

<style scoped>
</style>