<script setup>
//왜 안돼.. 따로 관리하고싶단말야
import { ref } from "vue";
import { likeInsert, likeList, likeDelete } from '@/api/member';
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);
const props = defineProps({
  attractionDetail: Object,
  attractionOverview: Object,
  attractionDetailIntro: Object,
  likeLength: Number,
  likeNo: Number,
});
const emit = defineEmits(["likeChange"]);
const likeAdd = (attractionDetail) => {
  likeInsert(
    {
      "memberId": memberInfo.value.memberId,
      "contentId": (attractionDetail.contentTypeId === 15) ? 0 : attractionDetail.contentId,
      "contentFestivalId": (attractionDetail.contentTypeId === 15) ? attractionDetail.contentId : 0,
      "contentTitle": attractionDetail.title
    },
    (response) => {
      emit("likeChange");
    },
    (err) => { 
      console.log(err);
    });
}
const likeRemove = () => {
  likeDelete(
    {
      "no": props.likeNo,
    },
    (response) => {
      emit("likeChange");
    },
    (err) => { 
      console.log(err);
    });
}

const isModalOpen = ref(false);

const closeModal = () => {
  if (props.likeLength === 0) emit("likeChange", true);
  isModalOpen.value = false;
  // 모달을 닫을 때 show 클래스 제거
  const modal = document.querySelector("#modal.modal-overlay");
  modal.classList.remove("show");
};

/** 모달창(디테일) 테스트 끝 */
</script>

