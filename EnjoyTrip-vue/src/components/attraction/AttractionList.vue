<script setup>
// defineProps({ attraction: Object });

import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  listAttraction,
  detailAttraction,
  detailIntroAttraction,
} from "@/api/attraction";
import AttractionDetailModal from "./AttractionDetailModal.vue";
import { useMemberStore } from "@/stores/member";
import { likeList } from '@/api/member';
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const router = useRouter();
const route = useRoute();
const scrollPosition = 230; // 원하는 스크롤 위치 (픽셀 단위)

const moveAttractionDetail = () => {
  router.push({ name: "attraction-detail" }); //contentId로
};

//검색 드롭박스
const selectThemeOption = ref([
  { text: "전체", value: "" },
  { text: "관광지명", value: "title" },
  { text: "위치", value: "addr" },
]);

//검색 선택, 페이지 처리
const attractions = ref([{}]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ATTRACTION_LIST_SIZE } = import.meta.env;
// const param = ref({
//   pgno: currentPage.value,
//   spp: VITE_ATTRACTION_LIST_SIZE,
//   key: "", //드롭박스 선택하면
//   word: "", //검색창 입력값
// });

const param = ref({
  key: "all",
  sidoCode: 0,
  selected: "All",
  searchWord: "",
});

onMounted(() => {
  param.value.sidoCode = route.params.sidoCode;
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 0,
    top: 220,
    behavior: "smooth",
  });
  // window.scrollTo(0, scrollPosition);
  getAttractionList();
  list = document.querySelector(".list");  
  listClientWidth = list.clientWidth;
  bindEvents();
});

const loadAttractionByTheme = (theme) => {
  // 테마에 따라 key 설정
  let key;
  switch (theme) {
    case "food":
      key = "food"; // 예시로 11번을 사용하며, 실제 key에 따라 수정 필요
      break;
    case "festival":
      key = "festival"; // 예시로 15번을 사용하며, 실제 key에 따라 수정 필요
      break;
    case "family":
      key = "family"; // 예시로 12번을 사용하며, 실제 key에 따라 수정 필요
      break;
    default:
      key = "all"; // 기본값 설정 혹은 오류 처리
  }

  // 서버에서 정보 가져오기
  param.value.key = key;
  // 테마 바꾸면 검색창 초기화
  param.value.searchWord = "";
  getAttractionList();
};

const getTranslateX = () => {
  return parseInt(getComputedStyle(list).transform.split(/[^\-0-9]+/g)[5]);
};

const setTranslateX = (x) => {
  list.style.transform = `translateX(${x}px)`;
};

