<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();

const moveAttractionDetail = () => {
  router.push({ name: "attraction-detail" }); //contentId로
};
</script>

<template>
  <div id="trip">
    <div id="star">
      <div class="snowBall_img" alt="스노우볼">
        <img
          id="TripFoodButton"
          src="@/assets/cityAttraction/TripFoodButton.png"
          alt=""
        />
        <img
          id="TripFestivalButton"
          src="@/assets/cityAttraction/TripFestivalButton.png"
          alt=""
        />
        <img
          id="TripChildHatButton"
          src="@/assets/cityAttraction/TripChildHatButton.png"
          alt=""
        />

        <select class="header-searchSelect">
          <option value="All">All</option>
          <option value="arts-craft">가게명</option>
          <option value="address">위치</option>
          <option value="menu">메뉴</option>
        </select>
        <div class="search-container">
          <input
            type="text"
            class="search-input"
            placeholder="검색어를 입력하세요"
          />
          <!-- 버튼에 @click="" 추가하기-->
          <button class="search-button" type="button">검색</button>
        </div>

        <div class="img0">
          <img
            id="TripSnowButton0"
            src="@/assets/cityAttraction/TripListAllButtonImg.png"
            alt=""
          />
          <div class="TripSnowButton0_text">
            <h3>All</h3>
          </div>
        </div>

        <div class="img1">
          <div class="spotLight1"></div>
          <img
            id="TripSnowButton1"
            src="@/assets/cityAttraction/TripSnowButton.png"
            alt=""
          />
          <div class="TripSnowButton1_text">
            <h3>Food</h3>
          </div>
        </div>

        <div class="img2">
          <img
            id="TripSnowButton2"
            src="@/assets/cityAttraction/TripSnowButton.png"
            alt=""
          />
          <div class="TripSnowButton2_text">
            <h3>Festival</h3>
          </div>
        </div>

        <div class="img3">
          <img
            id="TripSnowButton3"
            src="@/assets/cityAttraction/TripSnowButton.png"
            alt=""
          />
          <div class="TripSnowButton3_text">
            <h3>Family</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  background-color: #f4f4f4;
} */

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
