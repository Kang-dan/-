<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { boardList, boardWrite, boardView, boardUpdateHit } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import BoardWriteModal from "@/components/board/BoardWriteModal.vue";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false); //게시글 쓸 때 열 모달창

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

/** 게시글 작성 - 모달창 */
const showModal = (detail) => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    // getMypage();
    isModalOpen.value = true;
    // 모달이 나타날 때 show 클래스 추가
    const modal = document.querySelector("#modal.modal-overlay");
    modal.classList.add("show");
  }
};

const moveBoardWrite = () => {
  // mypage-bag 이미지를 클릭했을 때 모달창 열도록 설정
  showModal();
};
</script>

<template>
  <!-- <div>
    test보드{{ route.params.sidoCode }}
    <div v-for="board in boards" :key="board.index">
      글번호 : {{ board.boardNo }} 글제목 : {{ board.boardTitle }}
    </div>
  </div> -->

  <div class="board_background">
    <div class="board_tree">
      <div class="random_leaf">
        <img class="board_leaf" src="@/assets/board/board_leaf_1.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_2.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_3.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_4.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_5.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_6.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_7.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_8.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_9.png" alt="" />
        <img class="board_leaf" src="@/assets/board/board_leaf_10.png" alt="" />
      </div>
      <img
        class="board_search"
        src="@/assets/board/board_region_hole.png"
        alt=""
      />
      <img
        class="board_write"
        src="@/assets/board/board_mindle_hole.png"
        alt=""
        @click="moveBoardWrite"
      />
    </div>
  </div>

  <div id="modal_div">
    <!-- <button @click="showModal">모달 열기</button> -->
    <teleport to="body" v-if="isModalOpen">
      <BoardWriteModal></BoardWriteModal>
    </teleport>
  </div>
</template>


<style scoped>
.board_background {
  /* background-image: url("@/assets/board/board_background6.jpeg"); */
  background-color: darkblue;
  /* width: 1000px;
  height: 1000px; */
  background-size: cover;
  background-position: center;
  text-align: center;
  vertical-align: middle;
  margin: -100px auto;

  /* width: 90; */
  /* margin-top: 0px; */
  width: 100%;
  height: 220%;

  /* visibility: hidden; */
}

.board_tree {
  background-image: url("@/assets/board/board_tree.png");
  background-size: cover;
  height: 500px;
  width: 500px;
  margin: 0 auto;
  position: relative; /* 부모(.board_background)에 대해 상대 위치로 지정. */
  top: 60%; /* 부모(.board_background)의 세로 이동. */
  transform: translateY(-50%); /* 자기 자신의 높이의 절반만큼 위로 이동. */

  /* visibility: hidden; */
}

.board_leaf {
  width: 100px;
  z-index: 999;

  /* visibility: hidden; */
}

.board_leaf:hover {
  cursor: pointer;
}

.board_search {
  position: absolute;
  transform: translate(-560px, -50px) rotate(-50deg);
  height: 100px;
  cursor: pointer;
  transition: transform 1s ease;

  /* visibility: hidden; */
  
}

.board_search:hover {
  transform: translate(-560px, -50px) rotate(310deg);
}

.board_write {
  width: 130px;
  position: absolute;
  transform: translate(-440px, 110px);
  cursor: pointer;

  /* visibility: hidden; */
}

.board_write:hover {
  transform: translate(-440px, 110px) scale(1.2);
}
</style>