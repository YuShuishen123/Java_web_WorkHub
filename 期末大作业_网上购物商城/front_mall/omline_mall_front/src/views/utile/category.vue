<template>
  <div>
    <el-select v-model="category" placeholder="请选择分类" class="category-select">
      <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
    </el-select>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { getAllCategories } from '@/api/mall';
import { useRouter } from 'vue-router';


const router = useRouter();
const category = ref('');
const categories = ref([
  { id: '1', name: '分类名称' },
]);

// 定义事件
const emit = defineEmits(['categoryChange']);

// 获取所有分类
getAllCategories().then((res) => {
  categories.value = res.data;
  // 加入一个全部选项
  categories.value.unshift({ id: '0', name: '全部' });
});




// 监听分类选择
watch(category, (newVal: string) => {
  if (newVal === '0') {
    router.push('/mall');
  } else {
    searchProduct();
  }
});

// 分类搜索功能
const searchProduct = () => {
  router.push({
    name: 'categorybyid',
    query: { categoryId: category.value },
  });
};

</script>

<style scoped>
.category-select {
  width: 200px;
  /* 你可以根据需要调整宽度 */
}
</style>
