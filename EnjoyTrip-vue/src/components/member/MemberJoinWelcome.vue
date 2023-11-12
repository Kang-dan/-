<script setup></script>

<template>
  <div class="join_welcome_img">
    <div id="welcome">
      <div id="loginWrite">
        <form>
          <h1>Click me Login</h1>
        </form>
        <img
          id="WelcomeKoreaFlower"
          src="@/assets/join/WelcomeKoreaFlower.png"
          alt="무궁화"
          title="무궁화"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
h1 {
  color: white;
}

.join_welcome_img {
  background-image: url("@/assets/join/WelcomeKorea.png");
  width: 1400px;
  height: 1400px;
  background-size: cover;
  text-align: center;
  margin: 0 auto;
  /* vertical-align: middle; */
}

#loginWrite {
  position: absolute;
  transform: translate(300px, -50px);
  margin-right: 550px;
}

#welcome {
  height: 1500px;
  margin: auto;
}

#WelcomeKoreaFlower {
  width: 200px;
  transition: all ease 1s;
  padding-top: 0;
}

#WelcomeKoreaFlower:hover {
  /* transform: scale(1.5);
  -webkit-transform: scale(1.5); */
  -ms-transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
  transform: rotate(360deg);
}
</style>
