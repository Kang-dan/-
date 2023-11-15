<script setup>
import { ref } from "vue";

const isVisible = ref(false);

const open = () => {
  isVisible.value = true;
};

const close = () => {
  isVisible.value = false;
};
</script>

<template>
  <teleport to="body">
    <div class="backdrop" v-if="isVisible">
      <div class="modal-container">
        <slot></slot>
      </div>
    </div>
  </teleport>
</template>

<style scoped>
.backdrop {
  z-index: 999;
  display: flex;
  position: fixed;
  align-items: center;
  justify-content: center;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
}

.modal-container {
  background: white;
  max-width: 320px;
  width: 100%;
  padding: 1.5rem;
}
</style>