const getAttractionList = async () => {  
  //서버에서 사진, 관광지명, 주소 가져오기
  await listAttraction(
    param.value,
    ({ data }) => {
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
  listX = getTranslateX();
  setTranslateX(0);
  listX = 0;
  listScrollWidth = list.scrollWidth;  
};

const changeKey = (val) => {
  param.value.key = val;
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getAttractionList();
};

const likeChange = (check) => {
  getLikeOne();
}

//이미지 가로 스크롤
let list = null;
let listScrollWidth = ref(0);
let listClientWidth = ref(0);
// 이벤트마다 갱신될 값
let startX = ref(0);
let nowX = ref(0);
let endX = ref(0);
let listX = ref(0);

const getClientX = (e) => {
  const isTouches = e.touches ? true : false;
  return isTouches ? e.touches[0].clientX : e.clientX;
};



//이벤트 연결
const bindEvents = () => {
  list.addEventListener("mousedown", onScrollStart);
  list.addEventListener("touchstart", onScrollStart);
  list.addEventListener("click", onClick); // 여기서는 클릭 이벤트 처리를 제거합니다.
};

//스크롤 진행 이벤트 구현
const onScrollStart = (e) => {  
  startX = getClientX(e);
  window.addEventListener("mousemove", onScrollMove);
  window.addEventListener("touchmove", onScrollMove);
  window.addEventListener("mouseup", onScrollEnd);
  window.addEventListener("touchend", onScrollEnd);
};

const btnScrolle = (num) => {
  setTranslateX(getTranslateX() + (850 * num));
  list.style.transition = `all 0.3s ease`;
  listX = getTranslateX() + (850 * num);

  if (listX > 0) {
    setTranslateX(0);
    listX = 0;
  } else if (listX < listClientWidth - listScrollWidth) {
    setTranslateX(listClientWidth - listScrollWidth);
    listX = listClientWidth - listScrollWidth;
  }
  
  setTimeout(() => {    
    bindEvents();
    list.style.transition = "";
  }, 300);  

}

// 스크롤 종료 이벤트 구현
const onScrollEnd = (e) => {  
  endX = getClientX(e);
  listX = getTranslateX();
  if (listX > 0) {
    setTranslateX(0);
    list.style.transition = `all 0.3s ease`;
    listX = 0;
  } else if (listX < listClientWidth - listScrollWidth) {
    setTranslateX(listClientWidth - listScrollWidth);
    list.style.transition = `all 0.3s ease`;
    listX = listClientWidth - listScrollWidth;
  }

  window.removeEventListener("mousedown", onScrollStart);
  window.removeEventListener("touchstart", onScrollStart);
  window.removeEventListener("mousemove", onScrollMove);
  window.removeEventListener("touchmove", onScrollMove);
  window.removeEventListener("mouseup", onScrollEnd);
  window.removeEventListener("touchend", onScrollEnd);
  window.removeEventListener("click", onClick);

  setTimeout(() => {    
    bindEvents();
    list.style.transition = "";
  }, 300);
};

const onScrollMove = (e) => {
  const nowX = getClientX(e);
  setTranslateX(listX + nowX - startX);
};

//클릭 이벤트 구현
const onClick = (e) => {
  e.preventDefault(); // 화면이 위로 올라가는 기본 동작 막음
  if (startX - endX !== 0) {
  }
};

/** 모달창(디테일) 테스트 중 */
const isModalOpen = ref(false);
const attractionDetail = ref({});
const attractionOverview = ref({});
const attractionDetailIntro = ref({});
const memberLikeInfo = ref({});
const likeLength = ref({});
const likeNo = ref();

const introParam = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  MobileOS: "ETC",
  MobileApp: "AppTest",
  _type: "json",
  contentId: "",
  contentTypeId: "",
});

