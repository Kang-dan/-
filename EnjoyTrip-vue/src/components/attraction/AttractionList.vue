<script setup>
// defineProps({ attraction: Object });

import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { listAttraction } from "@/api/attraction";
const router = useRouter();

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
  contentTypeId: 39,
  sidoCode: 3,
});

onMounted(() => {
  getAttractionList();
});

const getAttractionList = () => {
  //서버에서 사진, 관광지명, 주소 가져오기
  listAttraction(
    param.value,
    ({ data }) => {
      attractions.value = data;
      console.log(attractions.value[0]);
    },
    (error) => {
      console.log(error);
    }
  );
};
// const getAttractionList = () => {
//   //서버에서 사진, 관광지명, 주소 가져오기
//   listAttraction(
//     param.value,
//     ({ data }) => {
//       attractions.value = data.attractions;
//       currentPage.value = data.currentPage;
//       totalPage.value = data.totalPageCount;
//     },
//     (error) => {
//       console.log(error);
//     }
//   );
// };

const changeKey = (val) => {
  param.value.key = val;
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getAttractionList();
};

//이미지 가로 스크롤
let list = null;
let listScrollWidth = 0;
let listClientWidth = 0;
// 이벤트마다 갱신될 값
let startX = 0;
let nowX = 0;
let endX = 0;
let listX = 0;

onMounted(() => {
  list = document.querySelector(".list");
  listScrollWidth = list.scrollWidth;
  listClientWidth = list.clientWidth;
  bindEvents();
});

const getClientX = (e) => {
  const isTouches = e.touches ? true : false;
  return isTouches ? e.touches[0].clientX : e.clientX;
};

const getTranslateX = () => {
  return parseInt(getComputedStyle(list).transform.split(/[^\-0-9]+/g)[5]);
};

const setTranslateX = (x) => {
  list.style.transform = `translateX(${x}px)`;
};

//이벤트 연결
const bindEvents = () => {
  list.addEventListener("mousedown", onScrollStart);
  list.addEventListener("touchstart", onScrollStart);
  list.addEventListener("click", onClick);
};

//스크롤 진행 이벤트 구현
const onScrollStart = (e) => {
  startX = getClientX(e);
  window.addEventListener("mousemove", onScrollMove);
  window.addEventListener("touchmove", onScrollMove);
  window.addEventListener("mouseup", onScrollEnd);
  window.addEventListener("touchend", onScrollEnd);
};

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
  nowX = getClientX(e);
  setTranslateX(listX + nowX - startX);
};

//클릭 이벤트 구현
const onClick = (e) => {
  if (startX - endX !== 0) {
    e.preventDefault();
  }
};
</script>

<template>
  <div id="trip">
    <div id="star">
      <div class="snowBall_img" alt="스노우볼">
        <img id="TripFoodButton" src="@/assets/cityAttraction/TripFoodButton.png" alt="" />
        <img id="TripFestivalButton" src="@/assets/cityAttraction/TripFestivalButton.png" alt="" />
        <img id="TripChildHatButton" src="@/assets/cityAttraction/TripChildHatButton.png" alt="" />

        <select class="header-searchSelect">
          <option value="All">All</option>
          <option value="title">여행지</option>
          <option value="address">위치</option>
        </select>
        <div class="search-container">
          <input
            type="text"
            class="search-input"
            v-model="param.word"
            placeholder="검색어를 입력하세요"
          />
          <!-- 버튼에 @click="" 추가하기-->
          <button class="search-button" type="button">검색</button>
        </div>

        <div class="img0">
          <img id="TripSnowButton0" src="@/assets/cityAttraction/TripListAllButtonImg.png" alt="" />
          <div class="TripSnowButton0_text">
            <h3>All</h3>
          </div>
        </div>

        <div class="img1">
          <div class="spotLight1"></div>
          <img id="TripSnowButton1" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
          <div class="TripSnowButton1_text">
            <h3>Food</h3>
          </div>
        </div>

        <div class="img2">
          <img id="TripSnowButton2" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
          <div class="TripSnowButton2_text">
            <h3>Festival</h3>
          </div>
        </div>

        <div class="img3">
          <img id="TripSnowButton3" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
          <div class="TripSnowButton3_text">
            <h3>Family</h3>
          </div>
        </div>

        <div class="listItem">
          <ul class="list">
            <li class="item">
              <a class="link" href="#">
                <img class="image" :src="attractions[0].firstImage" alt="" />
              </a>
            </li>
            <li class="item">
              <a class="link" href="#">
                <img class="image" :src="attractions[0].firstImage" alt="" />
              </a>
            </li>
            <li class="item">
              <a class="link" href="#">
                <img class="image" :src="attractions[0].firstImage" alt="" />
              </a>
            </li>
            <li class="item">
              <a class="link" href="#">
                <img class="image" :src="attractions[0].firstImage" alt="" />
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.listItem {
  width: 900px;
  margin-left: 52px;
  padding: 4rem 0;
  overflow: hidden;
}

