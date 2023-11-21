import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/board"

async function boardList(sidoCode, success, fail) {
    await local.get(`${url}/list/${sidoCode}`).then(success).catch(fail);
}
async function boardWrite(board, success, fail) {
    await local.post(`${url}/write`, JSON.stringify(board)).then(success).catch(fail);
}
async function boardView(no, success, fail) {
    await local.get(`${url}/view/${no}`).then(success).catch(fail);
}
async function boardDelete(no, success, fail) {
    await local.delete(`${url}/delete/${no}`).then(success).catch(fail);
}
async function boardUpdate(board, success, fail) {
    await local.post(`${url}/update`, JSON.stringify(board)).then(success).catch(fail);
}
async function boardUpdateHit(no, success, fail) {
    await local.get(`${url}/update/hit/${no}`).then(success).catch(fail);
}
async function boardUpdateLove(map, success, fail) {
    await local.post(`${url}/update/love`, JSON.stringify(map)).then(success).catch(fail);
}
async function boardDeleteMember(memberId, success, fail) {
    await local.delete(`${url}/${memberId}`).then(success).catch(fail);
}

export {
    boardList,
    boardWrite,
    boardView,
    boardDelete,
    boardUpdate,
    boardUpdateHit,
    boardUpdateLove,
    boardDeleteMember,

};