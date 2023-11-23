<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { idCheck, updateMember, deleteMember, loveList, loveDeleteAll } from "@/api/member";
import { boardDeleteMember, boardUpdateLoveAll } from "@/api/board";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { memberInfo, getMemberInfo, memberLogout } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const isModalOpen = ref(false);

const member = ref({
  memberNo: memberInfo.memberNo,
  memberId: memberInfo.memberId,
  memberPw: "",
  memberName: memberInfo.memberName,
  memberEmail: memberInfo.memberEmail,
  age: memberInfo.age,
});
const updateBtn = ref(true);

const closeModal = () => {
  isModalOpen.value = false;
  // 모달을 닫을 때 show 클래스 제거
  const modal = document.querySelector("#modal.modal-overlay");
  if (modal) {
    modal.classList.remove("show");
  }
};
/** 모달창(디테일) 테스트 끝 */
const infoChange = (command) => {
  const name = document.querySelector("#name");
  const email = document.querySelector("#email");
  const age = document.querySelector("#age");
  if (command) {
    // true인 경우
    name.classList.add("inputState");
    name.disabled = false;
    email.classList.add("inputState");
    email.disabled = false;
    age.classList.add("inputState");
    age.disabled = false;
    updateBtn.value = false;
  } else {
    name.classList.remove("inputState");
    name.disabled = true;
    email.classList.remove("inputState");
    email.disabled = true;
    age.classList.remove("inputState");
    age.disabled = true;
    updateBtn.value = true;
  }
};

