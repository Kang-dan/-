<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { boardWrite } from "@/api/board";
import { letterWrite } from "@/api/letter";
import { storeToRefs } from "pinia";
import { Howl } from "howler";
import btnClickSound from "@/assets/sound/pick.mp3";

const memberStore = useMemberStore();
const { memberInfo, getMemberInfo, memberLogout } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const isModalOpen = ref(false);
const emit = defineEmits(["getBoardList", "getLetterList", "changeListAction"]);
const closeModal = () => {
  isModalOpen.value = false;
  emit("getBoardList");
  emit("getLetterList");
  // 모달을 닫을 때 show 클래스 제거
  const modal = document.querySelector("#modal.modal-overlay");
  if (modal) {
    modal.classList.remove("show");
  }
};
/** 모달창(디테일) 테스트 끝 */

const board = ref({
  boardTitle: "",
  memberId: "",
  boardContent: "",
  boardX: 0,
  boardY: 0,
  boardImg: 0,
});

const letter = ref({
  letterTitle: "",
  memberNoFrom: 0,
  memberIdFrom: "",
  memberIdTo: "",
  letterContent: "",
  letterX: 0,
  letterY: 0,
  letterImg: 0,
});

const checkAction = ref("board");

const formSubmit = (action) => {
  let left = Math.floor(Math.random() * 701);
  left = left - 100;
  let top = 0;

  if (left > 550 || left < -50) {
    top = Math.floor(Math.random() * 361);
    top = top - 30;
    if (left > 550) {
      if (top > 180) top = top - (left - 550);
      else top = top + (left - 550);
    } else {
      if (top > 180) top = top + (left + 50);
      else top = top - (left + 50);
    }
  } else if (left > 400 || left < 100) {
    top = Math.floor(Math.random() * 431);
    top = top - 100;
    if (left > 400) {
      if (top > 115) top = top - (left - 400);
      else top = top + (left - 400);
    } else {
      if (top > 115) top = top + (left - 100);
      else top = top - (left - 100);
    }
  } else {
    top = Math.floor(Math.random() * 471);
    top = top - 150;
  }

  if (action === "board") boardSubmit(left, top);
  else if (action === "letter") letterSubmit(left, top);
};

const boardSubmit = (left, top) => {
  board.value.memberId = memberInfo.memberId;
  board.value.boardX = left;
  board.value.boardY = top;
  board.value.boardImg = Math.floor(Math.random() * 10 + 1);

  boardWrite(
    board.value,
    ({ data }) => {
      console.log(data);
      board.value.boardTitle = "";
      board.value.boardContent = "";
      emit("changeListAction");
      closeModal();
    },
    (err) => {
      console.log("에러");
    }
  );
};

const letterSubmit = (left, top) => {
  letter.value.memberIdFrom = memberInfo.memberId;
  letter.value.memberNoFrom = memberInfo.memberNo;
  letter.value.letterX = left;
  letter.value.letterY = top;
  letter.value.letterImg = Math.floor(Math.random() * 10 + 1);

  letterWrite(
    letter.value,
    ({ data }) => {
      console.log(data);
      letter.value.letterTitle = "";
      letter.value.letterContent = "";
      closeModal();
    },
    (err) => {
      console.log("에러");
    }
  );
};

/** 사운드 */

const buttonSound = new Howl({
  src: [btnClickSound],
});

const playButtonSound = () => {
  try {
    buttonSound.play();
  } catch (error) {
    console.error("Error in playButtonSound function:", error);
  }
};

const handleWriteClick = () => {
  playButtonSound();
};
/** 사운드 끝 */

function selectFile(element) {
  const file = element.files[0];
  const filename = element.closest(".file_input").firstElementChild;

  // 1. 파일 선택 창에서 취소 버튼이 클릭된 경우
  if (!file) {
    filename.value = "";
    return false;
  }

  // 2. 파일 크기가 10MB를 초과하는 경우
  const fileSize = Math.floor(file.size / 1024 / 1024);
  if (fileSize > 10) {
    alert("10MB 이하의 파일로 업로드해 주세요.");
    filename.value = "";
    element.value = "";
    return false;
  }

  // 3. 파일명 지정
  filename.value = file.name;
}

