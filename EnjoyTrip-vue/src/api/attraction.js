import { localAxios, attractionIntroAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance
const intro = attractionIntroAxios();

const url = "/attraction";

const listAttraction = async (attraction, success, fail) => {
  await local.post(`${url}`, JSON.stringify(attraction)).then(success).catch(fail);
}

const detailAttraction = async (attraction, success, fail) => {
  await local.post(`${url}/detail`, JSON.stringify(attraction)).then(success).catch(fail);
}

const detailIntroAttraction = async (introParsingData, success, fail) => {
  await intro.get("", { params: introParsingData }).then(success).catch(fail);
}

// function listAttraction(attraction, success, fail) {
//   local.post(`${url}`, JSON.stringify(attraction)).then(success).catch(fail);
// }
export { listAttraction, detailAttraction, detailIntroAttraction };
