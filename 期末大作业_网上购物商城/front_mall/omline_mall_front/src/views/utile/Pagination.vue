<template>
  <div class="demo-pagination-block flex justify-center mt-5 text-red-700 mb-5">
    <el-pagination v-bind:current-page="currentPage" v-bind:page-size="pageSize" :page-sizes="[8, 12, 16, 20]"
      :size="size" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>
</template>

<script setup lang="ts">
import type { ComponentSize } from 'element-plus';

// Props
const props = defineProps({
  total: {
    type: Number,
    required: true,
  },
  currentPage: {
    type: Number,
    required: true,
  },
  pageSize: {
    type: Number,
    required: true,
  },
  size: {
    type: String as () => ComponentSize, // 允许传入 'default'、'medium' 或 'small'
    default: 'default',
  },
});

// Emits
const emit = defineEmits(['update:currentPage', 'update:pageSize']);

const handleSizeChange = (val: number) => {
  emit('update:pageSize', val);
};

const handleCurrentChange = (val: number) => {
  emit('update:currentPage', val);
};
</script>

<style scoped>
/* 总数显示 */
:deep(.el-pagination .el-pagination__total) {
  color: #eeeaea;
}

/* 每页显示数量选择器 */
:deep(.el-pagination .el-pagination__sizes) {
  color: #28A745;
}

/* 分页按钮 */
:deep(.el-pagination .el-pagination__button) {
  color: #007BFF;
}

/* 当前页码 */
:deep(.el-pagination .el-pagination__pager .active) {
  color: #FF4500;
}

/* 页码按钮 */
:deep(.el-pagination .el-pagination__pager li) {
  color: #8A2BE2;
}

/* 跳转文本 */
:deep(.el-pagination .el-pagination__jump) {
  color: #eeeaea;
}

/* 上一页下一页按钮 */
:deep(.el-pagination .el-pagination__prev),
:deep(.el-pagination .el-pagination__next) {
  color: #eeeaea;
}
</style>