// 디테일 화면의 상세 설명 가져오기
const getOverview = (detail) => {
  detailAttraction(
    detail.value,
    ({ data }) => {
      attractionOverview.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const getAttractionIntro = () => {
  introParam.value.contentId = attractionDetail.value.contentId;
  introParam.value.contentTypeId = attractionDetail.value.contentTypeId;
  detailIntroAttraction(
    introParam.value,
    ({ data }) => {
      attractionDetailIntro.value = data.response.body.items.item[0];
    },
    (err) => {
      console.log(err);
    }
  );
};

const showModal = (detail) => {
  attractionDetail.value = detail;
  getOverview(attractionDetail);
  // 상세화면이 음식점일 경우 음식점 정보 가져오기
  if (attractionDetail.value.contentTypeId != 15) {
    getAttractionIntro();
  }  
  if (isLogin.value) {
    getLikeOne();
  }
  isModalOpen.value = true;
  // 모달이 나타날 때 show 클래스 추가
  const modal = document.querySelector("#modal.modal-overlay");
  modal.classList.add("show");
};

const getLikeOne = () => {
  likeList(
    {
      "memberId": memberInfo.value.memberId,
      "contentId": (attractionDetail.value.contentTypeId === 15) ? 0 : attractionDetail.value.contentId,
      "contentFestivalId": (attractionDetail.value.contentTypeId === 15) ? attractionDetail.value.contentId : 0,
    },
      ({ data }) => { 
        likeLength.value = data.length
        if (likeLength.value > 0) likeNo.value = data[0].no;
    },
    (err) => { 
      console.log(err)
    });
}

/** 모달창(디테일) 테스트 끝 */
</script>

<template>
  <div id="trip">
    <div id="star">
      <div class="snowBall_img" alt="스노우볼">
        <img
          id="TripFoodButton"
          src="@/assets/cityAttraction/TripFoodButton.png"
          alt=""
        />
        <img
          id="TripFestivalButton"
          src="@/assets/cityAttraction/TripFestivalButton.png"
          alt=""
        />
        <img
          id="TripChildHatButton"
          src="@/assets/cityAttraction/TripChildHatButton.png"
          alt=""
        />

        <select v-model="param.selected" class="header-searchSelect">
          <option value="All">All</option>
          <option value="title">여행지</option>
          <option value="addr1">위치</option>
        </select>
        <div class="search-container">
          <input
            type="text"
            class="search-input"
            v-model="param.searchWord"
            placeholder="검색어를 입력하세요"
          />
          <!-- 버튼에 @click="" 추가하기-->
          <button
            class="search-button"
            type="button"
            @click="getAttractionList"
          >
            검색
          </button>          
        </div>

        <!-- All -->
        <div class="img0" @click="loadAttractionByTheme('all')">
          
          <img
            id="TripSnowButton0"
            src="@/assets/cityAttraction/TripListAllButtonImg.png"
          />
          <div class="TripSnowButton0_text">
            <h3 class="select_all">All</h3>
          </div>
        </div>
        <!-- Food -->
        <div class="img1" @click="loadAttractionByTheme('food')">
          <div class="spotLight1"></div>
          <img
            id="TripSnowButton1"
            src="@/assets/cityAttraction/TripSnowButton.png"
          />
          <div class="TripSnowButton1_text">
            <h3>Food</h3>
          </div>
        </div>

        <!-- Festival -->
        <div class="img2" @click="loadAttractionByTheme('festival')">
          <img
            id="TripSnowButton2"
            src="@/assets/cityAttraction/TripSnowButton.png"
          />
          <div class="TripSnowButton2_text">
            <h3>Festival</h3>
          </div>
        </div>

        <!-- Family -->
        <div class="img3" @click="loadAttractionByTheme('family')">
          <img
            id="TripSnowButton3"
            src="@/assets/cityAttraction/TripSnowButton.png"            
          />
          <div class="TripSnowButton3_text">
            <h3>Family</h3>
          </div>
        </div>
        <div class="listItem">
          <ul class="list">
            <li
              class="item"
              @dblclick="showModal(attraction)"
              v-for="attraction in attractions"
              :key="attraction.index"
            >
              <a class="link" href="#">
                <img class="image" :src="attraction.firstImage" alt="" />
                <a class="item_title">{{ attraction.title }}</a>
                <a class="item_addr">{{ attraction.addr1 }}</a>
              </a>
            </li>
          </ul>
        </div>
        <div class="listControl">
          <img
            class="listBtn"
            src="@/assets/like/like_left_button.png"
            alt=""
            @click="btnScrolle(1)"
          />
          <img
            class="listBtn"
            src="@/assets/like/like_right_button.png"
            alt=""
            @click="btnScrolle(-1)"
          />          
        </div>
        <!-- 모달창(디테일) 테스트 -->
        <div id="modal_div">
          <!-- <button @click="showModal">모달 열기</button> -->
          <teleport to="body" v-if="isModalOpen">
            <AttractionDetailModal
              :attractionDetail="attractionDetail"
              :attractionOverview="attractionOverview"
              :attractionDetailIntro="attractionDetailIntro"
              :likeLength="likeLength"
              :likeNo="likeNo"
              @likeChange="likeChange"
              
            ></AttractionDetailModal>
          </teleport>
        </div>
        <!-- 모달창 테스트 끝 -->
      </div>
    </div>
  </div>
</template>

<style scoped>

.listControl {
  justify-content: space-between;
  width: 1100px;
  transform: translate(-45px, -315px);
  position: absolute;
  display: flex;
}

.listBtn {
  width: 50px;
  height: 50px;
  cursor: pointer;
}

.item:hover {
  transform: translate(10px, 10px); /* 3D처럼 사진 위에 마우스 효과 */
}
/** 테마버튼 효과 */

/** AttractionList Image 설정  */
a {
  text-decoration: none;
  line-height: 160%;
  padding-top: 5px;
  font-size: 13px;
  /* font-family: "ONE-Mobile-POP"; */
}
.item_title {
  font-size: 23px;
}
.item_title,
.item_addr {
  display: block;
  color: #fff;
  /* font-weight: 700; */
  /* line-height: 160%; */
}
.listItem {
  width: 900px;
  margin-left: 50px;
  padding: 4rem 0;
  overflow: hidden;
}

.list {
  padding: 1rem 0;
  /* margin-left: 30px; */
  margin-top: 245px;
  width: 100%;
  display: flex;
  transform: translate(0, 0);

  position: relative;
  perspective: 1000px;
  transform-style: preserve-3d;
}

.item {
  padding-right: 1rem;
  list-style: none;
  user-select: none;

  transition: transform 0.5s ease;
}

.item:first-child {
  padding-left: 1rem;
}

.link {
  display: block;
  -webkit-user-drag: none;
}

.image {
  display: block;
  width: 270px;
  height: 270px;
  border-radius: 50px;
  -webkit-user-drag: none;
}

/**  object-fit: cover; */

.list_item {
  display: inline-block;
  width: 150px;
  height: 125px;
  margin: 2px;
  overflow: hidden;
}

/** Attraction 배경 설정  */
.search-container {
  position: absolute;
  transform: translate(410px, 230px);
  width: 240px;
  border-radius: 0px 10px 0px 0px;
  border: none;
  outline: none;
  overflow: hidden;
}

input {
  color: #fff;
}

.search-input {
  width: 70%;
  margin-right: 70px;
  padding: 10px;
  /* border-radius: 10px 20px 20px 10px; */
  border: none;
  border-bottom: 1px solid white;
  outline: none;
  font-size: 16px;
  /* border-color: #fff; */
  background-color: rgba(255, 255, 255, 0); /*투명도 추가*/
}

.search-button {
  position: absolute;
  transform: translate(187px, 0px);
  width: 50px;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background-color: rgb(147, 174, 187, 0.4);
  color: #fff;
  /* border: none; */
  outline: none;
  /* border-color: #fff; */
  /* padding: 10px 20px; */
  border-radius: 10px 10px 10px 0;
  cursor: pointer;
}

.header-searchSelect {
  color: rgb(255, 255, 255);
  position: absolute;
  transform: translate(-167px, 230px);
  outline: none;
  height: 39px;
  /* border-radius: 16px 10px 10px 16px; */
  background-color: rgba(255, 255, 255, 0); /*투명도 추가*/
  font-size: 16px;
  padding: 5px;
  border: none;
  outline: none;
  /* border-color: rgb(223, 222, 222); */
}

/** Attraction Button(테마) 설정  */
#TripFoodButton {
  position: absolute;
  transform: translate(-260px, 65px);
  width: 120px;
  z-index: 2;
  /* display: none; */
  visibility: hidden;
}

#TripFestivalButton {
  position: absolute;
  transform: translate(-50px, 20px);
  width: 100px;
  z-index: 2;
  display: none;
}

#TripChildHatButton {
  position: absolute;
  transform: translate(135px, 85px);
  width: 130px;
  z-index: 2;
  display: none;
}

