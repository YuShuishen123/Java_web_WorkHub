<template>
    <Teleport to="body">
      <div v-if="visible" 
           class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
        <div class="bg-white rounded-2xl p-8 w-full max-w-md mx-4 relative animate-modal-fade">
          <h3 class="text-2xl font-bold text-gray-800 mb-6">修改密码</h3>
          
          <!-- 密码修改表单 -->
          <form @submit.prevent="handlePasswordSubmit" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">新密码</label>
              <div class="relative">
                <input 
                  v-model="passwordForm.password"
                  :type="showPassword ? 'text' : 'password'"
                  class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                  placeholder="请输入6-16位密码"
                />
                <button 
                  type="button"
                  @click="showPassword = !showPassword"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                >
                  <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                </button>
              </div>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
              <div class="relative">
                <input 
                  v-model="passwordForm.confirmPassword"
                  :type="showConfirmPassword ? 'text' : 'password'"
                  class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                  placeholder="请再次输入密码"
                />
                <button 
                  type="button"
                  @click="showConfirmPassword = !showConfirmPassword"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
                >
                  <i :class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                </button>
              </div>
            </div>

            <!-- 按钮组 -->
            <div class="flex justify-end gap-4 mt-8">
              <button 
                type="button"
                @click="closePasswordModal"
                class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300"
              >
                取消
              </button>
              <button 
                type="submit"
                class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300"
              >
                确认修改
              </button>
            </div>
          </form>
        </div>
      </div>
    </Teleport>
</template>

<script setup lang="js">
import { ElMessage } from 'element-plus';
import { updateUserInfo } from '@/api/user';
import { ref } from 'vue';
import { useRouter } from 'vue-router';


const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

// 使用 v-model 的约定事件名
const emit = defineEmits(['update:visible'])

// 密码修改相关
const showPasswordModal = ref(false);
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const passwordForm = ref({
  password: '',
  confirmPassword: ''
});

// 密码验证正则
const passwordRegex = /^[a-zA-Z0-9!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]{6,16}$/;


// 处理密码修改
const handlePasswordSubmit = async () => {
  // 验证密码
  if (!passwordRegex.test(passwordForm.value.password)) {
    ElMessage.error('密码必须为6-16位字母数字及常规符号');
    return;
  }
  
  // 验证两次密码是否一致
  if (passwordForm.value.password !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致');
    return;
  }
    await updateUserInfo({ password: passwordForm.value.password });
    ElMessage.success('密码修改成功');
    closePasswordModal();
    toLogin();
};

const router = useRouter();

// 跳转到登录页面
const toLogin = () => {
  router.push('/login'); // 点击登录按钮时跳转到登录页面
};


// 关闭密码模态框
const closePasswordModal = () => {
  showPasswordModal.value = false;
  passwordForm.value = {
    password: '',
    confirmPassword: ''
  };
  showPassword.value = false;
  showConfirmPassword.value = false;
  emit('update:visible', false)
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