// 파일 추가
function addFile() {
  const fileDiv = document.createElement("div");
  fileDiv.innerHTML = `
    <div class="file_input">
        <input type="text" readonly />
        <label> 첨부파일
            <input type="file" name="files" @click="selectFile(this);" />
        </label>
    </div>
    <button type="button" @click="removeFile(this);"><span>삭제</span></button>
`;
  document.querySelector(".file_list").appendChild(fileDiv);
}

// 파일 삭제
function removeFile(element) {
  const fileAddBtn = element.nextElementSibling;
  if (fileAddBtn) {
    const inputs = element.previousElementSibling.querySelectorAll("input");
    inputs.forEach((input) => (input.value = ""));
    return false;
  }
  element.parentElement.remove();
}
</script>

<template>
  <div id="modal" class="modal-overlay">
    <div class="modal-window" @click.stop>
      <div class="close-area" @click="closeModal">X</div>
      <div class="title"></div>

      <div class="content"></div>
      <label class="radio_btn" for="boardRadio">
        <input
          id="boardRadio"
          type="radio"
          v-model="checkAction"
          name="action"
          value="board"
          checked
        />
        게시글
      </label>

      <label class="radio_btn" for="letterRadio">
        <input
          id="letterRadio"
          type="radio"
          v-model="checkAction"
          name="action"
          value="letter"
        />
        편지
      </label>

      <template v-if="checkAction === 'board'">
        <div class="board_input">
          <form @submit.prevent="formSubmit('board')">
            <label class="board_title">제목</label>
            <input id="title" v-model="board.boardTitle" type="text" required />
            <textarea
              id="content"
              v-model="board.boardContent"
              cols="25"
              rows="10"
              type="text"
              required
            />
            <!-- <label> 첨부파일
              <input type="file" name="files" @click="selectFile(this);" />
            </label>
            <button type="button" @click="removeFile(this);" ><span>삭제</span></button>
            <button type="button" @click="addFile();" ><span>파일 추가</span></button> -->
            <span
              ><button class="board_write_btn" @click="handleWriteClick()">
                글쓰기
              </button></span
            >
          </form>
        </div>
      </template>

      <template v-if="checkAction === 'letter'">
        <div class="board_input">
          <form @submit.prevent="formSubmit('letter')">
            <p>
              <label>To</label
              ><input
                id="title"
                v-model="letter.memberIdTo"
                type="text"
                required
                placeholder=" 보낼 사람의 ID를 입력해주세요."
              />
            </p>
            <label>제목</label>
            <input
              id="title"
              v-model="letter.letterTitle"
              type="text"
              required
            />
            <textarea
              id="content"
              v-model="letter.letterContent"
              cols="25"
              rows="10"
              type="text"
              required
            />
            <!-- <label> 첨부파일
              <input type="file" name="files" @click="selectFile(this);" />
            </label>
            <button type="button" @click="removeFile(this);" ><span>삭제</span></button>
            <button type="button" @click="addFile();" ><span>파일 추가</span></button> -->
            <span
              ><button class="letter_write_btn" @click="handleWriteClick()">
                글쓰기
              </button></span
            >
          </form>
        </div>
      </template>
    </div>
  </div>
</template>

<style scoped>
* {
  font-family: Bazzi;
}

/** 폰트  */
@font-face {
  font-family: "Bazzi";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/Bazzi.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

/** 라디오 버튼 (동그라미) */
.radio_btn {
  color: white;
  padding: 10px;
  cursor: pointer;
}

#title::placeholder {
  color: rgba(255, 255, 255, 0.404);
}

/** 게시글 버튼 */
.letter_write_btn {
  padding: 1%;
  border: none;
  border-radius: 10%;
  background-color: rgba(255, 255, 255, 0.966);
  color: rgba(209, 84, 0, 0.966);
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}

.letter_write_btn:hover,
.board_write_btn:hover {
  background-color: rgba(209, 84, 0, 0.966);
  color: rgba(255, 255, 255, 0.966);
}

.board_write_btn {
  padding: 1%;
  border: none;
  border-radius: 10%;
  background-color: rgba(255, 255, 255, 0.966);
  color: rgba(209, 84, 0, 0.966);
  font-size: 16px;
  cursor: pointer;
  margin-top: 40px;
}

.board_write_btn:hover {
  background-color: rgba(209, 84, 0, 0.966);
  color: rgba(255, 255, 255, 0.966);
}