.spotLight1 {
  position: absolute;
  transform: translate(224px, 117px);
  border-radius: 100px;
  height: 85px;
  width: 85px;
  box-shadow: 0 0px 60px rgb(52, 255, 228);
  z-index: 1;
}

.img0 {
  cursor: pointer;
  position: absolute;
  transform: translate(190px, 85px);
  z-index: 5;
}

/** Attraction Button(홈) */
#TripSnowButton0 {
  cursor: pointer;
  position: absolute;
  /* transform: translate(-350px, 110px); */
  width: 150px;
  outline: none;
  transition: all ease 1s;
  padding-top: 0;
  z-index: 3;
}

#TripSnowButton0:hover {
  cursor: pointer;
  position: absolute;
  -ms-transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
  transform: rotate(360deg);
}

.TripSnowButton0_text {
  cursor: pointer;
  position: absolute;
  transform: translate(62px, 50px);
  color: #fff;
  z-index: 4;
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

.TripSnowButton0_text:hover {
  cursor: pointer;
  transform: translate(62px, 50px) scale(1.5);
}

#TripSnowButton1 {
  cursor: pointer;
  position: absolute;
  transform: translate(-150px, 60px);
  width: 100px;
  outline: none;
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

#TripSnowButton1:hover {
  cursor: pointer;
  transform: translate(-150px, 60px) scale(1.5);
}

