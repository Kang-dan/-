<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import audioFile from "@/assets/music/Christmas.mp3";
import { RouterLink, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, memberInfo } = storeToRefs(memberStore);

const router = useRouter();
function moveMain() {
  // router.replace({ path: "/" }); // 현재 라우트를 대체
  // router.push({ path: "/", replace: true }); // 위와 같음
  router.push({ name: "main" }); // 새 히스토리 항목을 푸시
  window.scrollTo({ top: 0 });
}

// const logout = () => {
//   memberStore.memberLogout(memberInfo.value.memberId);
// };

//로그아웃 완료된 후 홈화면으로 이동하기 (비동기로 처리)
const logout = async () => {
  await memberStore.memberLogout(memberInfo.value.memberId);
  moveMain(); // 로그아웃 후 홈화면으로 이동
};

const headerMain = () => {
  router.replace({ name: "main" });
  window.scrollTo({ top: 0 });
};

/** 음악 */
const audio = ref(new Audio());
let audioAbortController = null;
let lastPlayedTime = 0;

const loadAudio = () => {
  audio.value.src = audioFile;
  audio.value.loop = true;
  audio.value.currentTime = lastPlayedTime;

  // Promise를 반환하여 오디오 로딩이 완료될 때까지 대기
  return audio.value.play();
};

const playAudio = async () => {
  try {
    if (audioAbortController) {
      audioAbortController.abort();
      audioAbortController = null;
    }

    if (audio.value.paused) {
      // loadAudio 함수를 호출하고 로딩이 완료되면 재생
      await loadAudio();
      await audio.value.play();
    } else {
      lastPlayedTime = audio.value.currentTime;
      audio.value.pause();
    }
  } catch (error) {
    if (error.name === "AbortError") {
      console.warn("Audio playback aborted.");
    } else {
      console.error("Audio playback error:", error);
    }
  }
};

/** 타이머 */
const targetTime = new Date(); // 현재 날짜와 시간으로 초기화

// D-day를 설정 (예: 12월 25일 00:00:00)
targetTime.setDate(25);
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
  <button class="home-button" @click="headerMain()">
    <img class="home_btn_rudol" src="@/assets/home_btn_rudol.png" alt="" />
  </button>

  <button v-if="isLogin" @click.prevent="logout()" class="logout-button">
    로그아웃
  </button>

  <!-- <audio autoplay loop controls>
    <source src="@/assets/music/Christmas.mp3" type="audio/mpeg">
    <source src="@/assets/music/Christmas.mp3" type="audio/mp3" />
    <source src="@/assets/music/Christmas.mp3" type="audio/ogg" />
  </audio> -->

  <!-- <audio src="@/assets/music/Christmas.mp3" type="audio/mpeg" controls></audio> -->

  <img
    class="music_santa"
    src="@/assets/music/music-play-santa.gif"
    alt=""
    @click="playAudio"
  />

  <!-- <div>
    <button @click="togglePlay">
      {{ isPlaying ? "Stop" : "Play" }}
    </button>
  </div>

  <img src="@/assets/music/music-play-santa.gif" alt="" /> -->
</template>


<style scoped>
/** 음악  */
.music_santa {
  position: fixed;
  top: 50px;
  left: 110px;
  width: 120px;
  height: 90px;
  cursor: pointer;
  z-index: 999;
  display: none; /** 나중에 풀기 */
}

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

.home_btn_rudol {
  width: 38px;
  height: 38px;
  z-index: 999;
}

.home-button {
  /* position: absolute; */
  position: fixed;
  width: 55px;
  height: 55px;
  transform: translate(50px, -35px); /** 홈버튼 위치 */
  /* display: inline-block; */
  /* padding: 15px 15px; */
  /* font-size: 16px; */
  /* font-weight: bold; */
  /* text-align: center; */
  /* text-decoration: none; */
  border-radius: 100%;
  background-color: rgba(255, 157, 0, 0.835);
  color: #ffffff;
  cursor: pointer;
  /* position: relative; */
  overflow: hidden;
  border: none;
  transition: transform 0.3s, box-shadow 0.3s;
  /* transform: translateY(-3px); */
  box-shadow: 4px 4px 13px 4px rgba(255, 255, 255, 0.9);
  z-index: 999;
}

.home-button:hover {
  /* transform: translateY(-3px); */
  transform: translate(50px, -35px);
  /* box-shadow: 0 5px 15px rgba(255, 255, 255, 0.9); */
}

.home-button:active {
  /* transform: translateY(1px); */
  transform: translate(53px, -30px);
  box-shadow: 0 3px 12px rgba(170, 170, 170, 0.9);
}

/** 로그아웃 */
.logout-button {
  display: inline-block;
  position: fixed;
  top: 20px;
  right: 30px;
  padding: 6px 10px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  border-radius: 12px;
  background-color: rgba(0, 0, 0, 0.089);
  color: #ffffff;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  border: none;
  /* margin-left: 10px; 로그아웃 버튼과의 간격 조정 */
  z-index: 999;
}

.logout-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
}

.logout-button:active {
  transform: translateY(1px);
  box-shadow: 0 3px 10px rgba(255, 255, 255, 0.5);
}
</style>
