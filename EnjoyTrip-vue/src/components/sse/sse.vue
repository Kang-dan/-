<template>
    <div>
      <p v-if="message">{{ message }}</p>
      <p v-else>No data received yet.</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        message: null,
        eventSource: null,
      };
    },
    mounted() {
      this.initializeSSE();
    },
    beforeUnmount() {
      this.closeSSE();
    },
    methods: {
      initializeSSE() {
        this.eventSource = new EventSource("http://localhost:80/sse/connect");
  
        this.eventSource.onmessage = (event) => {
          this.handleSSEMessage(event);
        };
  
        this.eventSource.onerror = (event) => {
          this.handleSSEError(event);
        };
      },
      handleSSEMessage(event) {
            this.message = event.data;
            console.log("뭐지이거");
        // 원하는 동작 수행
      },
      handleSSEError(event) {
        console.error("SSE Error:", event);
        // 원하는 오류 처리 수행
      },
      closeSSE() {
        if (this.eventSource) {
          this.eventSource.close();
        }
      },
    },
  };
  </script>