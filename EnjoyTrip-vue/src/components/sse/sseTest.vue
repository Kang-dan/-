<template>
    <div>
      <SSEComponent />
      <button @click="sendMessage">Send Message</button>
    </div>
  </template>
  
  <script>
  import SSEComponent from "@/components/sse/sse.vue"; // 경로를 실제 프로젝트 구조에 맞게 수정
  
  export default {
    components: {
      SSEComponent,
    },
    methods: {
      sendMessage() {
        // 서버에 메시지를 보낼 수 있는 API 호출
        fetch("http://localhost:80/sse/broadcast?message=Hello%20from%20Vue");
      },
    },
  };
  </script>