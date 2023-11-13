<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance
const { VITE_ATTRACTION_FESTIVAL_URL } = import.meta.env;
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const festivals = ref([]);

onMounted(() => {
    getAttractionFestival();
});

const getAttractionFestival = () => {
    axios
        .get(VITE_ATTRACTION_FESTIVAL_URL, {
        params: {
            serviceKey: VITE_OPEN_API_SERVICE_KEY,
            numOfRows: 100,
            pageNo: 1,
            eventStartDate: 20231123,
            _type: "json",
            MobileOS: "ETC",
            MobileApp: "enjoytrip"
        }
        })
        .then(({ data }) => {
            festivals.value = data.response.body.items.item;
        })
        .catch((err) => {
            console.log(err);
        });
};

const postAttractionFestival = () => {    
    local.post(`/parsing`, JSON.stringify(festivals.value))
        .then(() => { console.log("성공") })
        .catch((err) => { console.log(err) });
}


</script>

<template>
    <div>
        <ul>
            <li>{{ festivals[0] }}</li>
        </ul>
    </div>
    <button @click.prevent="postAttractionFestival">보내기</button>
</template>

<style scoped>

</style>