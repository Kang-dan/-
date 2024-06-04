<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import MemberMypageModal from "@/components/member/MemberMypageModal.vue";

const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);

const isModalOpen = ref(false); //마이페이지 모달창

const router = useRouter();

const randomBallImages = ref(
  Array.from(
    { length: 17 },
    (_, index) =>
      `src/assets/cityLight/treeBall${Math.floor(Math.random() * 5) + 1}.png`
  )
);

const left = [
  0, 260, 340, 425, 520, 610, 450, 280, 360, 180, 300, 155, 280, 480, 500, 620,
  720, 395,
];
const top = [
  0, 380, 285, 390, 325, 650, 560, 570, 670, 670, 810, 900, 975, 920, 750, 900,
  1000, 1050,
];
const sidoCode = [
  0, 2, 1, 31, 32, 35, 33, 8, 3, 34, 37, 15, 38, 36, 4, 7, 6, 39,
];
const imgTitle = [
  "",
  "인천", //2
  "서울", //1
  "경기", //31
  "강원", //32
  "경북", //35
  "충북", //33
  "세종", //8
  "대전", //3
  "충남", //34
  "전북", //37
  "광주", //5
  "전남", //38
  "경남", //36
  "대구", //4
  "울산", //7
  "부산", //6
  "제주", //39
];

const moveLogin = () => {
  if (!isLogin.value) router.push({ name: "member-login" });
};

const moveAttractionList = (sidoCode) => {
  router.push({ name: "attraction-list", params: { sidoCode: sidoCode } });
};

const moveLike = () => {
  if (isLogin.value) router.push({ name: "member-like" });
  else if (!isLogin.value) confirm("로그인을 해주세요.");
};

const moveBoard = () => {
  router.push({ name: "board-list" }); //로그인 안해도 게시판은 들어갈 수 있음. (지역선택 후 글을 볼 때에는 로그인 필요)
};

/** 타이머 */
const targetTime = new Date(); // 현재 날짜와 시간으로 초기화

// D-day를 설정 (예: 12월 25일 00:00:00)
targetTime.setDate(1);
targetTime.setMonth(12); // 0부터 시작하므로 11은 12월을 의미합니다.
targetTime.setHours(0, 0, 0, 0);

const timer = ref({
  days: 0,
  hours: 0,
  minutes: 0,
  seconds: 0,
});

let intervalId;

onMounted(() => {
  startTimer();
});

onBeforeUnmount(() => {
  stopTimer();
});

const startTimer = () => {
  intervalId = setInterval(updateTimer, 1000);
};

const stopTimer = () => {
  clearInterval(intervalId);
};

