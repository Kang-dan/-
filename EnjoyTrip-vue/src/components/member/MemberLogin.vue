<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const moveJoin = () => {
  router.push({ name: "member-join" });
};
</script>

<template>
  <div id="login">
    <div class="login_moon_img" title="환영합니다">
      <div id="loginWrite">
        <form>
          <h1>Login</h1>
          <input type="text" placeholder="아이디를 입력해주세요" />
          <input type="password" placeholder="비밀번호를 입력해주세요" />
          <button class="form_btn1">Sign IN</button>
          <button class="form_btn2" @click="moveJoin">Sign UP</button>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login_moon_img {
  background-image: url("@/assets/login/login_moon.png");
  width: 1400px;
  height: 1400px;
  background-size: cover;
  text-align: center;
  margin: 0 auto;
  /* vertical-align: middle; */
}

#login {
  height: 1500px;
  margin: auto;
}

#TreeStar:hover {
  transform: scale(1.5);
  -webkit-transform: scale(1.5);
  /* align-items: center; */
}

body {
  background: linear-gradient(to bottom, #000118, #002c5a);
  /* min-height: 100vh; */
  margin: 0;
  padding: 0;
  /* overflow: hidden;  스크롤 여부 */
  display: flex;
  font-family: sans-serif;
}

.loginWrite {
  /* margin: 0 auto; */
  text-align: center;
  vertical-align: middle;
}

form {
  padding-top: 35%;
}

form input {
  display: block;
  background: #eee;
  padding: 20px;
  margin: 8px auto;
  width: 30%;
  border: 0;
  outline: none;
  border-radius: 20px;
  box-shadow: inset 7px 2px 10px #babebc, inset -5px -5px 12px #fff;
  font-size: medium;
}

button {
  display: block;
  font-size: 20px;
  font-weight: bold;
  font-size: larger;
  border-radius: 20px;
  border: none;
  outline: none;
  padding: 15px 45px;
  margin: 45px auto;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: transform 80ms ease-in;
  background-color: #babebc;
  color: white;
}

button:hover {
  background-color: #dede7c;
  transition: 0.7s;
}

.form_btn1 {
  box-shadow: -5px -5px 10px #fff, 5px 5px 8px #babebc;
}

.form_btn2 {
  box-shadow: 1px 1px 2px #babebc, -1px -1px 2px #ffffff;
}

.sign-in-container {
  /* position: absolute; */
  left: 0;
  width: 50%;
  height: 100%;
  transition: all 0.5s;
}

form h1 {
  margin-bottom: 50px;
  font-size: 50px;
  font-weight: bold;
  font-style: italic;
  color: rgb(191, 179, 72);
}
</style>
