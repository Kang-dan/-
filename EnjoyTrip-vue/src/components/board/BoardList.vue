<script setup>
import { ref, onMounted, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import { boardList, boardWrite, boardView, boardUpdateHit } from "@/api/board";
import { letterList } from "@/api/letter";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import BoardWriteModal from "@/components/board/BoardWriteModal.vue";
import BoardDetailModal from "@/components/board/BoardDetailModal.vue";
import BoardMyListModal from "@/components/board/BoardMyListModal.vue";

import { onMessage, onOpen, onClose, onError } from 'vue3-websocket'

const text = ref('');
const responseMsg = ref('');

const socket = inject('socket');

const sendMessage = () => socket.value.send(text.value);

onOpen((obj) => {
  console.log(obj);
  console.log('소켓 연결 성공');
});

onMessage((message) => {
  responseMsg.value = message.data;
  console.log('소켓으로 메시지 받음! ', message);
  getBoardList();
});

onClose((obj) => {
  console.log(obj);
  console.log('소켓 연결 끊김');
});

onError((error) => {
  console.error('소켓 에러 발생: ', error);
});

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false); //게시글 쓸 때 열 모달창
const isDetailModalOpen = ref(false); //게시글 쓸 때 열 모달창
const isMyListModalOpen = ref(false); //게시글 쓸 때 열 모달창

const router = useRouter();
const route = useRoute();

const boards = ref([{}]);
const letters = ref([{}]);

const boardDetail = ref({});
const letterDetail = ref({});

const listAction = ref('board');

const getBoardList = () => {
  boardList(
    ({ data }) => {
      boards.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const getLetterList = () => {
  if (memberInfo) {
    letterList(
      memberInfo.value.memberNo,
      ({ data }) => {
        letters.value = data;
      },
      (err) => {
        console.log(err);
      }
    );    
  }
}

onMounted(() => {
  getBoardList();
});

/** 게시글 작성 - 모달창 */
const showModal = (detail) => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    isModalOpen.value = true;
    // 모달이 나타날 때 show 클래스 추가
    const modal = document.querySelector("#modal.modal-overlay");
    modal.classList.add("show");
  }
};

const showModalDetail = async (no) => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    await boardUpdateHit(
      no,
      ({ data }) => {
        console.log(data);
      },
      (err) => {
        console.log(err);
      }
    );
    boardView(
      no,
      ({ data }) => {
        boardDetail.value = data;
        isDetailModalOpen.value = true;
      },
      (err) => {
        console.log(err);
      }
    );
    // 모달이 나타날 때 show 클래스 추가
    const modalDetail = document.querySelector("#modalDetail.modal-overlay");
    modalDetail.classList.add("show");
  }
};

const showModalMyList = () => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    isMyListModalOpen.value = true;

    // 모달이 나타날 때 show 클래스 추가
    const modalDetail = document.querySelector("#modalMyList.modal-overlay");
    modalDetail.classList.add("show");
  }
};

const moveBoardWrite = () => {
  showModal();
};
</script>

<template>
  <!-- <input v-model="text"/>
    <button @click="sendMessage">Send a message</button>
  <input v-model="responseMsg"> -->

  <div class="board_background">
    <div class="board_tree">
      <div class="random_leaf">
        <template v-if="listAction === 'board'">
          <img
            class="board_leaf"
            v-for="board in boards"
            :src="`src/assets/board/board_leaf_${board.boardImg}.png`"
            :style="`left:${board.boardX}px; top:${board.boardY}px`"
            @click="showModalDetail(board.boardNo)"
          />          
        </template>
        <template v-if="listAction === 'letter'">
          <img
            class="board_leaf"
            v-for="letter in letters"
            :src="`src/assets/board/board_leaf_${letter.letterImg}.png`"
            :style="`left:${letter.letterX}px; top:${letter.letterY}px`"
            @click="showModalDetail(letter.letterNo)"
          />          
        </template>
      </div>
      <img
        class="board_search"
        src="@/assets/board/board_region_hole.png"
        alt=""
        @click="showModalMyList()"
      />
      <img
        class="board_write"
        src="@/assets/board/board_mindle_hole.png"
        alt=""
        @click="moveBoardWrite"
      />
    </div>
  </div>

  <BoardWriteModal @getBoardList="getBoardList" :isOpen="isModalOpen">
  </BoardWriteModal>
  <BoardDetailModal
    @getBoardList="getBoardList"
    :isOpen="isDetailModalOpen"
    :boardDetail="boardDetail"
  >
  </BoardDetailModal>
  <BoardMyListModal
    :isOpen="isMyListModalOpen"
    :boards="boards"
  ></BoardMyListModal>
</template>


<style scoped>
.random_leaf {
  position: relative;
}

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
  height: 600px;
  width: 600px;
  margin: 0 auto;
  position: relative; /* 부모(.board_background)에 대해 상대 위치로 지정. */
  top: 60%; /* 부모(.board_background)의 세로 이동. */
  transform: translateY(-50%); /* 자기 자신의 높이의 절반만큼 위로 이동. */

  /* visibility: hidden; */
}

.board_leaf {
  width: 100px;
  z-index: 999;
  position: absolute;
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