<template>
  <div id="modal" class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="title">
        <h2></h2>
      </div>
      <div class="close-area" @click="closeModal">X</div>
      <div class="content">
        <div class="image-container">
          <img :src="attractionDetail.firstImage" alt="Attraction Image" />
        </div>
        <div class="info-container">
          <p v-show="attractionDetail.title">
            <span v-if="attractionDetail.contentTypeId === 39">가게명</span>
            <span v-else-if="attractionDetail.contentTypeId === 15"
              >축제명</span
            >
            <span v-else>관광지명</span>
            : {{ attractionDetail.title }}
          </p>
          <p v-show="attractionDetail.addr1">
            주소 : {{ attractionDetail.addr1 }}
          </p>
          <p v-show="attractionDetail.tel">
            전화번호 : {{ attractionDetail.tel }}
          </p>
          <!-- <img :src="attractionDetail.firstImage" alt=""> -->

          <p v-if="attractionDetail.contentTypeId === 15">
            축제기간 : {{ attractionDetail.eventStartDate }} ~
            {{ attractionDetail.eventEndDate }}
          </p>

          <template v-if="attractionDetail.contentTypeId === 12">
            <p v-show="attractionDetailIntro.chkbabycarriage">
              유모차대여정보 : {{ attractionDetailIntro.chkbabycarriage }}
            </p>
            <p v-show="attractionDetailIntro.chkpet">
              애완동물동반가능정보 : {{ attractionDetailIntro.chkpet }}
            </p>
            <p v-show="attractionDetailIntro.expguide">
              체험안내 : {{ attractionDetailIntro.expguide }}
            </p>
            <p v-show="attractionDetailIntro.parking">
              주차시설 : {{ attractionDetailIntro.parking }}
            </p>
            <p v-show="attractionDetailIntro.restdate">
              쉬는날 : {{ attractionDetailIntro.restdate }}
            </p>
            <p v-show="attractionDetailIntro.usetime">
              이용시간 : {{ attractionDetailIntro.usetime }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 14">
            <p v-show="attractionDetailIntro.chkbabycarriageculture">
              유모차대여정보 :
              {{ attractionDetailIntro.chkbabycarriageculture }}
            </p>
            <p v-show="attractionDetailIntro.chkpetculture">
              애완동물동반가능정보 : {{ attractionDetailIntro.chkpetculture }}
            </p>
            <p v-show="attractionDetailIntro.infocenterculture">
              고객센터 : {{ attractionDetailIntro.infocenterculture }}
            </p>
            <p v-show="attractionDetailIntro.parkingculture">
              주차시설 : {{ attractionDetailIntro.parkingculture }}
            </p>
            <p v-show="attractionDetailIntro.restdateculture">
              쉬는날 : {{ attractionDetailIntro.restdateculture }}
            </p>
            <p v-show="attractionDetailIntro.usefee">
              이용요금 : {{ attractionDetailIntro.usefee }}
            </p>
            <p v-show="attractionDetailIntro.usetimeculture">
              이용시간 : {{ attractionDetailIntro.usetimeculture }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 25">
            <p v-show="attractionDetailIntro.distance">
              코스 총 거리 : {{ attractionDetailIntro.distance }}
            </p>
            <p v-show="attractionDetailIntro.infocentertourcourse">
              고객센터 : {{ attractionDetailIntro.infocentertourcourse }}
            </p>
            <p v-show="attractionDetailIntro.taketime">
              코스 총 소요시간 : {{ attractionDetailIntro.taketime }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 28">
            <p v-show="attractionDetailIntro.chkbabycarriageleports">
              유모차대여정보 :
              {{ attractionDetailIntro.chkbabycarriageleports }}
            </p>
            <p v-show="attractionDetailIntro.chkpetleports">
              애완동물동반가능정보 : {{ attractionDetailIntro.chkpetleports }}
            </p>
            <p v-show="attractionDetailIntro.infocenterleports">
              고객센터 : {{ attractionDetailIntro.infocenterleports }}
            </p>
            <p v-show="attractionDetailIntro.parkingleports">
              주차시설 : {{ attractionDetailIntro.parkingleports }}
            </p>
            <p v-show="attractionDetailIntro.restdateleports">
              쉬는날 : {{ attractionDetailIntro.restdateleports }}
            </p>
            <p v-show="attractionDetailIntro.usefeeleports">
              입장료 : {{ attractionDetailIntro.usefeeleports }}
            </p>
            <p v-show="attractionDetailIntro.usetimeleports">
              이용시간 : {{ attractionDetailIntro.usetimeleports }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 32">
            <p v-show="attractionDetailIntro.barbecue">
              바비큐장여부 : {{ attractionDetailIntro.barbecue }}
            </p>
            <p v-show="attractionDetailIntro.bicycle">
              자전거대여여부 : {{ attractionDetailIntro.bicycle }}
            </p>
            <p v-show="attractionDetailIntro.foodplace">
              식음료장 : {{ attractionDetailIntro.foodplace }}
            </p>
            <p v-show="attractionDetailIntro.infocenterlodging">
              고객센터 : {{ attractionDetailIntro.infocenterlodging }}
            </p>
            <p v-show="attractionDetailIntro.parkinglodging">
              주차시설 : {{ attractionDetailIntro.parkinglodging }}
            </p>
            <p v-show="attractionDetailIntro.subfacility">
              편의시설 : {{ attractionDetailIntro.subfacility }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 38">
            <p v-show="attractionDetailIntro.chkbabycarriageshopping">
              유모차대여정보 :
              {{ attractionDetailIntro.chkbabycarriageshopping }}
            </p>
            <p v-show="attractionDetailIntro.chkpetshopping">
              애완동물동반가능정보 : {{ attractionDetailIntro.chkpetshopping }}
            </p>
            <p v-show="attractionDetailIntro.fairday">
              개장일 : {{ attractionDetailIntro.fairday }}
            </p>
            <p v-show="attractionDetailIntro.infocentershopping">
              고객센터 : {{ attractionDetailIntro.infocentershopping }}
            </p>
            <p v-show="attractionDetailIntro.opentime">
              영업시간 : {{ attractionDetailIntro.opentime }}
            </p>
            <p v-show="attractionDetailIntro.parkingshopping">
              주차시설 : {{ attractionDetailIntro.parkingshopping }}
            </p>
            <p v-show="attractionDetailIntro.restdateshopping">
              쉬는날 : {{ attractionDetailIntro.restdateshopping }}
            </p>
            <p v-show="attractionDetailIntro.saleitem">
              판매품목 : {{ attractionDetailIntro.saleitem }}
            </p>
          </template>
          <template v-if="attractionDetail.contentTypeId === 39">
            <p v-show="attractionDetailIntro.firstmenu">
              대표메뉴 : {{ attractionDetailIntro.firstmenu }}
            </p>
            <p v-show="attractionDetailIntro.opentimefood">
              영업시간 : {{ attractionDetailIntro.opentimefood }}
            </p>
            <p v-show="attractionDetailIntro.restdatefood">
              휴무일 : {{ attractionDetailIntro.restdatefood }}
            </p>
            <p v-show="attractionDetailIntro.parkingfood">
              주차여부 : {{ attractionDetailIntro.parkingfood }}
            </p>
          </template>

          <p v-show="attractionOverview.overview">
            상세설명 :
            {{ attractionOverview.overview }}
          </p>

          <div id="likeBtn" class="button-container" v-show="isLogin">
            <button class="like-button" v-show="likeLength === 0" @click="likeAdd(attractionDetail)" >
              <i class="fas fa-heart">❤️</i>찜추가
            </button>
            <button class="like-button" v-show="likeLength !== 0" @click="likeRemove()">
              <i class="fas fa-heart-broken">💔</i>찜삭제
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- <button @click="closeModal">닫기</button> -->
  </div>
</template>

<style scoped>
/** 모달창(디테일)  테스트 중 */
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
  z-index: 10;
}

#modal.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}

#modal .modal-window {
  background: rgba(9, 1, 26, 0.493);
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
  padding: 0px 10px;
  text-shadow: 1px 1px 2px rgb(68, 68, 68);
  font-size: 20px;
  color: rgb(255, 255, 255);
  display: flex; /* Flexbox를 사용하여 이미지와 정보를 나란히 표시 */
   justify-content: space-between;
}


#modal .image-container {
  flex: 1; /* Flex 아이템의 크기 비율 조절 */
  margin-right: 20px; /* 이미지와 정보 사이 간격 조절 */
}

#modal .image-container img {
  max-width: 100%; /* 이미지가 부모 요소를 넘어가지 않도록 최대 너비 설정 */
  border-radius: 10px; /* 이미지에 둥근 테두리 추가 */
}

#modal .info-container {
  flex: 2; /* Flex 아이템의 크기 비율 조절 */
}

/** 찜 버튼 */

#modal .button-container {
  margin-top: auto; /* 나머지 공간을 최대한 활용하기 위해 */
}

#modal .like-button {
  padding: 10px;
  margin-right: 10px;
  background-color: #0d1e29;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

#modal .like-button i {
  margin-right: 5px;
}

#modal .like-button:hover {
  background-color: #da7979;
}
/** 모달창(디테일) 테스트 끝 */
</style>
