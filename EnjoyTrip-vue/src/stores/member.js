import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { loginMember, findById, tokenRegeneration, logout } from "@/api/member";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const memberInfo = ref(null);
  const isValidToken = ref(false);

  const memberLogin = async (member) => {
    await loginMember(
      member,
      (response) => {
        // console.log("login ok!!!!", response.status);
        // console.log("login ok!!!!", httpStatusCode.CREATE);
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          // console.log("data", data);
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];
          console.log("accessToken", accessToken);
          console.log("refreshToken", refreshToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          localStorage.setItem("refreshToken", refreshToken);
          console.log("sessiontStorage에 담았다", isLogin.value);
        } else {
          console.log("로그인 실패했다");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.log("로그인 실패 에러");
        console.log(error);
      }
    );
  };

  const getMemberInfo = (token) => {
    let decodeToken = jwtDecode(token);
    console.log("2. decodeToken", decodeToken);
    findById(
      decodeToken.memberId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          memberInfo.value = response.data.memberInfo;
          console.log("3. getMemberInfo data >> ", response.data);
        } else {
          console.log("멤버 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error(
          "getMemberInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status
        );
        isValidToken.value = false;

        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
    await tokenRegeneration(
      JSON.stringify(memberInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"];
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            memberInfo.value.memberid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              memberInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "member-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              memberInfo.value = null;
            }
          );
        }
      }
    );
  };

  const memberLogout = async (memberid) => {
    await logout(
      memberid,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          memberInfo.value = null;
          isValidToken.value = false;
          sessionStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
        } else {
          console.error("멤버 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    memberInfo,
    isValidToken,
    memberLogin,
    getMemberInfo,
    tokenRegenerate,
    memberLogout,
  };
}, { persist: { storage: sessionStorage } }
);
