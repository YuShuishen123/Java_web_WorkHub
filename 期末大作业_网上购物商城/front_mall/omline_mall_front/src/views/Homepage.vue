<template>
  <div>
    <!-- 商品展示区域 -->
    <ProductList :list="tableData.list" />

    <!-- 分页 -->
    <Pagination :total="tableData.total" :currentPage="tableData.pageNum" :pageSize="tableData.pageSize"
      @update:currentPage="handleCurrentChange" @update:pageSize="handleSizeChange" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { getProducts } from '@/api/mall';
import type { ComponentSize } from 'element-plus';
import ProductList from '@/views/utile/ProductList.vue';  // 引入商品展示区域组件
import Pagination from '@/views/utile/Pagination.vue';  // 引入分页组件

// 分页相关
const size = ref<ComponentSize>('default');

// 商品数据接口定义
interface Product {
  id: number;
  name: string;
  price: number;
  image_url: string;
}

// 定义表格数据结构
interface TableRow {
  total: number;
  pageNum: number;
  pageSize: number;
  totalPages: number;
  list: Product[];
}

// 响应式数据
const tableData = ref<TableRow>({
  total: 0,
  pageNum: 1,
  pageSize: 12,
  totalPages: 0,
  list: [],
});

// 获取商品数据
const getAllProducts = async function () {
  try {
    const page = tableData.value.pageNum;
    const size = tableData.value.pageSize;
    const data = await getProducts(page, size);
    tableData.value = data.data;
    window.scrollTo(0, 0);  // 让页面滚动到顶部
  } catch (error) {
    console.error("获取产品数据失败", error);
  }
};

// 初始化获取商品数据
getAllProducts();

// 分页大小变化处理
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`);
  tableData.value.pageSize = val; // 更新每页显示商品数量
  getAllProducts(); // 重新获取商品数据
};

// 当前页码变化处理
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`);
  tableData.value.pageNum = val; // 更新当前页码
  getAllProducts(); // 重新获取商品数据
};
</script>

<style scoped lang="css">
/* 你可以根据需要在这里添加父组件的样式 */
</style>
