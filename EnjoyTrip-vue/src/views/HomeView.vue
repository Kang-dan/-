<script setup></script>
<template>
  <div id="treeMap">
    <div id="star">
      <img id="TreeStar" src="./TreeStar.png" alt="별" title="로그인" />
    </div>
    <img id="treeImg" src="./treeMap.png" alt="트리지도" title="전구 위에 마우스를 올려주세요" />
    <span id="treeBalls">
      <!-- 라우터 링크 달기 <RouterLink :to=""}>이미지</RouterLink>-->
      <img
        id="ball1"
        src="./cityLight/treeBall.png"
        alt="1"
        title="인천"
        style="position: absolute; width: 100px; left: 280px; top: 320px; z-index: 3"
      />
      <img
        id="ball2"
        src="./cityLight/treeBall2.png"
        alt="2"
        title="서울"
        style="position: absolute; width: 100px; left: 360px; top: 260px; z-index: 3"
      />
      <img
        id=" ball3"
        src="./cityLight/treeBall3.png"
        alt="3"
        title="경기"
        style="position: absolute; width: 100px; left: 430px; top: 350px; z-index: 3"
      />
      <img
        id="ball4"
        src="./cityLight/treeBall4.png"
        alt="4"
        title="강원"
        style="position: absolute; width: 100px; left: 510px; top: 300px; z-index: 3"
      />
      <img
        id="ball5"
        src="./cityLight/treeBall.png"
        alt="5"
        title="경북"
        style="position: absolute; width: 100px; left: 570px; top: 550px; z-index: 3"
      />
      <img
        id="ball6"
        src="./cityLight/treeBall2.png"
        alt="6"
        title="충북"
        style="position: absolute; width: 100px; left: 430px; top: 500px; z-index: 3"
      />
      <img
        id="ball7"
        src="./cityLight/treeBall.png"
        alt="7"
        title="세종"
        style="position: absolute; width: 100px; left: 300px; top: 530px; z-index: 3"
      />
      <img
        id="ball8"
        src="./cityLight/treeBall3.png"
        alt="8"
        title="대전"
        style="position: absolute; width: 100px; left: 360px; top: 620px; z-index: 3"
      />
      <img
        id="ball9"
        src="./cityLight/treeBall4.png"
        alt="9"
        title="충남"
        style="position: absolute; width: 100px; left: 210px; top: 570px; z-index: 3"
      />
      <img
        id="ball10"
        src="./cityLight/treeBall5.png"
        alt="10"
        title="전북"
        style="position: absolute; width: 100px; left: 260px; top: 750px; z-index: 3"
      />
      <img
        id="ball11"
        src="./cityLight/treeBall.png"
        alt="11"
        title="광주"
        style="position: absolute; width: 100px; left: 170px; top: 810px; z-index: 3"
      />
      <img
        id="ball12"
        src="./cityLight/treeBall3.png"
        alt="12"
        title="전남"
        style="position: absolute; width: 100px; left: 330px; top: 830px; z-index: 3"
      />
      <img
        id="ball13"
        src="./cityLight/treeBall.png"
        alt="13"
        title="경남"
        style="position: absolute; width: 100px; left: 450px; top: 800px; z-index: 3"
      />
      <img
        id="ball14"
        src="./cityLight/treeBall5.png"
        alt="14"
        title="대구"
        style="position: absolute; width: 100px; left: 500px; top: 700px; z-index: 3"
      />
      <img
        id="ball15"
        src="./cityLight/treeBall4.png"
        alt="15"
        title="울산"
        style="position: absolute; width: 100px; left: 620px; top: 750px; z-index: 3"
      />
      <img
        id="ball16"
        src="./cityLight/treeBall2.png"
        alt="16"
        title="부산"
        style="position: absolute; width: 100px; left: 670px; top: 830px; z-index: 3"
      />
      <img
        id="ball17"
        src="./cityLight/treeBall2.png"
        alt="17"
        title="제주"
        style="position: absolute; width: 100px; left: 410px; top: 920px; z-index: 3"
      />
    </span>

    <!-- 전구(도시)17개:relative -->
    <span id="present">
      <!-- 선물상자(마이페이지) -->
      <img id="PresentCloseGreen" src="./PresentCloseGreen.png" alt="선물상자" />
    </span>
  </div>
</template>

<style scoped>
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
  left: 780px;
  width: 100px;
  margin-top: 880px;
}

#PresentCloseGreen {
  width: 250px;
  /* margin-left: 100px; */
}

#TreeStar {
  margin-top: 50px;
  width: 150px;
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
  position: relative;
  /* display: block; */
  /* position: relative; */
  /* top: 0px; */
  /* left: 0px; */
  width: 1000px;
  height: 2000px;
  margin-left: 100px;
  z-index: 1;
  /* border: 1px solid; */
  margin: auto;
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
}

/* 
        #embedImSnowInfo {
            position: fixed;
            top: 0;
            left: 0;
            width: 300px;
            padding: 20px;
            display: none;
            background: #fff;
            height: 100vh;
            overflow-y: auto;
            padding-top: 70px;
            z-index: 10;
            box-shadow: 10px 10px 25px -10px rgba(0, 0, 0, 0.5);
        } */

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
