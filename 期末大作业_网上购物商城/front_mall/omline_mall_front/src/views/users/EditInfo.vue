<template>
    <Teleport to="body">
        <div v-if="visible" class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
            <div class="bg-white rounded-2xl p-8 w-full max-w-md mx-4 relative animate-modal-fade">
                <h3 class="text-2xl font-bold text-gray-800 mb-6">编辑个人资料</h3>

                <!-- 编辑表单 -->
                <form @submit.prevent="handleSubmit" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">昵称</label>
                        <input v-model="editForm.nickname" type="text"
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent" />
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                        <input v-model="editForm.email" type="email"
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent" />
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">手机</label>
                        <input v-model="editForm.phone" type="tel"
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent" />
                    </div>

                    <!-- 按钮组 -->
                    <div class="flex justify-end gap-4 mt-8">
                        <button type="button" @click="closeModal"
                            class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300">
                            取消
                        </button>
                        <button type="submit"
                            class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300">
                            保存
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </Teleport>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { updateUserInfo, getUserInfo } from '@/api/user'

// 定义 props
const props = defineProps({
    visible: {
        type: Boolean,
        default: false
    }
})

// 定义事件
const emit = defineEmits(['update:visible', 'info-updated'])

// 用户信息
const userInfo = ref(null)

// 编辑表单数据
const editForm = ref({
    nickname: '',
    email: '',
    phone: ''
})

// 获取用户信息
const fetchUserInfo = async () => {
    try {
        const response = await getUserInfo()
        userInfo.value = response.data
        // 更新表单数据
        editForm.value = {
            nickname: response.data.nickname || '',
            email: response.data.email || '',
            phone: response.data.phone || ''
        }
    } catch (error) {
        console.error('获取用户信息失败:', error)
        ElMessage.error('获取用户信息失败')
    }
}

// 监听 visible 变化，当打开模态框时获取用户信息
watch(() => props.visible, (newVal) => {
    if (newVal) {
        fetchUserInfo()
    }
})

// 关闭模态框
const closeModal = () => {
    emit('update:visible', false)
}

// 处理表单提交
const handleSubmit = async () => {
    try {
        await updateUserInfo(editForm.value)
        ElMessage.success('更新成功')
        emit('info-updated')
        closeModal()
    } catch (error) {
        console.error('更新失败:', error)
        ElMessage.error('更新失败，请重试')
    }
}
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
}
</style>
