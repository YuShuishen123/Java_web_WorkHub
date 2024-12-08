<template>
  <div class="min-h-screen bg-gradient-to-br via-white to-purple-100 py-12 px-4">
    <div class="max-w-4xl mx-auto bg-white/80 backdrop-blur-sm rounded-3xl shadow-xl p-8 relative">
      <div class="flex flex-col md:flex-row gap-8">
        <!-- 左侧头像区域 -->
        <div class="md:w-1/3">
          <div class="w-40 h-40 rounded-2xl bg-gradient-to-br from-pink-200 to-purple-200 p-1 shadow-lg mx-auto md:mx-0 cursor-pointer group"
               @click="showAvatarModal = true">
            <div class="w-full h-full rounded-2xl overflow-hidden relative">
              <img 
                :src="userInfo?.avatar || '/default-avatar.jpg'" 
                alt="用户头像" 
                class="w-full h-full object-cover transition duration-300 group-hover:scale-105"
              />
              <!-- 悬浮提示 -->
              <div class="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
                <span class="text-white text-sm">点击更换头像</span>
              </div>
            </div>
          </div>
          <div class="text-center md:text-left mt-4">
            <h2 class="text-2xl font-bold text-gray-800">{{ userInfo?.nickname || '未设置昵称' }}</h2>
            <p class="text-gray-500">@{{ userInfo?.username }}</p>
            <!-- 添加创建时间 -->
            <div class=" mt-4 text-sm text-gray-500 space-y-1">
              <div class="flex items-center gap-2">
                <i class="fas fa-clock"></i>
                <span>注册于 {{ formatDate(userInfo?.createdtime) }}</span>
              </div>
              <div class="flex items-center gap-2">
                <i class="fas fa-history"></i>
                <span>更新于 {{ formatDate(userInfo?.updatedtime) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 右侧信息区域 -->
        <div class="md:w-2/3 space-y-6">
          <!-- 信息卡片 -->
          <div class="grid gap-6">
            <!-- 邮箱卡片 -->
            <div class="bg-white/50 hover:bg-white/80 rounded-2xl p-6 transition-all duration-300 shadow-sm hover:shadow-md">
              <div class="flex items-center gap-4">
                <div class="p-3 bg-gradient-to-br from-pink-400 to-purple-400 rounded-xl shadow-inner">
                  <i class="fas fa-envelope text-white text-xl"></i>
                </div>
                <div class="flex-1">
                  <div class="text-sm text-gray-500 font-medium">邮箱地址</div>
                  <div class="text-gray-800 font-semibold mt-1">{{ userInfo?.email || '未设置邮箱' }}</div>
                </div>
              </div>
            </div>

            <!-- 手机卡片 -->
            <div class="bg-white/50 hover:bg-white/80 rounded-2xl p-6 transition-all duration-300 shadow-sm hover:shadow-md">
              <div class="flex items-center gap-4">
                <div class="p-3 bg-gradient-to-br from-purple-400 to-pink-400 rounded-xl shadow-inner">
                  <i class="fas fa-phone text-white text-xl"></i>
                </div>
                <div class="flex-1">
                  <div class="text-sm text-gray-500 font-medium">手机号码</div>
                  <div class="text-gray-800 font-semibold mt-1">{{ userInfo?.phone || '未设置手机' }}</div>
                </div>
              </div>
            </div>
          </div>

          <!-- 操作按钮 -->
          <div class="flex justify-end gap-4 mt-8">
            <button 
              @click="showPasswordModal = true"
              class="px-6 py-2.5 bg-white text-pink-500 border border-pink-500 rounded-xl hover:bg-pink-50 transition duration-300">
              修改密码
            </button>
            <button 
              @click="showEditModal = true"
              class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300 shadow-md">
              编辑资料
            </button>
            <button 
              @click="showOrderHistory = true"
              class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300 shadow-md">
              购买记录
            </button>
            <button 
              @click="showAddressModal = true"
              class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300 shadow-md">
              地址管理
            </button>
            <button 
              @click="showDeleteModal = true"
              class="px-6 py-2.5 bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-xl hover:from-red-600 hover:to-pink-600 transition duration-300 shadow-md">
              注销账号
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 编辑资料模态框 -->
    <EditInfo v-model:visible="showEditModal" @info-updated="fetchUserInfo"/>

    <!-- 添加头像上传模态框 -->
     <ChangeAvatar v-model:visible="showAvatarModal" @avatar-updated="fetchUserInfo"/>

    <!-- 添加密码修改模态框 -->
    <ChangePassword v-model:visible="showPasswordModal"/> 

    <!-- 添加注销账号确认模态框 -->
    <DeleteAcount v-model:visible="showDeleteModal"/>

    <!-- 购买记录模态框 -->
    <OrderHistoryButton v-model:visible="showOrderHistory"/>

    <!-- 地址管理模态框 -->
    <AddressManageButton v-model:visible="showAddressModal"/>
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getUserInfo } from '@/api/user';
import OrderHistoryButton from './OrderHistoryButton.vue';
import AddressManageButton from './AddressManageButton.vue';
import DeleteAcount from './DeleteAcount.vue';
import ChangePassword from './ChangePassword.vue';
import ChangeAvatar from './ChangeAvatar.vue';
import EditInfo from './EditInfo.vue';

// 定义购买记录模态框
const showOrderHistory = ref(false);
// 定义地址管理模态框
const showAddressModal = ref(false);
// 注销账号相关
const showDeleteModal = ref(false);
// 密码修改相关
const showPasswordModal = ref(false);
// 头像处理
const showAvatarModal = ref(false);

// 定义用户信息接口
interface UserInfo {
  username: string;
  email: string;
  phone: string;
  nickname: string;
  avatar: string;
  createdtime: string;
  updatedtime: string;
}

// 用户信息响应式数据
const userInfo = ref<UserInfo | null>(null);

// 控制模态框显示
const showEditModal = ref(false);

// 编表单数据
const editForm = ref({
  nickname: '',
  email: '',
  phone: ''
});


// 格式化日期
const formatDate = (dateStr?: string) => {
  if (!dateStr) return '未知';
  const date = new Date(dateStr);
  return new Intl.DateTimeFormat('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date);
};

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    const response = await getUserInfo();
    userInfo.value = response.data;
  } catch (error) {
    console.error('获取用户信息失败:', error);
    ElMessage.error('获取用户信息失败');
  }
};

onMounted(() => {
  fetchUserInfo();
});
</script>

<style scoped>
.bg-white\/50 {
  backdrop-filter: blur(8px);
}

.bg-white\/80 {
  backdrop-filter: blur(12px);
}

/* 添加卡片悬浮动画 */
.hover\:shadow-md:hover {
  transform: translateY(-2px);
}

</style>