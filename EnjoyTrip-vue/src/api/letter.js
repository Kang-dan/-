import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/letter"

async function letterList(memberNo, success, fail) {
    await local.get(`${url}/list/${memberNo}`).then(success).catch(fail);
}
async function letterWrite(letter, success, fail) {
    await local.post(`${url}/write`, JSON.stringify(letter)).then(success).catch(fail);
}
async function letterView(no, success, fail) {
    await local.get(`${url}/view/${no}`).then(success).catch(fail);
}
async function letterDelete(no, success, fail) {
    await local.delete(`${url}/delete/${no}`).then(success).catch(fail);
}
async function letterUpdate(letter, success, fail) {
    await local.post(`${url}/update`, JSON.stringify(letter)).then(success).catch(fail);
}
async function letterUpdateHit(no, success, fail) {
    await local.get(`${url}/update/hit/${no}`).then(success).catch(fail);
}

export {
    letterList,
    letterWrite,
    letterView,
    letterDelete,
    letterUpdate,
    letterUpdateHit,
};