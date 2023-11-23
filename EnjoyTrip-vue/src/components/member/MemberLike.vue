<script setup>
// defineProps({ attraction: Object });

import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { likeList } from "@/api/member";
import AttractionDetailModal from "@/components/attraction/AttractionDetailModal.vue";
import { detailAttraction, detailIntroAttraction } from "@/api/attraction";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const router = useRouter();
const route = useRoute();
const scrollPosition = 0; // 원하는 스크롤 위치 (픽셀 단위)

//검색 선택, 페이지 처리
const attractions = ref([{}]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ATTRACTION_LIST_SIZE } = import.meta.env;

const isModalOpen = ref(false);
const likeLength = ref({});
const likeNo = ref();
const likeLists = ref([{}]);
const attractionDetail = ref({});
const attractionOverview = ref({});
const attractionDetailIntro = ref({});

const introParam = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  MobileOS: "ETC",
  MobileApp: "AppTest",
  _type: "json",
  contentId: "",
  contentTypeId: "",
});

const likeChange = (check) => {
  if (check) {
    if (startIndex.value > 0) {
      startIndex.value -= 1;
    }
    getLike();
  } else getLikeOne();
};

const getLike = () => {
  likeList(
    {
      memberId: memberInfo.value.memberId,
    },
    ({ data }) => {
      likeLists.value = data;
      // if (likeLength.value > 0) likeNo.value = data[0].no;
    },
    (err) => {
      console.log(err);
    }
  );
};

const getLikeOne = () => {
  likeList(
    {
      memberId: memberInfo.value.memberId,
      contentId:
        attractionDetail.value.contentTypeId === 15
          ? 0
          : attractionDetail.value.contentId,
      contentFestivalId:
        attractionDetail.value.contentTypeId === 15
          ? attractionDetail.value.contentId
          : 0,
    },
    ({ data }) => {
      likeLength.value = data.length;
      if (likeLength.value > 0) likeNo.value = data[0].no;
    },
    (err) => {
      console.log(err);
    }
  );
};

onMounted(() => {
  getLike();
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 0,
    top: 0,
    behavior: "smooth",
  });
});

/** 가로 슬라이드 목록 구현 */
const startIndex = ref(0);
const visibleItemCount = 1; // 한 번에 보이는 목록 개수

const moveLeft = () => {
  if (startIndex.value > 0) {
    startIndex.value -= 1;
  }
};

const moveRight = () => {
  if (startIndex.value < likeLists.value.length - visibleItemCount) {
    startIndex.value += 1;
  }
};

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
            v-if="likeLists.length === 0"
            :key="0"
            style="
              border: 1px solid #fff;
              display: flex;
              justify-content: center;
              align-items: center;
              color: #fff;
              font-size: 30px;
            "
          >
            텅텅..
            찜을 눌러주세요.
          </div>
          <div
            v-else
            class="item"
            v-for="item in likeLists.slice(
              startIndex,
              startIndex + visibleItemCount
            )"
            :key="item.index"
            @click="showModal(item)"
          >
            <a class="link" href="#">
              <a class="item_list"
                >list : {{ startIndex + 1 }} / {{ likeLists.length }}</a
              >
              <img class="image" :src="item.firstImage" alt="" />
              <a class="item_title">{{ item.title }}</a>
              <a class="item_addr">{{ item.addr1 }}</a>
            </a>
          </div>
        </div>
        <div class="controls">
          <img
            class="left-btn"
            src="@/assets/like/like_left_button.png"
            alt=""
            @click="moveLeft"
          />
          <img
            class="right-btn"
            src="@/assets/like/like_right_button.png"
            alt=""
            @click="moveRight"
          />
        </div>
        <!-- 모달창(디테일) 테스트  -->
        <div id="modal_div">
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

    <div class="like_snow_circle_img">
      <!-- <img class="like_tree" src="@/assets/like/like_tree.png" alt="" /> -->
      <div id="gifs">
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
  </div>
  <!-- <img src="@/assets/like/like_snow_circle2.png" alt=""> -->
</template>

<style scoped>
/** 윗부분 */
.like_top_background {
  height: 550px;
  /* margin: 0 auto; */
}

/** 눈 행성 위 꾸미기 */
.like_snow_circle_img {
  background-image: url("@/assets/like/like_snow_circle.png");
  width: 1400px;
  height: 1300px;
  justify-content: center;

  background-size: cover;
  text-align: center;
  margin: 0 auto;
  /* overflow: hidden;  */
  visibility: hidden; /** 나중에 풀자! */
}

#gifs {
  transform: translate(35px, 0px);
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
  transform: translate(610px, -600px);
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
  width: 320px; /* 각 아이템의 너비 조정 */
  height: 420px; /* 각 아이템의 높이 조정 */
  padding: 4px;
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
  padding: 20px 20px;
  margin-top: 30px;
  width: 30px;
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 50%;
  border-style: outset;
}

.left-btn:active {
  margin-top: 33px;
  margin-left: 3px;
}

.right-btn:active {
  margin-top: 33px;
  margin-right: -3px;
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
  text-align: center;
  /* font-weight: 700; */
  /* line-height: 160%; */
}
.item_list {
  display: block;
  color: #fff;
  text-align: center;
  font-size: 15px;
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
  margin: 0 auto;
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
