<script setup>
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);

const router = useRouter();

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
</script>
<template>
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

        <img
          v-for="n in 17"
          :id="`ball${n}`"
          :src="`src/assets/cityLight/treeBall${
            Math.floor(Math.random() * 5) + 1
          }.png`"
          :alt="n"
          :title="imgTitle[n]"
          :style="`position: absolute; width: 130px; left: ${left[n]}px; top: ${top[n]}px; z-index: 3`"
          @click="moveAttractionList(sidoCode[n])"
        />
      </span>
      <span id="present">
        <!-- 선물상자(마이페이지) -->
        <img
          id="PresentCloseGreen"
          src="../assets/PresentCloseGreen.png"
          alt="선물상자"
        />
        <img id="PresentOpenGreen" src="../assets/PresentOpen.png" />
      </span>
    </div>
  </div>
</template>

<style scoped>
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

#present {
  position: absolute;
  left: 850px;
  width: 100px;
  margin-top: 1010px;
  z-index: 4;
}

#PresentCloseGreen {
  margin-left: 10px;
  margin-top: 25px;
  width: 320px;
  /* margin-left: 100px; */
}

#PresentOpenGreen {
  width: 320px;
  display: none;
}

#present:hover #PresentOpenGreen {
  display: block;
}

#present:hover #PresentCloseGreen {
  display: none;
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
