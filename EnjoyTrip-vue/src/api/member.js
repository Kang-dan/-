import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/member"

function loginMember(member, success, fail) {
    local.post(`${url}/login`, JSON.stringify(member)).then(success).catch(fail);
}

function registMember(member, success, fail) {    
    local.post(`${url}/regist`, JSON.stringify(member)).then(success).catch(fail);
}

export {
    loginMember,
    registMember
};