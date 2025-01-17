<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { boardWrite, boardDelete } from "@/api/board";
import { letterDelete } from "@/api/letter";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { memberInfo, getMemberInfo, memberLogout } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const isMyListModalOpen = ref(false);
const checkMyListAction = ref("board");
const props = defineProps({
  boards: Object,
  letters: Object,
});
const emit = defineEmits(["getBoardList", "getLetterList"]);

const closeModal = () => {
  isMyListModalOpen.value = false;
  // 모달을 닫을 때 show 클래스 제거

  const modalMyList = document.querySelector("#modalMyList.modal-overlay");
  if (modalMyList) {
    modalMyList.classList.remove("show");
  }
};
/** 모달창(디테일) 테스트 끝 */

const deleteBoard = (no) => {
  if (confirm("삭제하시겠습니까?")) {
    boardDelete(
      no,
      () => {
        emit("getBoardList");
      },
      () => {}
    );
  }
};

const deleteLetter = (no) => {
  if (confirm("삭제하시겠습니까?")) {
    letterDelete(
      no,
      () => {
        emit("getLetterList");
      },
      (err) => {
        console.log(err);
      }
    );
  }
};
</script>

<template>
  <div id="modalMyList" class="modal-overlay">
    <div class="modal-window" @click.stop>
      <div class="close-area" @click="closeModal">X</div>
      <div class="title"></div>

      <div class="content"></div>
      <label class="radio_btn"
        ><input
          type="radio"
          v-model="checkMyListAction"
          name="actionMyList"
          value="board"
          checked
        />게시글</label
      >
      <label class="radio_btn"
        ><input
          type="radio"
          v-model="checkMyListAction"
          name="actionMyList"
          value="letter"
        />편지</label
      >

      <div class="board_input">
        <p>마이리스트</p>

        <template v-if="checkMyListAction === 'board'">
          <div class="tableDiv">
            <table class="myListTable">
              <thead>
                <tr>
                  <th>제목</th>
                  <th>작성자</th>
                  <th>작성일</th>
                  <th>조회수</th>
                  <th>좋아요</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <template v-if="memberInfo">
                  <template v-for="board in boards">
                    <template v-if="board.memberId === memberInfo.memberId">
                      <tr>
                        <td>{{ board.boardTitle }}</td>
                        <td>{{ board.memberId }}</td>
                        <td>{{ board.boardRegisterTime }}</td>
                        <td>{{ board.boardHit }}</td>
                        <td>{{ board.boardLove }}</td>
                        <td>
                          <button
                            class="delete_btn"
                            @click="deleteBoard(board.boardNo)"
                            v-if="board.boardLove === 0"
                          >
                            삭제
                          </button>
                        </td>
                      </tr>
                    </template>
                  </template>
                </template>
              </tbody>
            </table>
          </div>
        </template>

        <template v-if="checkMyListAction === 'letter'">
          <div class="tableDiv">
            <table class="myListTable">
              <thead>
                <tr>
                  <th>제목</th>
                  <th>보낸 사람</th>
                  <th>받은 사람</th>
                  <th>편지 확인 여부</th>
                  <th>보낸 날짜</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <template v-if="memberInfo">
                  <template v-for="letter in letters">
                    <tr>
                      <td>{{ letter.letterTitle }}</td>
                      <td>{{ letter.memberIdFrom }}</td>
                      <td>{{ letter.memberIdTo }}</td>
                      <td>{{ letter.letterHit > 0 ? "확인함" : "확인안함" }}</td>
                      <td>{{ letter.letterRegisterTime }}</td>
                      <td>
                        <button
                          class="delete_btn"
                          @click="deleteLetter(letter.letterNo)"
                          v-if="
                            letter.memberIdTo === memberInfo.memberId ||
                            letter.letterHit === 0
                          "
                        >
                          삭제
                        </button>
                      </td>
                    </tr>
                  </template>
                </template>
              </tbody>
            </table>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 스크롤바 숨기기 */
.tableDiv::-webkit-scrollbar {
  display: none;
}
.tableDiv {
  margin: 0 auto;
  width: 490px;
  height: 300px;
  overflow: auto;
}
.delete_btn {
  border: none;
  border-radius: 10%;
  background-color: white;
  color: rgb(134, 0, 0);
  cursor: pointer;
}

.delete_btn:hover {
  background-color: rgb(134, 0, 0);
  color: white;
}

.radio_btn {
  color: white;
  padding: 10px;
  cursor: pointer;
}

.myListTable {
  color: white;
  margin: 0 auto;
}

.myListTable th,
.myListTable td {
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 10%;
  padding: 8px; /* Adjust padding for cell content */
  color: rgba(255, 255, 255, 0.8); /* Light gray text color */
}

/* Add light vertical lines between table cells */
.myListTable th:not(:last-child),
.myListTable td:not(:last-child) {
  border-right: none;
}

/* Add light horizontal lines between table rows */
.myListTable tr:not(:last-child) td {
  border-bottom: none;
}

/** 게시글 입력 */
.board_input {
  margin-top: 40px;
  color: white;
  font-size: 17px;
}
.board_input p {
  font-size: 25px;
}

.board_input #title {
  font-size: 15px;
  color: #222222;
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
  color: #222222;
  width: 350px;
  max-width: 100%; /* 최대 가로 너비 설정 */
  height: 300px;
  border-color: #aaaaaa;
  border-radius: 2%;
  border-style: ridge;
  text-align: left;
  overflow: auto; /* 내용이 넘칠 경우 스크롤 생성 */
  word-wrap: break-word; /* 단어가 가로로 길어질 때 자동으로 줄 바꿈 */
  padding: 10px; /* 내용 입력 영역에 패딩 추가 */
  vertical-align: top; /* 텍스트가 상단에서부터 시작되도록 설정 */
}

.board_input input {
  font-size: 15px;
  color: #222222;
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
  margin-top: 17px;
}

#modalMyList .modal-window {
  overflow-y: auto; /* 내용이 창을 벗어날 경우 스크롤 추가 */
  max-height: 80vh; /* 모달창의 최대 높이를 지정하여 화면을 넘어가지 않도록 함 */
}

#modalMyList.modal-overlay {
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

#modalMyList.modal-overlay.show {
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
#modalMyList .title {
  /* display: inline; */
  padding-left: 250px;
  text-shadow: 1px 1px 2px gray;
  color: white;
}
#modalMyList .title h2 {
  padding-left: 55px;
}
#modalMyList .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px gray;
  color: white;
}

/** 모달창 예쁘게 */
#modalMyList .content {
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
