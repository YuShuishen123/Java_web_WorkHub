<template>
  <div class="common-layout bg-center bg-fixed bg-cover h-full">
    <el-container>
      <!-- 头部部分 -->
      <!-- 引入头部组件 -->
      <Header />

      <!-- 主体部分 -->
      <el-main class="main mt-12">
        <!-- 商品展示区域 -->
        <router-view></router-view>
      </el-main>



    </el-container>
  </div>
</template>

<script setup lang="ts">
// 导入接口
import { RouterView } from 'vue-router';
import Header from '@/views/utile/Header.vue'

</script>

<style scoped>
/* 整体布局容器 */
.common-layout {
  background-image: url(../assets/bg.png);
}

/* 添加以下样式 */
.container {
  padding: 0;
  /* 移除容器内边距 */
  margin: 0;
  /* 移除容器外边距 */
  width: 100%;
  /* 确保容器宽度100% */
}

.main {
  width: 100%;
  min-height: calc(100vh - 60px);
  /* 减去头部高度 */
  box-sizing: border-box;
}

:deep(.el-main) {
  /* 覆盖 element-plus 的默认内边距 */
  padding: 0 !important;
}
</style>