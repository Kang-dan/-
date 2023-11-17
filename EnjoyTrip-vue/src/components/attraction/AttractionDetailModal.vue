<script setup>
//왜 안돼.. 따로 관리하고싶단말야
import { ref } from "vue";
defineProps({ attractionDetail: Object, attractionOverview: Object, attractionDetailIntro: Object });


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
        <h2></h2>
      </div>
      <div class="close-area" @click="closeModal">X</div>
      <div class="content">        
        <p v-show="attractionDetail.title">
          <span v-if="attractionDetail.contentTypeId === 39">가게명</span>
          <span v-else-if="attractionDetail.contentTypeId === 15">축제명</span>
          <span v-else>관광지명</span>
           : {{attractionDetail.title}}
        </p>
        <p v-show="attractionDetail.addr1">주소 : {{attractionDetail.addr1}}</p>
        <p v-show="attractionDetail.tel">전화번호 : {{attractionDetail.tel}}</p>
        <!-- <img :src="attractionDetail.firstImage" alt=""> -->
        <p v-show="attractionOverview.overview">디테일설명 : {{ attractionOverview.overview }}</p>
        <p v-if="attractionDetail.contentTypeId === 15">축제기간 : {{ attractionDetail.eventStartDate }} ~ {{ attractionDetail.eventEndDate }}</p>
        <template v-if="attractionDetail.contentTypeId === 12">
          <p v-show="attractionDetailIntro.chkbabycarriage">유모차대여정보 : {{ attractionDetailIntro.chkbabycarriage }}</p>
          <p v-show="attractionDetailIntro.chkpet">애완동물동반가능정보 : {{ attractionDetailIntro.chkpet }}</p>
          <p v-show="attractionDetailIntro.expguide">체험안내 : {{ attractionDetailIntro.expguide }}</p>
          <p v-show="attractionDetailIntro.parking">주차시설 : {{ attractionDetailIntro.parking }}</p>
          <p v-show="attractionDetailIntro.restdate">쉬는날 : {{ attractionDetailIntro.restdate }}</p>
          <p v-show="attractionDetailIntro.usetime">이용시간 : {{ attractionDetailIntro.usetime }}</p>
        </template>
        <template v-if="attractionDetail.contentTypeId === 14">
          <p v-show="attractionDetailIntro.chkbabycarriageculture">유모차대여정보 : {{ attractionDetailIntro.chkbabycarriageculture }}</p>
          <p v-show="attractionDetailIntro.chkpetculture">애완동물동반가능정보 : {{ attractionDetailIntro.chkpetculture }}</p>
          <p v-show="attractionDetailIntro.infocenterculture">고객센터 : {{ attractionDetailIntro.infocenterculture }}</p>
          <p v-show="attractionDetailIntro.parkingculture">주차시설 : {{ attractionDetailIntro.parkingculture }}</p>
          <p v-show="attractionDetailIntro.restdateculture">쉬는날 : {{ attractionDetailIntro.restdateculture }}</p>
          <p v-show="attractionDetailIntro.usefee">이용요금 : {{ attractionDetailIntro.usefee }}</p>
          <p v-show="attractionDetailIntro.usetimeculture">이용시간 : {{ attractionDetailIntro.usetimeculture }}</p>
        </template>
        <template v-if="attractionDetail.contentTypeId === 25">
          <p v-show="attractionDetailIntro.distance">코스 총 거리 : {{ attractionDetailIntro.distance }}</p>
          <p v-show="attractionDetailIntro.infocentertourcourse">고객센터 : {{ attractionDetailIntro.infocentertourcourse }}</p>
          <p v-show="attractionDetailIntro.taketime">코스 총 소요시간 : {{ attractionDetailIntro.taketime }}</p>          
        </template>
        <template v-if="attractionDetail.contentTypeId === 28">
          <p v-show="attractionDetailIntro.chkbabycarriageleports">유모차대여정보 : {{ attractionDetailIntro.chkbabycarriageleports }}</p>
          <p v-show="attractionDetailIntro.chkpetleports">애완동물동반가능정보 : {{ attractionDetailIntro.chkpetleports }}</p>
          <p v-show="attractionDetailIntro.infocenterleports">고객센터 : {{ attractionDetailIntro.infocenterleports }}</p>
          <p v-show="attractionDetailIntro.parkingleports">주차시설 : {{ attractionDetailIntro.parkingleports }}</p>
          <p v-show="attractionDetailIntro.restdateleports">쉬는날 : {{ attractionDetailIntro.restdateleports }}</p>
          <p v-show="attractionDetailIntro.usefeeleports">입장료 : {{ attractionDetailIntro.usefeeleports }}</p>
          <p v-show="attractionDetailIntro.usetimeleports">이용시간 : {{ attractionDetailIntro.usetimeleports }}</p>
        </template>
        <template v-if="attractionDetail.contentTypeId === 32">
          <p v-show="attractionDetailIntro.barbecue">바비큐장여부 : {{ attractionDetailIntro.barbecue }}</p>
          <p v-show="attractionDetailIntro.bicycle">자전거대여여부 : {{ attractionDetailIntro.bicycle }}</p>
          <p v-show="attractionDetailIntro.foodplace">식음료장 : {{ attractionDetailIntro.foodplace }}</p>
          <p v-show="attractionDetailIntro.infocenterlodging">고객센터 : {{ attractionDetailIntro.infocenterlodging }}</p>
          <p v-show="attractionDetailIntro.parkinglodging">주차시설 : {{ attractionDetailIntro.parkinglodging }}</p>
          <p v-show="attractionDetailIntro.subfacility">편의시설 : {{ attractionDetailIntro.subfacility }}</p>
        </template>
        <template v-if="attractionDetail.contentTypeId === 38">
          <p v-show="attractionDetailIntro.chkbabycarriageshopping">유모차대여정보 : {{ attractionDetailIntro.chkbabycarriageshopping }}</p>
          <p v-show="attractionDetailIntro.chkpetshopping">애완동물동반가능정보 : {{ attractionDetailIntro.chkpetshopping }}</p>
          <p v-show="attractionDetailIntro.fairday">개장일 : {{ attractionDetailIntro.fairday }}</p>
          <p v-show="attractionDetailIntro.infocentershopping">고객센터 : {{ attractionDetailIntro.infocentershopping }}</p>
          <p v-show="attractionDetailIntro.opentime">영업시간 : {{ attractionDetailIntro.opentime }}</p>
          <p v-show="attractionDetailIntro.parkingshopping">주차시설 : {{ attractionDetailIntro.parkingshopping }}</p>
          <p v-show="attractionDetailIntro.restdateshopping">쉬는날 : {{ attractionDetailIntro.restdateshopping }}</p>
          <p v-show="attractionDetailIntro.saleitem">판매품목 : {{ attractionDetailIntro.saleitem }}</p>          
        </template>
        <template v-if="attractionDetail.contentTypeId === 39">
          <p v-show="attractionDetailIntro.firstmenu">대표메뉴 : {{ attractionDetailIntro.firstmenu }}</p>
          <p v-show="attractionDetailIntro.opentimefood">영업시간 : {{ attractionDetailIntro.opentimefood }}</p>
          <p v-show="attractionDetailIntro.restdatefood">휴무일 : {{ attractionDetailIntro.restdatefood }}</p>
          <p v-show="attractionDetailIntro.parkingfood">주차여부 : {{ attractionDetailIntro.parkingfood }}</p>
        </template>
        <div id="likeBtn">
          <button>찜추가</button>
          <button>찜삭제</button>
        </div>
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