.list {
  padding: 1rem 0;
  /* margin-left: 30px; */
  margin-top: 255px;
  width: 100%;
  display: flex;
  transform: translate(0, 0);
}

.item {
  padding-right: 1rem;
  list-style: none;
  user-select: none;
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
  width: 290px;
  height: 290px;
  border-radius: 30px;
  -webkit-user-drag: none;
}

/* #list_item {
  display: inline-block;
  width: 300px;
  height: 270px;
  margin: 10px;
  overflow: hidden;
}

#list_img {
  display: inline-block;
  width: 100%;
  height: 270px;
  overflow: hidden;
  object-fit: cover;
  border-radius: 10px;
} */
/* body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  background-color: #f4f4f4;
} */

.list_item {
  display: inline-block;
  width: 150px;
  height: 125px;
  margin: 2px;
  overflow: hidden;
}

.search-container {
  position: absolute;
  transform: translate(400px, 250px);
  width: 300px;
  border-radius: 10px 20px 20px 10px;
  overflow: hidden;
}

.search-input {
  width: 100%;
  padding: 10px;
  /* border: none; */
  border-radius: 10px 20px 20px 10px;
  /* outline: none; */
  font-size: 16px;
  background-color: rgba(255, 255, 255, 0.1); /* 투명도 추가 */
}

.search-button {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  background-color: rgb(147, 174, 187);
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 0 20px 20px 0;
  cursor: pointer;
}

.header-searchSelect {
  position: absolute;
  transform: translate(-185px, 250px);
  /* outline: none; */
  height: 39px;
  border-radius: 16px 10px 10px 16px;
  background-color: rgba(255, 255, 255, 0.1); /*투명도 추가*/
  font-size: 16px;
  padding: 5px;
  border: none;
}

#TripFoodButton {
  position: absolute;
  transform: translate(-260px, 65px);
  width: 120px;
  z-index: 2;
  display: none;
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
  transform: translate(260px, 160px);
  border-radius: 100px;
  height: 85px;
  width: 85px;
  box-shadow: 0 0px 60px rgb(52, 255, 228);
}

.img0 {
  position: absolute;
  transform: translate(150px, 110px);
}

#TripSnowButton0 {
  position: absolute;
  /* transform: translate(-350px, 110px); */
  width: 150px;
  outline: none;
  transition: all ease 1s;
  padding-top: 0;
}

#TripSnowButton0:hover {
  position: absolute;
  -ms-transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
  transform: rotate(360deg);
}

.TripSnowButton0_text {
  position: absolute;
  /* transform: translate(213px, 195px); */
  color: rgb(61, 71, 67);
}

#TripSnowButton1 {
  position: absolute;
  transform: translate(-250px, 150px);
  width: 100px;
  outline: none;
}

.TripSnowButton1_text {
  position: absolute;
  transform: translate(280px, 175px);
  color: rgb(61, 71, 67);
}

#TripSnowButton2 {
  position: absolute;
  transform: translate(-50px, 100px);
  width: 100px;
}
.TripSnowButton2_text {
  position: absolute;
  transform: translate(470px, 125px);
  color: rgb(61, 71, 67);
}

#TripSnowButton3 {
  position: absolute;
  transform: translate(150px, 150px);
  width: 100px;
}
.TripSnowButton3_text {
  position: absolute;
  transform: translate(670px, 170px);
  color: rgb(61, 71, 67);
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
  font-family: sans-serif;
}
</style>
