<script setup>
import { ref, watch, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { memberLogin, getMemberInfo } = memberStore;

const router = useRouter();

const member = ref({
  memberId: "",
  memberPw: "",
});

const memberIdErrMsg = ref("");
const memberPwErrMsg = ref("");

onMounted(() => {
  // 페이지가 마운트되면 스크롤을 원하는 위치로 이동
  window.scrollTo({
    bottom: 0,
    top: 390,
    behavior: "smooth",
  });
  // window.scrollTo(0, scrollPosition);
});

watch(
  () => member.value.memberId,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      memberIdErrMsg.value = "아이디를 확인해 주세요!";
    } else memberIdErrMsg.value = "";
  },
  { immediate: true }
);

watch(
  () => member.value.memberPw,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 60) {
      memberPwErrMsg.value = "비밀번호를 확인해 주세요!";
    } else memberPwErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (memberIdErrMsg.value) {
    alert(memberIdErrMsg.value);
  } else if (memberPwErrMsg.value) {
    alert(memberPwErrMsg.value);
  } else {
    login();
  }
}

const login = async () => {
  await memberLogin(member.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin._value) {
    console.log("로그인 성공");
    getMemberInfo(token);
    moveMain();
  } else {
    alert("아이디 또는 비밀번호를 확인해 주셈");
  }
};

const moveMain = () => {
  router.replace({ name: "main" });
  window.scrollTo({
    bottom: document.body.scrollHeight - window.innerHeight,
    top: 0,
    behavior: "smooth",
  }); //로그인 성공하면 홈화면 맨 위로 보이도록 함
};

const moveJoin = () => {
  router.push({ name: "member-join" });
};

</script>

<template>
  <div id="login">
    <div class="login_moon_img" title="환영합니다">
      <div id="loginWrite">
        <form @submit.prevent="onSubmit">
          <h1>Login</h1>
          <input
            type="text"
            v-model="member.memberId"
            placeholder="아이디를 입력해주세요"
          />
          <input
            type="password"
            v-model="member.memberPw"
            placeholder="비밀번호를 입력해주세요"
          />
          <button type="submit" class="form_btn1">Sign IN</button>
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