/** 게시글 입력 */
.board_input {
  margin-top: 40px;
}

.board_input #title {
  font-size: 15px;
  color: #ffffff;
  width: 300px;
  border: none;
  border-bottom: solid #aaaaaa 1px;
  padding-bottom: 10px;
  padding-left: 10px;
  position: relative;
  background: none;
  z-index: 5;
}

.board_input input::placeholder {
  color: #aaaaaa;
}
.board_input input:focus {
  outline: none;
}

.board_input label {
  /* position: absolute; */
  color: rgb(107, 107, 107);
  font-size: 20px;
  bottom: 8px;
  transition: all 0.2s;
}

.board_input input:focus ~ label,
.board_input input:valid ~ label {
  font-size: 16px;
  bottom: 40px;
  color: #666;
  font-weight: bold;
}

.board_input input:focus ~ span,
.board_input input:valid ~ span {
  width: 100%;
}

/** 내용부분 */
#content {
  display: block;
  position: relative;
  margin: auto;
  margin-top: 20px;
  background: none;
  z-index: 5;
  font-size: 15px;
  color: #ffffff;
  width: 350px;
  max-width: 100%; /* 최대 가로 너비 설정 */
  height: 240px;
  border-color: #aaaaaa;
  border-radius: 10%;
  border-style: ridge;
  text-align: left;
  overflow: auto; /* 내용이 넘칠 경우 스크롤 생성 */
  word-wrap: break-word; /* 단어가 가로로 길어질 때 자동으로 줄 바꿈 */
  padding: 10px; /* 내용 입력 영역에 패딩 추가 */
  vertical-align: top; /* 텍스트가 상단에서부터 시작되도록 설정 */
}

.board_input #content {
}

.board_input input {
  font-size: 15px;
  color: #ffffff;
  width: 100%; /* 입력 필드를 가로 전체로 확장 */
  border: none;
  border-bottom: solid #aaaaaa 1px;
  padding-bottom: 10px;
  padding-left: 10px;
  position: relative;
  background: none;
  z-index: 5;
  margin-bottom: 10px; /* 입력 필드 간격을 조절하기 위해 마진 추가 */
}

/** 모달 디자인 */
.modal-window {
  background-image: url("@/assets/board/board_leaf_10.png");
  background-size: cover;
  background-position: center;
  text-align: center;
  vertical-align: middle;
  width: 600px;
  height: 600px;
  z-index: 999;
}

.modal-window input {
  margin-top: 20px;
}

#modal .modal-window {
  overflow-y: auto; /* 내용이 창을 벗어날 경우 스크롤 추가 */
  max-height: 80vh; /* 모달창의 최대 높이를 지정하여 화면을 넘어가지 않도록 함 */
}

#modal.modal-overlay {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(1.5px);
  -webkit-backdrop-filter: blur(1.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  /** 모달창 부드럽게 보이기 */
  opacity: 0;
  pointer-events: none;
  transition: opacity 0.5s ease, transform 0.5s ease;
  z-index: 999; /* 적절한 값으로 조절 */
}

#modal.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}
/* 
#modal .modal-window {
  background: rgba(0, 0, 0, 0.87);
  box-shadow: 0 8px 32px 0 rgba(171, 175, 235, 0.37);
  backdrop-filter: blur(13.5px);
  -webkit-backdrop-filter: blur(13.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  width: 900px;
  height: 700px;
  position: relative;
  padding: 10px;
} */
#modal .title {
  /* display: inline; */
  padding-left: 250px;
  text-shadow: 1px 1px 2px gray;
  color: white;
}
#modal .title h2 {
  padding-left: 55px;
}
#modal .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px gray;
  color: white;
}

/** 모달창 예쁘게 */
#modal .content {
  margin-top: 20px;
  align-content: center;
  /* padding: 0px 10px; */
  text-shadow: 1px 1px 2px rgb(68, 68, 68);
  font-size: 20px;
  color: rgb(255, 255, 255);
  display: flex; /* Flexbox를 사용하여 이미지와 정보를 나란히 표시 !!!!!! (여기)*/
  /* justify-content: space-between; */
}
/** 모달창(디테일) 테스트 끝 */

/** 폰트 */
@font-face {
  font-family: "Bazzi";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/Bazzi.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: "Bazzi";
}
</style>
