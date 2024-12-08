<template>
  <span class="text-red-500 font-bold text-2xl">test</span>
  <button @click="routerchange"> 点击切换</button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import router from '@/router/index'

const routerchange = () => {
  router.push({ name: 'search' })
}

</script>

<style scoped></style>