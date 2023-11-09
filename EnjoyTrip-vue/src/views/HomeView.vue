<script setup>
const left = [0, 280, 360, 430, 510, 570, 430, 300, 360, 210, 260, 170, 330, 450, 500, 620, 670, 410];
const top = [0, 320, 260, 350, 300, 550, 500, 530, 620, 570, 750, 810, 830, 800, 700, 750, 830, 920];
const imgTitle = ['', '인천', '서울', '경기', '강원', '경북', '충북', '세종', '대전', '충남', '전북', '광주', '전남', '경남', '대구', '울산', '부산', '제주'];
</script>
<template>
  <div id="treeMap">    
    <div id="treeParent">      
      <div id="star">
        <img id="TreeStar" src="../assets/TreeStar.png" alt="별" title="로그인" />
      </div>
      <img id="treeImg" src="../assets/treeMap.png" alt="트리지도" title="전구 위에 마우스를 올려주세요" />      
      <span id="treeBalls">
        <!-- 라우터 링크 달기 <RouterLink :to=""}>이미지</RouterLink>-->
        <!-- 전구(도시)17개:relative -->
        <img
          v-for="n in 17"
          :id="`ball${n}`" 
          :src="`src/assets/cityLight/treeBall${Math.floor(Math.random() * 5) + 1}.png`" 
          :alt="n"
          :title="imgTitle[n]"
          :style="`position: absolute; width: 130px; left: ${left[n]}px; top: ${top[n]}px; z-index: 3`"
          />
      </span>
      <span id="present">
        <!-- 선물상자(마이페이지) -->
        <img id="PresentCloseGreen" src="../assets/PresentCloseGreen.png" alt="선물상자" />
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
  width: 320px;
  /* margin-left: 100px; */
}

#TreeStar {
  margin-top: 50px;
  width: 200px;
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