const passwordCheck = (action) => {
  idCheck(
    member.value,
    ({ data }) => {
      if (data === "성공") {
        console.log("비밀번호 체크 성공함");
        if (action === "update") {
          infoChange(true);
        } else if (action === "delete") infoDelete();
      } else {
        console.log("비밀번호 체크 실패함");
        confirm("비밀번호가 맞지 않습니다.");
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const infoDelete = () => {
  if (confirm("기존 데이터는 모두 사라지게 됩니다. 회원 탈퇴를 원하시나요? ")) {
    console.log("yes");
    memberLogout(member.value.memberId);
    loveList(
      member.value.memberNo,
      ({ data }) => {
        boardUpdateLoveAll(
          { boardNo: data },
          ({data}) => {
            console.log("좋아요 전부 취소 완료");
          },
          (err) => {
            console.log(err);
          }
        );        
      }, 
      (err) => { 
        console.log(err);
      }
    );
    loveDeleteAll(
      member.value.memberNo,
      ({data}) => {
        console.log("좋아요 기록 삭제");
      },
      (err) => {
        console.log(err);
      }
    );
    boardDeleteMember(
      member.value.memberId,
      ({ data }) => {
        console.log(data);
        console.log("게시글 삭제 완료");
      },
      (err) => {
        console.log(err);
      }
    );
    deleteMember(
      member.value.memberId,
      ({ data }) => {
        console.log(data);
        console.log("아이디 삭제 완료");
      },
      (err) => {
        console.log(err);
      }
    );

    closeModal();
  } else console.log("no");
};

const infoUpdate = () => {
  console.log("업데이트 시도");
  updateMember(
    member.value,
    ({ data }) => {
      let token = sessionStorage.getItem("accessToken");
      getMemberInfo(token);
      console.log("업데이트 성공");
      confirm("회원정보가 수정되었습니다.");
      infoChange(false);
      member.value.memberPw = "";
    },
    (err) => {
      console.log(err);
    }
  );
};
</script>

<template>
  <div id="modal" class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="close-area" @click="closeModal">X</div>
      <div class="title">
        <h1>" Life is full of possibilities "</h1>
        <h2>인생은 가능성으로 차 있어 - 소울-</h2>
      </div>
      <div class="content">
        <img
          class="mypage_info_photo_img"
          src="@/assets/mypage/mypage_info_photo.png"
          alt=""
        />
        <img
          class="mypage_winter_children_photo"
          src="@/assets/mypage/mypage_winter_children_photo.gif"
          alt=""
        />
        <h3>
          <input
            class="photo_joinDate"
            type="text"
            disabled
            :value="memberInfo.joinDate"
          />
        </h3>
        <div class="mypage_member_info">
          <form @submit.prevent="infoUpdate">
            <h3>
              아이디
              <input
                id="id"
                class="inputInfo"
                type="text"
                disabled
                :value="memberInfo.memberId"
              />
            </h3>
            <h3>
              비밀번호
              <input
                id="pw"
                class="inputInfo inputState"
                type="password"
                v-model="member.memberPw"
                placeholder="수정 또는 탈퇴시 비밀번호 입력"
              />
            </h3>
            <h3>
              이름
              <input
                id="name"
                class="inputInfo"
                type="text"
                disabled
                v-model="member.memberName"
              />
            </h3>
            <h3>
              이메일
              <input
                id="email"
                class="inputInfo"
                type="text"
                disabled
                v-model="member.memberEmail"
              />
            </h3>
            <h3>
              나이
              <input
                id="age"
                class="inputInfo"
                type="number"
                disabled
                v-model="member.age"
              />
            </h3>
            <h3>
              우리 회원이 된 날
              <input
                class="inputInfo"
                type="text"
                disabled
                :value="memberInfo.joinDate"
              />
            </h3>
            <button
              id="pwCheck_btn"
              @click.prevent="passwordCheck('update')"
              v-show="updateBtn"
            >
              비밀번호확인
            </button>
            <button id="update_btn" v-show="!updateBtn">수정하기</button>
            <button id="remove_btn" @click.prevent="passwordCheck('delete')">
              탈퇴하기
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/** 회원정보 띄우는 쪽 예쁘게 */
.photo_joinDate {
  position: absolute;
  transform: translate(-350px, 430px);
  color: rgb(0, 0, 0);
  font-size: 19px;
  border: none;
  text-align: center;
  font-family: UhBeeJJIBBABBA; 
  background-color: rgba(51, 51, 51, 0);
}

@font-face {
  font-family: "UhBeeJJIBBABBA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_five@.2.0/UhBeeJJIBBABBA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

.inputInfo {
  border-radius: 5%;
  background-color: rgba(127, 143, 128, 0.37);
  color: rgb(255, 255, 255);
  height: 25px;
  width: 170px;
  position: absolute;
  transform: translateX(650px);
  left: 5px;
  font-size: 16px;
}
.inputState {
  background-color: rgb(0, 0, 0);
}

.mypage_member_info button {
  /* 생략 */
  /* box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06); */
  background-color: rgba(255, 255, 255, 0.993);
  cursor: pointer;
  border: none;
  border-radius: 5%;
  /* margin: 30px 30px; */
  outline: none;
  margin-top: 40px;
  margin-left: 55px;
  padding: 10px;
}

button:disabled {
  opacity: 0.5;
}

/** 마이페이지 (사진) */
.mypage_member_info {
  margin: 50px 20px;
}
.mypage_info_photo_img {
  padding-left: 30px;
  /* visibility: hidden; */
}

.mypage_winter_children_photo {
  position: absolute;
  /* transform: translate(-410px, 100px); */
  transform: translate(69px, 100px);
  width: 365px;
  /* display: none; */
  /* visibility: hidden; */
}

#modal .modal-window {
  overflow-y: auto; /* 내용이 창을 벗어날 경우 스크롤 추가 */
  max-height: 85vh; /* 모달창의 최대 높이를 지정하여 화면을 넘어가지 않도록 함 */
}

#modal.modal-overlay {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(1.5px);
  -webkit-backdrop-filter: blur(1.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  /** 모달창 부드럽게 보이기 */
  opacity: 0;
  pointer-events: none;
  transition: opacity 1s ease, transform 1s ease;
  z-index: 999; /* 적절한 값으로 조절 */
}

#modal.modal-overlay.show {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%) scale(1); /* 이동 및 크기 변경 */
}

#modal .modal-window {
  background: rgba(0, 0, 0, 0.87);
  box-shadow: 0 8px 32px 0 rgba(171, 175, 235, 0.37);
  backdrop-filter: blur(13.5px);
  -webkit-backdrop-filter: blur(13.5px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  width: 900px;
  height: 700px;
  position: relative;
  padding: 10px;
}
#modal .title {
  /* display: inline; */
  padding-left: 250px;
  text-shadow: 1px 1px 2px gray;
  color: white;
}
#modal .title h2 {
  padding-left: 55px;
  /* position: absolute; */
  /* transform: translate(430px, 50%); */
  /* display: inline; */
}
#modal .close-area {
  display: inline;
  float: right;
  padding-right: 10px;
  cursor: pointer;
  text-shadow: 1px 1px 2px gray;
  color: white;
}

/** 모달창 예쁘게 */
#modal .content {
  margin-top: 20px;
  align-content: center;
  /* padding: 0px 10px; */
  text-shadow: 1px 1px 2px rgb(68, 68, 68);
  font-size: 20px;
  color: rgb(255, 255, 255);
  display: flex; /* Flexbox를 사용하여 이미지와 정보를 나란히 표시 !!!!!! (여기)*/
  /* justify-content: space-between; */
}
/** 모달창(디테일) 테스트 끝 */

/** 폰트 */

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
</style>
