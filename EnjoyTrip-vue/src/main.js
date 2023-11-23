// import './assets/main.css'

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import socket from 'vue3-websocket'
const { VITE_VUE_API_URL_SOCKET } = import.meta.env;
import App from "./App.vue";
import router from "./router";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.use(pinia);
// app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);
app.use(socket, VITE_VUE_API_URL_SOCKET);

app.mount("#app");
