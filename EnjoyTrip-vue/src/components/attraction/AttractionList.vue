<script setup>
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
        <img id="TripFoodButton" src="@/assets/cityAttraction/TripFoodButton.png" alt="" />
        <img id="TripFestivalButton" src="@/assets/cityAttraction/TripFestivalButton.png" alt="" />
        <img id="TripChildHatButton" src="@/assets/cityAttraction/TripChildHatButton.png" alt="" />

        <img id="TripSnowButton1" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
        <img id="TripSnowButton2" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
        <img id="TripSnowButton3" src="@/assets/cityAttraction/TripSnowButton.png" alt="" />
      </div>
    </div>
  </div>
</template>

<style scoped>
#TripFoodButton {
  position: absolute;
  transform: translate(-260px, 65px);
  width: 120px;
  z-index: 2;
  /* display: none; */
  /* visibility: hidden; */
}

#TripFestivalButton {
  position: absolute;
  transform: translate(-50px, 20px);
  width: 100px;
  z-index: 2;
  /* display: none; */
}

#TripChildHatButton {
  position: absolute;
  transform: translate(135px, 85px);
  width: 130px;
  z-index: 2;
  /* display: none; */
}

#TripSnowButton1 {
  position: absolute;
  transform: translate(-250px, 150px);
  width: 100px;
}

#TripSnowButton2 {
  position: absolute;
  transform: translate(-50px, 100px);
  width: 100px;
}

#TripSnowButton3 {
  position: absolute;
  transform: translate(150px, 150px);
  width: 100px;
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
