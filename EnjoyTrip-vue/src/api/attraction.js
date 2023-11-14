import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/attraction";

function listAttraction(attraction, success, fail) {
  local.post(`${url}`, JSON.stringify(attraction)).then(success).catch(fail);
}
export { listAttraction };
