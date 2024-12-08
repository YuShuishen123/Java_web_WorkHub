<template>
  <div class="products">
    <div class="product-card" 
      v-for="product in list" 
      :key="product.id"
      @click="handleProductClick(product)"
    >
      <!-- 商品图片 -->
      <div class="product-image">
        <img :src="product.image_url" alt="商品图片" />
      </div>
      <!-- 商品名称 -->
      <div class="product-name">
        <span>{{ product.name }}</span>
      </div>
      <!-- 商品价格 -->
      <div class="product-price">
        <span>¥{{ product.price }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

// 商品数据接口定义
interface Product {
  id: number;
  name: string;
  price: number;
  image_url: string;
}

// 接收商品列表作为 prop
const props = defineProps({
  list: {
    type: Array as () => Product[],
    required: true,
  },
});

// 处理商品点击事件
const handleProductClick = (product: Product) => {
  router.push(`/mall/product/${product.id}`);
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

/* 商品卡片样式 */
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

/* 商品图片容器 */
.product-image {
  position: relative;
  width: 100%;
  padding-top: 100%;
}

/* 商品图片 */
.product-image img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px 10px 0 0;
}

/* 商品名称 */
.product-name {
  padding: 10px;
  font-size: 16px;
  font-weight: 500;
  color: #333;
  text-align: center;
  margin-top: 5px;
}

/* 商品价格 */
.product-price {
  padding: 10px;
  font-size: 18px;
  font-weight: 600;
  color: #FF5722;
  text-align: center;
  border-top: 1px solid #e0e0e0;
}
</style>
