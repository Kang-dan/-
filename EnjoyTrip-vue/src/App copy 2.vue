<script setup>

</script>
<template>

  <div id="treeMap">
    <div class="outer-area">
      <div class="inner-area">
        <div class="inner-tree"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .outer-area {
    margin: 0 auto;
    width: 700px;
    height: 700px;
    border-radius: 50%;
    border: 10px solid rgba(67, 85, 51, 0.333);
  }
  .inner-area {
    position: relative;
    left: calc(50% - 250px);
    top: calc(50% - 250px);
    width: 500px;
    height: 500px;
    border: 10px solid rgba(67, 85, 51, 0.333);
  }
  .inner-tree {
    position: absolute;
    top: -100px;
    left: 120px;
    width: 100px;
    height: 100px;    
    border: 10px solid rgba(51, 92, 15, 0.333);
  }
</style>