const updateTimer = () => {
  const currentTime = new Date();
  const timeDifference = targetTime - currentTime;

  if (timeDifference > 0) {
    timer.value.days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    timer.value.hours = Math.floor(
      (timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    timer.value.minutes = Math.floor(
      (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
    );
    timer.value.seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);
  } else {
    // 타이머 종료 후 원하는 동작을 수행할 수 있습니다.
    stopTimer();
    console.log("타이머 종료");
  }
};

const formatTime = (value) => {
  return value < 10 ? `0${value}` : value;
};

/** 마이페이지 - 모달창 */
const showModal = async () => {
  // 로그인이 되어있을 때에만 열리게 하기
  if (isLogin.value) {
    await changeModalOpen();
    // 모달이 나타날 때 show 클래스 추가
    const modal = document.querySelector("#modal.modal-overlay");
    modal.classList.add("show");
  } else if (!isLogin.value) confirm("로그인을 해주세요.");
};

const changeModalOpen = () => {
  isModalOpen.value = true;
};

const moveMypage = () => {
  // mypage-bag 이미지를 클릭했을 때 모달창 열도록 설정
  showModal();
};

// const getMypage = () => {
//   memberId(
//     {
//       memberId: memberInfo.value.memberId,
//     },
//     (err) => {
//       console.log(err);
//     }
//   );
// };

const hovered = ref(false);
</script>
<template>
  <p v-if="isLogin" class="welcome_text">반가워요!</p>

  <p v-if="!isLogin" class="not_login_text">별을 클릭해 로그인을 해주세요</p>

  <div class="timer-container">
    <div class="timer-section">
      <div class="timer-value">{{ timer.days }}</div>
      <div class="timer-label">일</div>
    </div>
    <div class="timer-section">
      <div class="timer-value">{{ formatTime(timer.hours) }}</div>
      <div class="timer-label">시간</div>
    </div>
    <div class="timer-section">
      <div class="timer-value">{{ formatTime(timer.minutes) }}</div>
      <div class="timer-label">분</div>
    </div>
    <div class="timer-section">
      <div class="timer-value">{{ formatTime(timer.seconds) }}</div>
      <div class="timer-label">초</div>
    </div>
  </div>

  <img id="snow_footer" src="@/assets/back_footter.png" />

  <div id="treeMap">
    <div id="treeParent">
      <div id="star" @click="moveLogin">
        <img
          id="TreeStar"
          :style="{
            transform: isLogin
              ? 'scale(1)'
              : hovered
              ? 'scale(1.5)'
              : 'scale(1)',
            cursor: isLogin ? 'default' : 'pointer',
          }"
          @mouseover="hovered = true"
          @mouseout="hovered = false"
          src="../assets/TreeStar.png"
          alt="별"
          title="로그인"
        />
      </div>
      <img
        id="treeImg"
        src="../assets/treeMap.png"
        alt="트리지도"
        title="전구 위에 마우스를 올려주세요"
      />
      <span id="treeBalls">
        <!-- 라우터 링크 달기 <RouterLink :to=""}>이미지</RouterLink>-->
        <!-- 전구(도시)17개:relative -->

        <!-- <img
          class="city_ball"
          v-for="(image, n) in randomBallImages"
          :key="n"
          :id="`ball${n + 1}`"
          :src="image"
          :alt="n + 1"
          :title="imgTitle[n + 1]"
          :style="`position: absolute; width: 130px; left: ${
            left[n + 1]
          }px; top: ${top[n + 1]}px; z-index: 3`"
          @click="moveAttractionList(sidoCode[n + 1])"
        /> -->

        <div v-for="(city, n) in imgTitle.slice(1)" :key="n">
          <img
            class="city_ball"
            :src="randomBallImages[n]"
            :alt="n + 1"
            :title="city"
            :style="` position: absolute; width: 130px; left: ${
              left[n + 1]
            }px; top: ${top[n + 1]}px; z-index: 3`"
            @click="moveAttractionList(sidoCode[n + 1])"
          />
          <span
            class="city-label"
            @click="moveAttractionList(sidoCode[n + 1])"
            :style="`position: absolute; left: ${left[n + 1] + 50}px; top: ${
              top[n + 1] + 70
            }px; `"
          >
            {{ city }}
          </span>
        </div>
      </span>

      <span id="mypage">
        <img
          class="mypage-bag"
          src="../assets/mypage/mypage-bag.png"
          alt=""
          @click="moveMypage"
        />
        <img
          class="mypage_camera"
          src="../assets/mypage/mypage_camera.gif"
          alt=""
          @click="moveMypage"
        />
      </span>
      <span id="present">
        <!-- 선물상자(마이페이지) -->
        <img
          id="PresentCloseGreen"
          src="../assets/PresentCloseGreen.png"
          alt="선물상자"
        />
        <img
          id="PresentOpenGreen"
          src="../assets/PresentOpen.png"
          @click="moveLike"
        />
      </span>
      <span id="board">
        <!-- 게시판 -->
        <img
          class="board_button"
          src="@/assets/board/board_button.png"
          alt=""
          @click="moveBoard"
        />
      </span>
    </div>
  </div>

  <div id="modal_div">
    <!-- <button @click="showModal">모달 열기</button> -->
    <teleport to="body" v-if="isModalOpen">
      <MemberMypageModal></MemberMypageModal>
    </teleport>
  </div>
</template>

<style scoped>
/** 로그인 텍스트 */
.welcome_text {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  font-size: 30px;
  text-shadow: 5px 5px 10px rgb(255, 238, 0); /* 텍스트 주위에 흐릿한 빛 효과 추가 */
}

.not_login_text {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  font-size: 30px;
  text-shadow: 5px 5px 10px rgb(255, 238, 0); /* 텍스트 주위에 흐릿한 빛 효과 추가 */
}

#snow_footer {
  position: absolute;
  margin-top: 220px;
  width: 100%;
  height: 1300px;
  z-index: 1;
  /*visibility: hidden; /* 나중에 풀자! */
}

/** 게시판 */
.board_button {
  position: absolute;
  transform: translate(-220px, -470px);
  width: 390px;
  cursor: pointer;
  z-index: -1;
}

/** 전구 위 지역 글씨 */
.city-label {
  display: none;
  color: #ffffff;
  text-align: center;
  z-index: 4;
  font-size: 17px;
  background-color: rgba(0, 0, 0, 0.35);
  border-radius: 10%;
  cursor: pointer;
}

/** 폰트  */
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

/** 타이머 */
.timer-container {
  position: fixed;
  top: 35px;
  right: 20px;
  color: #ffffff;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 20px;
  padding: 6px 5px;
  border-radius: 15px;
  overflow: hidden;
  background-color: rgba(190, 190, 190, 0.541);
  /* background-color: rgba(192, 2, 2, 0.541); */
  width: 200px; /* 변경된 너비 설정 */
}

.timer-section {
  text-align: center;
  margin: auto 2px;
  flex: 1; /* 각 섹션 동일한 비율로 설정 */
}

.timer-value {
  font-size: 24px;
  font-weight: bold;
  padding: 6px;
  background-color: #ffffff;
  color: #000000;
  border-radius: 10px;
  width: 30px; /* 변경된 숫자 너비 설정 */
  margin: 0 auto; /* 가운데 정렬 */
}

.timer-label {
  font-size: 16px;
  margin-top: 5px;
}

/** 트리 */
#treeParent {
  position: relative;
}

#TreeStar:hover {
  transform: scale(1.5);
  -webkit-transform: scale(1.5);
  /* align-items: center; */
}

#treeBalls img:hover {
  transform: scale(1.3);
  -webkit-transform: scale(1.3);
  align-items: center;
}

#TreeStar {
  margin-top: 50px;
  width: 200px;
  transition: transform 0.5s ease; /* 트랜지션 효과 추가 */
}

#star {
  position: absolute;
  width: 5px;
  height: 5px;
  left: 360px;
  top: 0px;
  z-index: 2;
  /* margin: auto; */
  font-size: 180px;
  cursor: pointer;
}

#treeMap {
  display: flex;
  justify-content: center;
  position: relative;
  /* display: block; */
  /* position: relative; */
  top: 40px;
  /* left: 0px; */
  width: 100%;
  height: 100%;
  /* margin-left: 100px; */
  z-index: 2;
  /* border: 1px solid; */
  margin: 0 auto;
  /* text-align: center; */
  /* margin-top: 100px; */
}

#treeImg {
  /*부모 트리배경 */
  position: relative;
  z-index: 1;
  text-align: center;
  margin-top: 150px;
  width: 900px;
  left: 7px;
}

.city_ball {
  cursor: pointer;
}

/** (마이페이지) */
#mypage {
  position: absolute;
  /* right: 350px; */
  /* bottom: 100px; */
  transform: translate(630px, -150px);
  /* left: 850px; */
  /* width: 200px; */
  /* margin-top: 1010px; */
  z-index: 5;
}

.mypage-bag {
  position: absolute;
  /* transform: translate(-500px, -20px); */
  width: 210px;
  cursor: pointer;
  z-index: 999;
}

.mypage_camera {
  position: absolute;
  transform: translate(-90px, 90px);
  width: 150px;
  cursor: pointer;
  z-index: 999;
}

/** 선물(찜 목록) */
#present {
  /* display: none; */
  position: absolute;
  transform: translate(800px, -250px);
  /* left: 850px; */
  width: 100px;
  /* margin-top: 1010px; */
  z-index: 4;
}

#PresentCloseGreen {
  /* margin-left: 25px; */
  /* margin-bottom: 27px; */
  width: 270px;
  /* margin-left: 100px; */
}

#PresentOpenGreen {
  width: 320px;
  /* margin-bottom: 705px; */
  /* margin-left: -15px; */
  transform: translate(-39px, -63px);
  display: none;
  cursor: pointer;
}

#present:hover #PresentOpenGreen {
  display: block;
}

#present:hover #PresentCloseGreen {
  display: none;
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
