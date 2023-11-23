import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/member"

async function loginMember(member, success, fail) {
    await local.post(`${url}/login`, JSON.stringify(member)).then(success).catch(fail);
}

function registMember(member, success, fail) {    
    local.post(`${url}/regist`, JSON.stringify(member)).then(success).catch(fail);
}

async function updateMember(member, success, fail) {
  await local.put(`${url}/update`, JSON.stringify(member)).then(success).catch(fail);
}

async function deleteMember(memberId, success, fail) {
  await local.delete(`${url}/${memberId}`).then(success).catch(fail);
}

async function findById(memberid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`${url}/info/${memberid}`).then(success).catch(fail);
}

async function tokenRegeneration(member, success, fail) {
  local.defaults.headers["refreshToken"] = localStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`${url}/refresh`, member).then(success).catch(fail);
}

async function logout(memberid, success, fail) {
  await local.get(`${url}/logout/${memberid}`).then(success).catch(fail);
}

async function idCheck(member, success, fail) {
  await local.post(`${url}/idCheck`, JSON.stringify(member)).then(success).catch(fail);
}

async function likeInsert(like, success, fail) {
  await local.post(`${url}/like/insert`, JSON.stringify(like)).then(success).catch(fail);
}

async function likeList(memberInfo, success, fail) {
  await local.post(`${url}/like`, JSON.stringify(memberInfo)).then(success).catch(fail);
}

async function likeDelete(like, success, fail) {
  await local.post(`${url}/like/delete`, JSON.stringify(like)).then(success).catch(fail);
}

async function loveInsert(love, success, fail) {
  await local.post(`${url}/love/insert`, JSON.stringify(love)).then(success).catch(fail);
}

async function loveListOne(love, success, fail) {
  await local.post(`${url}/love/list`, JSON.stringify(love)).then(success).catch(fail);
}

async function loveList(memberNo, success, fail) {
  await local.get(`${url}/love/list/${memberNo}`).then(success).catch(fail);
}

async function loveDelete(love, success, fail) {
  await local.post(`${url}/love/delete`, JSON.stringify(love)).then(success).catch(fail);
}

async function loveDeleteAll(memberNo, success, fail) {
  await local.delete(`${url}/love/delete/${memberNo}`).then(success).catch(fail);
}

export {
  loginMember,
  registMember,
  findById,
  tokenRegeneration,
  logout,
  likeInsert,
  likeList,
  likeDelete,
  idCheck,
  updateMember,
  deleteMember,
  loveInsert,
  loveDeleteAll,
  loveDelete,
  loveList,
  loveListOne,
};