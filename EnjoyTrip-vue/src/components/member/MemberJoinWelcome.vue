<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

const router = useRouter();

const moveLogin = () => {
  router.push({ name: "member-login" });
};

onMounted(() => {
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 800,
    top: 0,
    behavior: "smooth",
  });
});
</script>

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
          @click="moveLogin"
        />
      </div>
      <div class="welcome_content">
        <div class="text">
          <p>환영합니다!</p>
          <p>우리 회원이 되어주셔서 감사합니다.</p>
          <p>즐거운 시간 되시길 바랍니다.</p>
        </div>

        <div class="text">
          <p>Welcome!</p>
          <p>Thank you for being our member</p>
          <p>Enjoy your Trip</p>
        </div>
        <div class="text">
          <p>ようこそ!</p>
          <p>私たちのメンバーになってくれてありがとう。</p>
          <p>楽しい時間になりますように。</p>
        </div>
        <div class="text">
          <p>accueillir!</p>
          <p>Merci d'être notre membre</p>
          <p>Passez un bon moment</p>
        </div>
        <div class="text">
          <p>欢迎!</p>
          <p>感谢您成为我们的会员。</p>
          <p>祝你玩得开心。</p>
        </div>
        <div class="text">
          <p>bienvenido!</p>
          <p>Gracias por ser nuestro miembro</p>
          <p>Pasar un buen rato</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.text {
  margin-top: 13px;
}

.welcome_content {
  position: absolute;
  transform: translate(565px, 380px);
  width: 300px;
  height: 130px;
  /* background-color: rgba(255, 255, 255, 0.123); */
  border: none;
  border-radius: 5%;
  margin: auto;
  color: rgb(0, 0, 0);
  text-align: center;
  align-content: center;
  padding: 5px;
  font-size: 19px;
}

.welcome_content p {
  margin: auto;
  padding-top: 5px;
}

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
