<script setup>
import { ref, onMounted, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import { boardList, boardWrite, boardView, boardUpdateHit } from "@/api/board";
import { letterList, letterView, letterUpdateHit } from "@/api/letter";
import { loveListOne } from "@/api/member";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import BoardWriteModal from "@/components/board/BoardWriteModal.vue";
import BoardDetailModal from "@/components/board/BoardDetailModal.vue";
import BoardLetterDetailModal from "@/components/board/BoardLetterDetailModal.vue";
import BoardMyListModal from "@/components/board/BoardMyListModal.vue";
import btnClickSound from "@/assets/sound/sound_btn_click.mp3";

import { onMessage, onOpen, onClose, onError } from "vue3-websocket";

const text = ref("");
const responseMsg = ref("");

const socket = inject("socket");

const sendMessage = () => socket.value.send(text.value);

// 스크롤을 맨 아래로 자동으로 내림
window.scrollTo({
  top: document.documentElement.scrollHeight, // 문서 전체의 높이
  behavior: "smooth", // 부드러운 스크롤 적용
});

onOpen((obj) => {
  console.log(obj);
  console.log("소켓 연결 성공");
});

onMessage((message) => {
  responseMsg.value = message.data;
  console.log("소켓으로 메시지 받음! ", message);
  getBoardList();
});

onClose((obj) => {
  console.log(obj);
  console.log("소켓 연결 끊김");
});

onError((error) => {
  console.error("소켓 에러 발생: ", error);
});

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false); //게시글 쓸 때 열 모달창
const isDetailModalOpen = ref(false); //상세화면 모달창
const isMyListModalOpen = ref(false); //마이리스트 모달창
const isLetterModalOpen = ref(false);

const router = useRouter();
const route = useRoute();

const boards = ref([{}]);
const letters = ref([{}]);

const boardDetail = ref({});
const letterDetail = ref({});
const loveLength = ref({});

const listAction = ref("board");

const getboardLoveOne = () => {
  loveListOne();
};

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
  if (memberInfo.value) {
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
};

onMounted(() => {
  getBoardList();
  getLetterList();
  // 화면 로드 시 맨 아래로 스크롤
  const targetPosition = 400; // 멈추길 원하는 스크롤 위치
  const scrollDuration = 1800; // 전체 스크롤에 걸리는 시간 (밀리초)

  const startScroll = () => {
    const startTime = performance.now(); // 시작 시간
    const startTop = window.pageYOffset || document.documentElement.scrollTop; // 시작 지점

    const scroll = (currentTime) => {
      const timeElapsed = currentTime - startTime; // 경과 시간
      const progress = Math.min(timeElapsed / scrollDuration, 1); // 진행률 (0부터 1까지)

      window.scrollTo(0, startTop + (targetPosition - startTop) * progress); // 스크롤 이동

      if (timeElapsed < scrollDuration) {
        // 스크롤이 완료되지 않았으면 애니메이션 계속
        requestAnimationFrame(scroll);
      }
    };

    // 애니메이션 시작
    requestAnimationFrame(scroll);
  };

  startScroll(); // 스크롤 시작
});

/** 사운드 */
const buttonSound = new Howl({
  src: [btnClickSound],
});

const playButtonSound = () => {
  try {
    buttonSound.play();
  } catch (error) {
    console.error("playButtonSound 함수에서 에러:", error);
  }
};

const changeListAction = () => {
  listAction.value = "board";
};

const toggleSound = (listType) => {
  playButtonSound(); // 소리 재생

  if (listType === "letter") {
    listAction.value = "board"; // 쪽지 리스트로 변경
  } else {
    listAction.value = "letter"; // 보드 리스트로 변경
  }
};

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

const getLoveOne = () => {
  loveListOne(
    {
      memberNo: memberInfo.value.memberNo,
      boardNo: boardDetail.value.boardNo,
    },
    ({ data }) => {
      console.log("좋아요 가져오기");
      loveLength.value = data.length;
      console.log(loveLength.value);
    },
    (err) => {
      console.log(err);
    }
  );
};

const loveChangeEmit = (no) => {
  getLoveOne();
  getBoardDetail(no);
};

const getBoardDetail = (no) => {
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
    getBoardDetail(no);
    getLoveOne();
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
    const modalMylist = document.querySelector("#modalMyList.modal-overlay");
    modalMylist.classList.add("show");
  }
};

