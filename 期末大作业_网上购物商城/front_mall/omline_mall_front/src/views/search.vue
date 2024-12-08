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
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { searchProduct } from '@/api/mall';
import Pagination from '@/views/utile/Pagination.vue';
import ProductList from '@/views/utile/ProductList.vue';


// 获取路由参数
const route = useRoute();

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

// 获取搜索关键词
const getSearchQuery = () => {
  return Array.isArray(route.query.keyword) ? route.query.keyword[0] : route.query.keyword;
};

// 监听 route.query.search 的变化，重新获取商品数据
watch(() => route.query.keyword, (newSearchQuery) => {
  // 设置页码为1
  tableData.value.pageNum = 1;
  getAllProducts();  // 重新获取商品数据
});

// 获取商品数据
const getAllProducts = async function () {
  try {
    const page = tableData.value.pageNum;
    const size = tableData.value.pageSize;
    const searchQuery = getSearchQuery();  // 获取搜索关键词
    const query = Array.isArray(searchQuery) ? searchQuery[0] : searchQuery || ''; // 确保是字符串
    const data = await searchProduct(page, size, query);
    tableData.value = data.data;
    window.scrollTo(0, 0);  // 让页面滚动到顶部
  } catch (error) {
    console.error("获取产品数据失败", error);
  }
};

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
/* 商品展示区域 */
.products {
  width: 70%;
  margin: 0 auto;
  padding: 10px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  grid-auto-rows: 1fr;
  gap: 10px;
}

.product-card {
  background-color: #fbf7f7;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 80%;
  padding: 10px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-image {
  position: relative;
  width: 100%;
  padding-top: 100%;
}

.product-image img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px 10px 0 0;
}

.product-name {
  padding: 10px;
  font-size: 16px;
  font-weight: 500;
  color: #333;
  text-align: center;
  margin-top: 10px;
}

.product-price {
  padding: 10px;
  font-size: 18px;
  font-weight: 600;
  color: #FF5722;
  text-align: center;
  border-top: 1px solid #e0e0e0;
}

.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}

#el-pagination {
  display: flex;
  justify-content: right;
  margin-top: 20px;
}
</style>
