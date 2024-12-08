<template>
    <!-- 添加头像上传模态框 -->
    <Teleport to="body">
        <div v-if="visible" class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
            <div class="bg-white rounded-2xl p-8 w-full max-w-md mx-4 relative animate-modal-fade">
                <h3 class="text-2xl font-bold text-gray-800 mb-6">更换头像</h3>

                <!-- 预览区域 -->
                <div class="mb-6">
                    <div class="w-32 h-32 mx-auto rounded-full overflow-hidden border-4 border-gray-100 shadow-md">
                        <img :src="avatarPreview || userInfo?.avatar || '/default-avatar.jpg'" alt="头像预览"
                            class="w-full h-full object-cover" />
                    </div>
                </div>

                <!-- 上传按钮 -->
                <div class="flex flex-col items-center gap-4">
                    <input ref="fileInput" type="file" accept="image/jpeg,image/png,image/gif" class="hidden"
                        @change="handleFileChange" />
                    <button type="button" @click="triggerFileInput"
                        class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300 w-full">
                        选择图片
                    </button>
                    <p class="text-sm text-gray-500">支持 jpg、png、gif 格式，最大 5MB</p>
                </div>

                <!-- 按钮组 -->
                <div class="flex justify-end gap-4 mt-8">
                    <button type="button" @click="closeAvatarModal"
                        class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300">
                        取消
                    </button>
                    <button type="button" :disabled="!selectedFile" @click="handleAvatarUpload"
                        class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300 disabled:opacity-50 disabled:cursor-not-allowed">
                        {{ uploading ? '上传中...' : '确认更换' }}
                    </button>
                </div>
            </div>
        </div>
    </Teleport>

</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { getUserInfo, changeAvatar } from '@/api/user';


const props = defineProps({
    visible: {
        type: Boolean,
        default: false
    }
})

// 使用 v-model 的约定事件名和自定义更新事件
const emit = defineEmits(['update:visible', 'avatar-updated'])

// 头像上传相关
const showAvatarModal = ref(false);
const fileInput = ref<HTMLInputElement | null>(null);
const selectedFile = ref<File | null>(null);
const avatarPreview = ref('');
const uploading = ref(false);

// 触发文件选择
const triggerFileInput = () => {
    fileInput.value?.click();
};

// 处理文件选择
const handleFileChange = (event: Event) => {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
        const file = input.files[0];

        // 验证文件大小
        if (file.size > 5 * 1024 * 1024) {
            ElMessage.error('文件大小不能超过 5MB');
            return;
        }

        // 验证文件类型
        const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!validTypes.includes(file.type)) {
            ElMessage.error('只支持 jpg、png、gif 格式的图片');
            return;
        }

        selectedFile.value = file;
        // 创建预览
        avatarPreview.value = URL.createObjectURL(file);
    }
};


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

// 处理头像上传
const handleAvatarUpload = async () => {
    if (!selectedFile.value) return;

    try {
        uploading.value = true;
        const response = await changeAvatar(selectedFile.value);
        ElMessage.success('头像更新成功');
        // 只发出事件，让父组件决定如何处理
        emit('avatar-updated');
        closeAvatarModal();
    } catch (error) {
        console.error('上传失败:', error);
        ElMessage.error('上传失败，请重试');
    } finally {
        uploading.value = false;
    }
};

// 关闭模态框
const closeAvatarModal = () => {
    showAvatarModal.value = false;
    selectedFile.value = null;
    avatarPreview.value = '';
    if (fileInput.value) {
        fileInput.value.value = '';
    }
    // 发送更新事件
    emit('update:visible', false);
};

onMounted(() => {
    fetchUserInfo();
});
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