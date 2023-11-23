<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { boardWrite } from "@/api/board";
import { letterDelete } from "@/api/letter";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { memberInfo, getMemberInfo, memberLogout } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const props = defineProps({
  letterDetail: Object,
});
const emit = defineEmits(["getBoardList", "getLetterList"]);
const isLetterModalOpen = ref(false);
const closeModal = () => {
  isLetterModalOpen.value = false;
  emit("getBoardList");
  emit("getLetterList");
  // 모달을 닫을 때 show 클래스 제거
  const modalLetter = document.querySelector("#modalLetter.modal-overlay");
  if (modalLetter) {
    modalLetter.classList.remove("show");
  }
};
/** 모달창(디테일) 테스트 끝 */

const deleteLetter = () => {
  if (confirm("삭제하시겠습니까?")) {
    letterDelete(
      props.letterDetail.letterNo, () => {closeModal();}, () => {}
    );
  }
}
</script>

<template>
  <div id="modalLetter" class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="close-area" @click="closeModal">X</div>
      <div class="board_input">
        <!-- <p>제목 : {{ props.boardDetail.boardTitle }}</p>
        <p>작성자 : {{ props.boardDetail.memberId }}</p>
        <p>작성일 : {{ props.boardDetail.boardRegisterTime }}</p>
        <p>내용 : {{ props.boardDetail.boardContent }}</p>
        <p>조회수 : {{ props.boardDetail.boardHit }}</p>
        <p>좋아요 : {{ props.boardDetail.boardLove }}</p> -->
        <div class="title">
          <p>
            <i>제목 : </i><i>{{ props.letterDetail.letterTitle }}</i>
          </p>
          <p>
            <i>보낸 사람 : </i> <i>{{ props.letterDetail.memberIdFrom }}</i>
          </p>
          <p>
            <i>받은 사람 : </i> <i>{{ props.letterDetail.memberIdTo }}</i>
          </p>
          <p>
            <i>받은 날짜 : </i> <i>{{ props.letterDetail.letterRegisterTime }}</i>
          </p>
        </div>
        <div class="content">
          <p>{{ props.letterDetail.letterContent }}</p>
        </div>
        <div class="stats">
          <i>읽은 횟수 : {{ props.letterDetail.letterHit }}</i>
          <button class="like" @click="deleteLetter">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/** 글 배치 예쁘게 */
.like {
  border: none;
  border-radius: 10%;
  padding: 5px;
  margin-right: 5px;
  color: #ff7979;
  background: #ffffff;
  font-size: 20px;
  cursor: pointer;
}

.like:hover {
  color: #ffffff;
  background: #ff8989;
}

#modalLetter .title {
  color: rgb(0, 0, 0);
}

#modalLetter .title p {
  text-shadow: 1px 1px 2px rgb(255, 154, 108);
  position: relative;
  /* transform: translateX(0px); */
  margin-left: 160px;
  color: rgb(255, 255, 255);
  font-size: 20px;
  /* 추가적인 스타일링 */
}

#modalLetter .content p {
  margin-top: 20px;
  text-shadow: 1px 1px 2px rgb(224, 193, 92);
  font-size: 22px;
  color: rgb(255, 255, 255);
  display: flex;
  flex-direction: column; /* 세로로 요소들을 배열합니다. */
  align-items: center; /* 가운데 정렬 */
}

#modalLetter .stats {
  justify-content: space-around; /* 좌우 여백 동일하게 분배 */
  /* margin-top: 30px; 간격 조절 */
  margin-top: 30px;
  text-align: center;
}

#modalLetter .stats i {
  justify-content: space-around; /* 좌우 여백 동일하게 분배 */
  /* margin-top: 30px; 간격 조절 */
  color: white;
  font-size: 25px;
  padding: 20px;
}

/** 게시글 입력 */
.board_input {
  margin-top: 70px;
}

/** 내용부분 */
#modalLetter .content {
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
  height: 250px;
  border-color: #ffffff;
  border-radius: 10%;
  border-style: double;
  background-color: rgba(250, 0, 0, 0.1);
  border-color: rgba(255, 205, 205, 0.5);
  text-align: left;
  overflow: auto; /* 내용이 넘칠 경우 스크롤 생성 */
  word-wrap: break-word; /* 단어가 가로로 길어질 때 자동으로 줄 바꿈 */
  padding: 10px; /* 내용 입력 영역에 패딩 추가 */
  vertical-align: top; /* 텍스트가 상단에서부터 시작되도록 설정 */
}

/** 모달 디자인 */
.modal-window {
  background-image: url("@/assets/board/board_leaf_10.png");
  background-size: cover;
  background-position: center;
  /* text-align: center; */
  vertical-align: middle;
  width: 600px;
  height: 600px;
  z-index: 999;
}

#modalLetter .modal-window {
  overflow-y: auto; /* 내용이 창을 벗어날 경우 스크롤 추가 */
  max-height: 80vh; /* 모달창의 최대 높이를 지정하여 화면을 넘어가지 않도록 함 */
}

#modalLetter.modal-overlay {
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

#modalLetter.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}

#modalLetter .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px gray;
  color: white;
}

/** 모달창 예쁘게 */
#modalLetter .content {
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
