<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);

const router = useRouter();

const randomBallImages = ref(
  Array.from(
    { length: 17 },
    (_, index) =>
      `src/assets/cityLight/treeBall${Math.floor(Math.random() * 5) + 1}.png`
  )
);

const left = [
  0, 270, 360, 430, 530, 620, 450, 280, 360, 180, 300, 160, 280, 480, 500, 620,
  720, 410,
];
const top = [
  0, 350, 270, 380, 320, 630, 560, 570, 670, 670, 800, 900, 960, 900, 750, 900,
  1000, 1050,
];
const sidoCode = [
  0, 2, 1, 31, 32, 35, 33, 8, 3, 34, 37, 5, 38, 36, 4, 7, 6, 39,
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
};

/** 타이머 */
const targetTime = new Date(); // 현재 날짜와 시간으로 초기화

// D-day를 설정 (예: 12월 25일 00:00:00)
targetTime.setDate(26);
targetTime.setMonth(11); // 0부터 시작하므로 11은 12월을 의미합니다.
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
</script>
<template>
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

  <div id="treeMap">
    <div id="treeParent">
      <div id="star" @click="moveLogin">
        <img
          id="TreeStar"
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
            :style="`position: absolute; width: 130px; left: ${
              left[n + 1]
            }px; top: ${top[n + 1]}px; z-index: 3`"
            @click="moveAttractionList(sidoCode[n + 1])"
          />
          <span
            class="city-label"
            :style="`position: absolute; left: ${left[n + 1] + 50}px; top: ${
              top[n + 1] + 70
            }px; `"
          >
            {{ city }}
          </span>
        </div>
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
    </div>
  </div>
</template>

<style scoped>
/** 전구 위 지역 글씨 */
.city-label {
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
  background-color: rgba(192, 2, 2, 0.541);
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
  left: 375px;
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
  /* top: 0px; */
  /* left: 0px; */
  width: 100%;
  height: 100%;
  /* margin-left: 100px; */
  z-index: 1;
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
  width: 1150px;
  left: -100px;
}

.city_ball {
  cursor: pointer;
}

/** 선물(마이페이지) */
#present {
  position: absolute;
  right: 200px;
  bottom: -60px;
  /* left: 850px; */
  width: 100px;
  /* margin-top: 1010px; */
  z-index: 4;
}

#PresentCloseGreen {
  margin-left: 25px;
  margin-bottom: 27px;
  width: 270px;
  /* margin-left: 100px; */
}

#PresentOpenGreen {
  width: 320px;
  margin-bottom: 35px;
  margin-left: -15px;
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