const showModalLetter = async (no) => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    await letterUpdateHit(
      no,
      ({ data }) => {
        console.log(data);
      },
      (err) => {
        console.log(err);
      }
    );
    letterView(
      no,
      ({ data }) => {
        letterDetail.value = data;
        isLetterModalOpen.value = true;
      },
      (err) => {
        console.log(err);
      }
    );
    // 모달이 나타날 때 show 클래스 추가
    const modalLetter = document.querySelector("#modalLetter.modal-overlay");
    modalLetter.classList.add("show");
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
    <div class="board_letter_change_btn">
      <button
        class="letter_btn"
        v-if="listAction === 'letter'"
        @click="toggleSound('letter')"
      >
        보드 리스트 보기
      </button>
      <button
        class="every_btn"
        v-if="listAction === 'board'"
        @click="toggleSound('board')"
      >
        쪽지 리스트 보기
      </button>
    </div>
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
          <template v-for="letter in letters">
            <img
              class="board_leaf"
              v-if="letter.memberNoTo == memberInfo.memberNo"
              :src="`src/assets/board/board_leaf_${letter.letterImg}.png`"
              :style="`left:${letter.letterX}px; top:${letter.letterY}px`"
              @click="showModalLetter(letter.letterNo)"
            />
          </template>
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

  <BoardWriteModal
    @getBoardList="getBoardList"
    @getLetterList="getLetterList"
    @changeListAction="changeListAction"
    :isOpen="isModalOpen"
  >
  </BoardWriteModal>
  <BoardDetailModal
    @getBoardList="getBoardList"
    @getLetterList="getLetterList"
    :isOpen="isDetailModalOpen"
    :boardDetail="boardDetail"
    :loveLength="loveLength"
    @loveChangeEmit="loveChangeEmit"
  >
  </BoardDetailModal>
  <BoardMyListModal
    @getBoardList="getBoardList"
    @getLetterList="getLetterList"
    :isOpen="isMyListModalOpen"
    :boards="boards"
    :letters="letters"
  ></BoardMyListModal>
  <BoardLetterDetailModal
    @getBoardList="getBoardList"
    @getLetterList="getLetterList"
    :isOpen="isLetterModalOpen"
    :letterDetail="letterDetail"
  >
  </BoardLetterDetailModal>
</template>


<style scoped>
/** 보드 쪽지 토글 버튼 꾸미기*/
.board_letter_change_btn {
  position: relative;
  margin: 0 auto;
  top: 30%;
  transform: translateY(-40%);
}
.board_letter_change_btn button {
  padding: 10px;
  border: none;
  border-radius: 10%;
  outline: none;
  color: white;
  box-shadow: 0 4px 8px rgba(255, 255, 255, 0.5); /* 그림자 효과 */
  transition: transform 0.3s, box-shadow 0.3s; /* 애니메이션 효과 */
}

.board_letter_change_btn .letter_btn {
  background-color: rgba(212, 190, 92, 0.685);
  cursor: pointer;
}

.board_letter_change_btn .every_btn {
  background-color: rgba(45, 123, 175, 0.753);
  cursor: pointer;
}

.board_letter_change_btn .letter_btn:hover,
.board_letter_change_btn .every_btn:hover {
  transform: translateY(-2px); /* 마우스 호버 시 약간 위로 이동 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 그림자 크게 */
}

/* 클릭 시 버튼이 약간 아래로 이동하는 애니메이션 */
.board_letter_change_btn .letter_btn:active,
.board_letter_change_btn .every_btn:active {
  transform: translateY(2px); /* 클릭 시 아래로 이동 */
}

.random_leaf {
  position: relative;
}

.board_background {
  background-image: url("@/assets/board/board_background6.jpeg");
  /* background-color: darkblue; */
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
  transform: translate(-560px, 290px) rotate(-50deg);
  height: 100px;
  cursor: pointer;
  transition: transform 1s ease;

  /* visibility: hidden; */
}

.board_search:hover {
  transform: translate(-560px, 290px) rotate(310deg);
}

.board_write {
  width: 130px;
  position: absolute;
  transform: translate(-440px, 455px);
  cursor: pointer;

  /* visibility: hidden; */
}

.board_write:hover {
  transform: translate(-440px, 455px) scale(1.2);
}
</style>