.TripSnowButton1_text {
  cursor: pointer;
  position: absolute;
  transform: translate(377px, 85px);
  color: rgb(61, 71, 67);
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}
.TripSnowButton1_text:hover {
  cursor: pointer;
  transform: translate(377px, 85px) scale(1.5);
}

#TripSnowButton2 {
  cursor: pointer;
  position: absolute;
  transform: translate(20px, 60px);
  width: 100px;
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

#TripSnowButton2:hover {
  cursor: pointer;
  transform: translate(20px, 60px) scale(1.5);
}

.TripSnowButton2_text {
  cursor: pointer;
  position: absolute;
  transform: translate(532px, 85px);
  color: rgb(61, 71, 67);
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

.TripSnowButton2_text:hover {
  cursor: pointer;
  transform: translate(532px, 85px) scale(1.5);
}

#TripSnowButton3 {
  cursor: pointer;
  position: absolute;
  transform: translate(175px, 100px);
  width: 100px;
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

#TripSnowButton3:hover {
  cursor: pointer;
  transform: translate(175px, 100px) scale(1.5);
}

.TripSnowButton3_text {
  cursor: pointer;
  position: absolute;
  transform: translate(695px, 125px);
  color: rgb(61, 71, 67);
}

.TripSnowButton3_text:hover {
  cursor: pointer;
  transform: translate(695px, 125px) scale(1.5);
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

#trip {
  height: 1500px;
  margin: auto;
}

#star {
  margin-top: 100px;
}

.snowBall_img {
  background-image: url("@/assets/cityAttraction/snowBall.png");
  width: 1000px;
  height: 1150px;
  background-size: cover;
  text-align: center;
  margin: 0 auto;
  /* vertical-align: middle; */
}

body {
  background: linear-gradient(to bottom, #000118, #002c5a);
  min-height: 100vh;
  margin: 0;
  padding: 0;
  /* overflow: hidden;  스크롤 여부 */
  display: flex;
  /* font-family: sans-serif; */
}

/** good  */
@font-face {
  font-family: "UhBeeSe_hyun";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_five@.2.0/UhBeeSe_hyun.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

/** good */
@font-face {
  font-family: "SDSamliphopangche_Outline";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Outline.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "Bazzi";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/Bazzi.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "UhBeeJJIBBABBA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_five@.2.0/UhBeeJJIBBABBA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "Katuri";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_13@1.0/Katuri.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  /* font-family: "UhBeeSe_hyun"; */
  /* font-family: "SDSamliphopangche_Outline"; */
  font-family: "Bazzi";
  /* font-family: "Katuri"; */
}
</style>
