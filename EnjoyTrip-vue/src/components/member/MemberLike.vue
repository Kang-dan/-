<script setup>
// defineProps({ attraction: Object });

import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const router = useRouter();
const route = useRoute();
const scrollPosition = 0; // 원하는 스크롤 위치 (픽셀 단위)

//검색 선택, 페이지 처리
const attractions = ref([{}]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ATTRACTION_LIST_SIZE } = import.meta.env;

onMounted(() => {
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 0,
    top: 0,
    behavior: "smooth",
  });
});

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getAttractionList();
};

const onScrollMove = (e) => {
  const nowX = getClientX(e);
  setTranslateX(listX + nowX - startX);
};

/** 가로 슬라이드 목록 구현 */
const items = ref(["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"]);
const startIndex = ref(0);
const visibleItemCount = 1; // 한 번에 보이는 목록 개수

const moveLeft = () => {
  if (startIndex.value > 0) {
    startIndex.value -= 1;
  }
};

const moveRight = () => {
  if (startIndex.value < items.value.length - visibleItemCount) {
    startIndex.value += 1;
  }
};

onMounted(() => {
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 0,
    top: 0,
    behavior: "smooth",
  });
});
</script>

<template>
  <!-- <div id="trip">
    <div id="star">
      <div class="snowBall_img" alt="스노우볼">
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
         모달창(디테일) 테스트 
        <div id="modal_div">
          //<button @click="showModal">모달 열기</button>
          <teleport to="body" v-if="isModalOpen">
            <AttractionDetailModal
              :attractionDetail="attractionDetail"
              :attractionOverview="attractionOverview"
              :attractionDetailIntro="attractionDetailIntro"
            ></AttractionDetailModal>
          </teleport>
        </div>
        모달창 테스트 끝
      </div>
    </div>
  </div>
  <div class="like_top_background"></div> -->

  <!-- <div class="like_top_background"> -->
  <!-- <div class="body">
    <div class="container">
      <div class="carousel" ref="carousel">
        <div class="item a">A</div>
        <div class="item b">B</div>
        <div class="item c">C</div>
        <div class="item d">D</div>
        <div class="item e">E</div>
        <div class="item f">F</div>
        <div class="item g">G</div>
        <div class="item h">H</div>
        <div class="item i">I</div>
        <div class="item j">J</div>
        <div class="item k">K</div>
        <div class="item l">L</div>
      </div>
    </div>

    <div class="next" @click="rotate('n')">Next</div>
    <div class="prev" @click="rotate('p')">Prev</div>
  </div> -->
  <!-- </div> -->

  <div>
    <div class="like_top_background">
      <div class="carousel-container">
        <div class="carousel" ref="carousel">
          <div
            class="item"
            v-for="(item, index) in items.slice(
              startIndex,
              startIndex + visibleItemCount
            )"
            :key="index"
          >
            {{ item }}
          </div>
        </div>
        <div class="controls">
          <button class="left-btn" @click="moveLeft">←</button>
          <button class="right-btn" @click="moveRight">→</button>
        </div>
      </div>
    </div>

    <div class="like_snow_circle_img">
      <!-- <img class="like_tree" src="@/assets/like/like_tree.png" alt="" /> -->
      <img
        class="gif1_bell"
        src="@/assets/like/like-christmas-bell-unscreen.gif"
        alt=""
      />
      <img
        class="gif2_box"
        src="@/assets/like/like-christmas-presentBox-unscreen.gif"
        alt=""
      />
      <img
        class="gif3_santa_ski"
        src="@/assets/like/like-christmas-santa-ski-unscreen.gif"
        alt=""
      />
      <img
        class="gif4_santa_snow"
        src="@/assets/like/like-christmas-snow-santa-unscreen.gif"
        alt=""
      />
      <img
        class="gif5_snowball"
        src="@/assets/like/like-christmas-snowball-snowma-unscreen.gif"
        alt=""
      />
      <img
        class="gif6_snowman"
        src="@/assets/like/like-christmas-snowman-unscreen.gif"
        alt=""
      />
      <img
        class="gif7_tree"
        src="@/assets/like/like-christmas-tree-unscreen.gif"
        alt=""
      />
      <img
        class="gif8_tree2"
        src="@/assets/like/like-christmas-tree2-unscreen.gif"
        alt=""
      />
    </div>
  </div>
  <!-- <img src="@/assets/like/like_snow_circle2.png" alt=""> -->
</template>

<style scoped>
/** 윗부분 */
.like_top_background {
  height: 470px;
  /* margin: 0 auto; */
}

/** 눈 행성 위 꾸미기 */
.like_snow_circle_img {
  background-image: url("@/assets/like/like_snow_circle.png");
  width: 1300px;
  height: 1300px;
  justify-content: center;

  background-size: cover;
  text-align: center;
  margin: 0 auto;
  /* margin: 0 auto; */
}

/** 나무 */
.like_tree {
  position: absolute;
  transform: translate(610px, -50px);
  width: 100px;
  z-index: 2;
}

.gif1_bell {
  display: flex;
  position: absolute;
  width: 100px;
  transform: translate(610px, -550px);
  z-index: 999;
  display: none;
}
.gif2_box {
  display: flex;
  position: absolute;
  width: 80px;
  transform: translate(570px, 25px);
  z-index: 998;
  display: none;
}
.gif3_santa_ski {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(450px, 0px);
  z-index: 999;
  display: none;
}
.gif4_santa_snow {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(500px, -10px);
  display: none; /**찐 넌 */
}
.gif5_snowball {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(700px, -10px);
  display: none; /**찐 넌 */
}
.gif6_snowman {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(650px, 0px);
  display: none;
}
.gif7_tree {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(580px, -70px);
  display: none; /**찐 넌 */
}
.gif8_tree2 {
  display: flex;
  position: absolute;
  width: 150px;
  transform: translate(580px, -70px);
  display: none;
}

/* 캐러셀 스타일 */
.carousel-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative; /*부모로부터 상대적인 위치 설정*/
}

.carousel {
  position: absolute;
  left: 50%; /* 부모 요소 중앙에서 가로 위치 조정 */
  transform: translate(-50%, 30px);
  width: 50%; /* 원하는 너비 조정 */
  display: flex;
  justify-content: center; /* 아이템을 수평 중앙에 정렬 */
}

.item {
  /* flex: 0 0 auto; */
  width: 300px; /* 각 아이템의 너비 조정 */
  height: 300px; /* 각 아이템의 높이 조정 */
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  font-size: 18px; /* 아이템의 글자 크기 조정 */
  border-radius: 20%;
  cursor: pointer;
  color: #fff;
}

.controls {
  position: absolute;
  /* top: 50%; 부모 요소 중앙에서 세로 위치 조정 */
  transform: translateY(180px);
  width: 50%; /* 원하는 너비 조정 */
  display: flex;
  justify-content: space-between;
}

.left-btn,
.right-btn {
  padding: 5px 10px;
  cursor: pointer;
}

.left-btn {
  position: absolute;
  left: 0;
}

.right-btn {
  position: absolute;
  right: 0;
}

button {
  margin: 0;
  height: 50px;
  width: 50px;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.253);
  border: none;
  color: #fff;
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

.list_item {
  display: inline-block;
  width: 150px;
  height: 125px;
  margin: 2px;
  overflow: hidden;
}

#trip {
  height: 1500px;
  margin: auto;
}

#star {
  margin-top: 100px;
}

/* .snowBall_img {
  background-image: url("@/assets/cityAttraction/snowBall.png");
  width: 1000px;
  height: 1150px;
  background-size: cover;
  text-align: center;
  margin: 0 auto;
} */

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
