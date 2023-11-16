<script setup>
//왜 안돼.. 따로 관리하고싶단말야
import { ref } from "vue";

const isModalOpen = ref(false);

const showModal = () => {
  isModalOpen.value = true;
  // 모달이 나타날 때 show 클래스 추가
  const modal = document.querySelector("#modal.modal-overlay");
  modal.classList.add("show");
};

const closeModal = () => {
  isModalOpen.value = false;
  // 모달을 닫을 때 show 클래스 제거
  const modal = document.querySelector("#modal.modal-overlay");
  modal.classList.remove("show");
};

/** 모달창(디테일) 테스트 끝 */
</script>

<template>
  <div id="modal" class="modal-overlay">
    <div class="modal-window">
      <div class="title">
        <h2>Title 또는 이미지</h2>
      </div>
      <div class="close-area" @click="closeModal">X</div>
      <div class="content">
        <p>사진 또는 Title</p>
        <p>설명</p>
        <p>가나다라마바사 아자차카타파하</p>
        <p>찜기능 있다면 찜 사진</p>
      </div>
    </div>
    <!-- <button @click="closeModal">닫기</button> -->
  </div>
</template>

<style scoped>
/** 모달창(디테일)  테스트 중 */
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
}

#modal.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}

#modal .modal-window {
  background: rgba(69, 139, 197, 0.3);
  box-shadow: 0 8px 32px 0 rgba(171, 175, 235, 0.37);
  backdrop-filter: blur(13.5px);
  -webkit-backdrop-filter: blur(13.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  width: 700px;
  height: 500px;
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

#modal .content {
  margin-top: 20px;
  padding: 0px 10px;
  text-shadow: 1px 1px 2px gray;
  color: white;
}
/** 모달창(디테일) 테스트 끝 */
</style>
