<script setup>
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

/** music */
const play = (sound) => {
  if (sound) {
    const audio = new Audio(sound);
    audio.play();
  }
};

import { ref } from "vue";

const isPlaying = ref(false);

const togglePlay = () => {
  isPlaying.value = !isPlaying.value;

  const sound =
    "http://soundbible.com/mp3/Air Plane Ding-SoundBible.com-496729130.mp3";

  if (isPlaying.value) {
    const audio = new Audio(sound);
    audio.play();
  } else {
    // 멈춤 기능은 여기에 추가 (현재는 토글만 구현되어 있습니다)
  }
};
</script>

<template>
  <button class="home-button" @click="headerMain()">홈</button>

  <button v-if="isLogin" @click.prevent="logout()" class="logout-button">
    로그아웃
  </button>

  <div>
    <button @click="togglePlay">
      {{ isPlaying ? "Stop" : "Play" }}
    </button>
  </div>

  <img src="@/assets/music/music_santa.gif" alt="" />
</template>

<style scoped>
img {
  width: 100px;
  height: 100px;
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

.home-button {
  /* position: absolute; */
  position: fixed;
  width: 55px;
  height: 55px;
  transform: translate(50px, -35px); /** 홈버튼 위치 */
  /* display: inline-block; */
  padding: 15px 15px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  border-radius: 100%;
  background-color: rgba(255, 25, 0, 0.9);
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

.logout-button {
  display: inline-block;
  position: fixed;
  top: 20px;
  right: 30px;
  padding: 10px 10px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  border-radius: 12px;
  background-color: rgba(231, 76, 60, 0.2);
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
