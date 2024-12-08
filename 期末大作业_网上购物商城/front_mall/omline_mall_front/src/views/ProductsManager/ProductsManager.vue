<template>
  <!-- 左侧选项菜单高度贯穿整个页面 -->
  <div class="h-full">
    <!-- 左侧侧边栏，宽度为200px，固定不随页面滚动 -->
    <el-aside width="150px" class=" ml-0 fixed top-30 left-0 h-full overflow-hidden">
      <!-- 选项菜单，高度贯穿整个页面 ,背景灰色 ,字体加粗 ，菜单与菜单之间有分割线-->
      <el-menu class="h-full font-bold text-lg bg-pink-200">
        <el-menu-item class="text-base">商品管理</el-menu-item>
        <el-menu-item class="text-base">分类管理</el-menu-item>
        <el-sub-menu index="user-management" class="bg-pink-200">
          <template #title>
            <span class="text-base">用户管理</span>
          </template>
          <el-menu-item class="font-bold  bg-pink-200 text-sm">用户列表</el-menu-item>
          <el-menu-item class="font-bold  bg-pink-200 text-sm">用户权限</el-menu-item>
          <el-menu-item class="font-bold  bg-pink-200 text-sm">用户角色</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧内容区域,纯白色背景，覆盖整个页面 -->
    <div class="ml-[150px] overflow-y-auto h-full">
    </div>
  </div>
</template>
<script setup lang="ts">
</script>
<style scoped></style>