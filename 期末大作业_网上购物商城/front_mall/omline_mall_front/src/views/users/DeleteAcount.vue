<template>
    <Teleport to="body">
      <div v-if="visible" 
           class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
        <div class="bg-white rounded-2xl p-8 w-full max-w-md mx-4 relative animate-modal-fade">
          <div class="text-center">
            
            <h3 class="text-2xl font-bold text-gray-800 mb-2">确认注销账号？</h3>
            <p class="text-gray-600 mb-6">
              注销后账号将被永久删除，所有数据将无法恢复。
              <br>
              请谨慎操作！
            </p>

            <!-- 确认输入框 -->
            <div class="mb-6">
              <input 
                v-model="confirmText"
                type="text"
                placeholder="请输入'确认注销'以继续"
                class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-red-500 focus:border-transparent"
              />
            </div>

            <!-- 按钮组 -->
            <div class="flex justify-end gap-4">
              <button 
                @click="closeDeleteModal"
                class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300"
              >
                取消
              </button>
              <button 
                @click="handleDelete"
                :disabled="confirmText !== '确认注销'"
                class="px-6 py-2.5 bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-xl hover:from-red-600 hover:to-pink-600 transition duration-300 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                确认注销
              </button>
            </div>
          </div>
        </div>
      </div>
    </Teleport>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { deleteAccount } from '@/api/user';
import { useTokenStore } from '@/stores/token';
import { useRouter } from 'vue-router';


const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

// 使用 v-model 的约定事件名
const emit = defineEmits(['update:visible'])


// 关闭注销模态框  
const closeDeleteModal = () => {
  showDeleteModal.value = false;
  confirmText.value = '';
  emit('update:visible', false)
}


// 注销账号相关
const showDeleteModal = ref(false);
const confirmText = ref('');
const tokenStore = useTokenStore();
const router = useRouter(); 

// 跳转到登录页面
const toLogin = () => {
  router.push('/login'); // 点击登录按钮时跳转到登录页面
};

// 处理注销
const handleDelete = async () => {
  if (confirmText.value !== '确认注销') return;
  try {
    await deleteAccount();
    ElMessage.success('账号已注销成功');
    // 设置token为空
    tokenStore.setToken('');
    // 跳转到登陆界面
    toLogin();
  } catch (error) {
    console.error('注销失败:', error);
    ElMessage.error('注销失败，请重试');
  }
};


</script>

<style scoped>
/* 模态框动画 */
.animate-modal-fade {
  animation: modalFade 0.3s ease-out;
}


@keyframes modalFade {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}</style>