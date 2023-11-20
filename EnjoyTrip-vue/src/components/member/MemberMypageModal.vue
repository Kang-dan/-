<script setup>
import { ref } from "vue";
import { likeInsert, likeList, likeDelete } from "@/api/member";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const isModalOpen = ref(false);

const closeModal = () => {
  isModalOpen.value = false;
  // 모달을 닫을 때 show 클래스 제거
  const modal = document.querySelector("#modal.modal-overlay");
  if (modal) {
    modal.classList.remove("show");
  }
};



/** 모달창(디테일) 테스트 끝 */
</script>

<template>
  <div id="modal" class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="title">
        <h2>할 수 이 따</h2>
      </div>

      <div class="close-area" @click="closeModal">X</div>
      <div class="content">
        <img
          class="mypage_info_photo_img"
          src="@/assets/mypage/mypage_info_photo.png"
          alt=""
        />
        <img
          class="mypage_winter_children_photo"
          src="@/assets/mypage/mypage_winter_children_photo.gif"
          alt=""
        />
        <div class="mypage_member_info">
          <h3>아이디</h3>
          <h3>비밀번호</h3>
          <h3>이름</h3>
          <h3>이메일</h3>
          <h3>나이</h3>
          <h3>우리 회원이 된 날</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/** 마이페이지 (사진) */
.mypage_member_info {
  margin: auto 20px;
}
.mypage_info_photo_img {
  padding-left: 30px;
}

.mypage_winter_children_photo {
  position: absolute;
  /* transform: translate(-410px, 100px); */
  transform: translate(69px, 100px);
  width: 365px;
  /* display: none; */
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
  transition: opacity 1s ease, transform 1s ease;
  z-index: 999; /* 적절한 값으로 조절 */
}

#modal.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}

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
}
#modal .title {
  padding-left: 10px;
  display: inline;
  text-shadow: 1px 1px 2px gray;
  color: white;
}
#modal .title h2 {
  display: inline;
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
