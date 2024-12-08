<template>
  <el-header class="header fixed top-0 left-0 w-full bg-pink-200 text-white p-2 z-10">
    <!-- 左侧部分：商城标题和Logo -->
    <div class="left-section">
      <img @click="toMall" src="@/assets/猪猪大头贴.svg" alt="Logo" class="logo cursor-pointer" />
      <span @click="toMall" class="mall-title text-red-700 font-bold overflow-hidden cursor-pointer">活力优选购物商城</span>
    </div>

    <!-- 中间部分：搜索框和按钮 -->
    <div class="center-section">
      <div class="search-container">
        <!-- 搜索框 -->
        <div class="search-input">
          <el-input v-model="input" @keydown.enter="searchProduct" placeholder="请输入商品名称" />
        </div>
        <!-- 搜索按钮 -->
        <div class="search-button">
          <el-button type="primary" :icon="Search" @click="searchProduct">搜索</el-button>
        </div>
        <!-- 分类选择 -->
        <div class="ml-2">
          <Category />
        </div>
      </div>
    </div>

    <!-- 右侧部分：用户头像和昵称 -->
    <div class="right-section">
      <div v-if="isLogin" class="user-info">
        <div class="demo-avatar">
          <el-row class="demo-avatar demo-basic">
            <el-col :span="12">
              <div class="demo-basic--circle">
                <div class="block">
                  <el-avatar shape="square" :size="35" :src="userAvatar" />
                </div>
              </div>
            </el-col>
          </el-row>
        </div>

        <div class="user-name">
          <el-dropdown>
            <span class="el-dropdown-link font-bold text-black-700 font-size-800 focus:outline-none focus:ring-0">
              {{ nickname }}
              <el-icon class="el-icon--right">
                <arrow-down />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu class="font-bold text-gray-700">
                <el-dropdown-item @click="toInfo">个人中心</el-dropdown-item>
                <el-dropdown-item @click="toProductsManager">商品管理</el-dropdown-item>
                <el-dropdown-item @click="handlelogout">退出登陆</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      <!-- 如果未登录，显示登录按钮，点击后跳转到登录页面 -->
      <el-button @click="toLogin" v-else>登陆</el-button>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { ArrowDown } from '@element-plus/icons-vue';
import { Search } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { logout, getUserInfo } from '@/api/user';
import Category from './category.vue';

// 响应式数据
const input = ref(''); // 搜索框的输入
const isLogin = ref(false); // 登录状态
const userAvatar = ref(''); // 用户头像
const nickname = ref(''); // 用户昵称

// 使用路由
const router = useRouter();

// 获取用户信息
const getUserInfoData = async () => {
  try {
    const data = await getUserInfo();
    nickname.value = data.data.nickname;
    userAvatar.value = data.data.avatar;
  } catch (error) {
    console.error('获取用户信息失败', error);
  }
};

// 跳转到商城首页
const toMall = () => {
  input.value = ''; // 清空搜索框内容
  router.push('/mall');
};

// 跳转到商品管理页面
const toProductsManager = () => {
  router.push('productsmanager');
};

// 判断登录状态，并获取用户信息
const checkLoginStatus = () => {
  if (localStorage.getItem('token')) {
    // 存在且不为空
    isLogin.value = true;
    getUserInfoData(); // 如果用户已经登录，获取用户信息
  } else {
    isLogin.value = false;
  }
};

// 跳转到登录页面
const toLogin = () => {
  router.push('/login'); // 点击登录按钮时跳转到登录页面
};

// 跳转到个人中心页面
const toInfo = () => {
  router.push('/mall/info'); // 使用绝对路径跳转到个人中心页面
};

// 退出登录
const handlelogout = async () => {
  try {
    await logout(); // 退出登录操作
    ElMessage.success('退出成功');
    localStorage.removeItem('token'); // 清除 token
    isLogin.value = false;
    userAvatar.value = '';
    nickname.value = '';
    router.push('/mall'); // 退出后跳转到商城页面
  } catch (error) {
    console.error('退出失败', error);
  }
};

// 搜索商品
const searchProduct = () => {
  if (input.value) {
    router.push({
      name: 'search',
      query: { keyword: input.value },
    });
  } else { // 如果搜索框为空，跳转到商城首页
    router.push('/mall');
  }
};

// 组件挂载时检查登录状态
onMounted(() => {
  checkLoginStatus(); // 页面加载时检查用户是否已登录
});
</script>

<style scoped>
/* 头部样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  color: white;
  height: 50px;
}

/* 左侧部分：Logo和商城标题 */
.left-section {
  display: flex;
  align-items: center;
}

.logo {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.mall-title {
  font-size: 24px;
  font-family: 'Arial', sans-serif;
}

/* 中间部分：搜索框和按钮 */
.center-section {
  display: flex;
  align-items: center;
  flex-grow: 1;
  margin: 0 20px;
}

.search-container {
  display: flex;
}

.search-input {
  width: 300px;
  border-radius: 20px;
}

.search-button {
  margin-left: 10px;
}

.search-btn {
  background-color: #ffffff;
  color: #409EFF;
  border: 1px solid #409EFF;
  border-radius: 20px;
  padding: 0 20px;
  margin-left: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.search-btn:hover {
  background-color: #409EFF;
  color: white;
}

/* 右侧部分：用户头像和昵称 */
.right-section {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
}

.demo-avatar {
  margin-right: 5px;
}
</style>
