<template>
    <Teleport to="body">
      <div v-if="visible" 
          class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
        <div class="bg-white rounded-2xl p-8 w-full max-w-4xl mx-4 relative animate-modal-fade">
          <h3 class="text-2xl font-bold text-gray-800 mb-6">地址管理</h3>
          
          <!-- 地址列表 -->
          <div class="max-h-[60vh] overflow-y-auto custom-scrollbar">
            <div class="space-y-4">
              <!-- 现有地址列表 -->
              <div v-for="(address, index) in addresses" :key="index" 
                  class="bg-gray-50 p-4 rounded-xl relative group">
                <div class="flex items-start justify-between">
                  <div class="space-y-2 flex-1">
                    <div class="flex items-center gap-4">
                      <span class="font-medium">{{ address.receiver_name }}</span>
                      <span class="text-gray-600">{{ address.phone }}</span>
                      <span v-if="address.is_default" 
                            class="px-2 py-0.5 text-xs bg-pink-100 text-pink-600 rounded-full">
                        默认地址
                      </span>
                    </div>
                    <div class="text-gray-600">
                      {{ address.province }}{{ address.city }}{{ address.district }}{{ address.detail }}
                    </div>
                  </div>
                  
                  <!-- 操作按钮 -->
                  <div class="flex items-center gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                    <button v-if="!address.is_default"
                            @click="handleSetDefault(address.id)"
                            class="text-blue-600 hover:text-blue-700 text-sm">
                      设为默认
                    </button>
                    <button @click="editAddress(address)"
                            class="text-gray-600 hover:text-gray-700 text-sm">
                      编辑
                    </button>
                    <button @click="handleDeleteAddress(address.id)"
                            class="text-red-600 hover:text-red-700 text-sm">
                      删除
                    </button>
                  </div>
                </div>
              </div>
              
              <!-- 新增地址表单 -->
              <div v-if="showAddressForm" class="bg-white p-4 rounded-xl border-2 border-dashed border-gray-200">
                <form @submit.prevent="handleAddressSubmit" class="space-y-4">
                  <div class="grid grid-cols-2 gap-4">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">收件人</label>
                      <input v-model="addressForm.receiver_name" 
                            type="text" 
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">联系电话</label>
                      <input v-model="addressForm.phone" 
                            type="tel" 
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                    </div>
                  </div>
                  
                  <div class="grid grid-cols-3 gap-4">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">省份</label>
                      <input v-model="addressForm.province" 
                            type="text" 
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">城市</label>
                      <input v-model="addressForm.city" 
                            type="text" 
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">区/县</label>
                      <input v-model="addressForm.district" 
                            type="text" 
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">详细地址</label>
                    <input v-model="addressForm.detail" 
                          type="text" 
                          required
                          class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
                  </div>
                  
                  <div class="flex items-center">
                    <input v-model="addressForm.is_default" 
                          type="checkbox" 
                          id="isDefault"
                          class="rounded text-purple-500 focus:ring-purple-500"/>
                    <label for="isDefault" class="ml-2 text-sm text-gray-700">设为默认地址</label>
                  </div>
                  
                  <div class="flex justify-end gap-4">
                    <button type="button"
                            @click="cancelAddressForm"
                            class="px-4 py-2 text-gray-600 hover:text-gray-700">
                      取消
                    </button>
                    <button type="submit"
                            class="px-6 py-2 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600">
                      保存
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
          
          <!-- 底部按钮 -->
          <div class="mt-6 flex justify-end">
            <button 
              v-if="!showAddressForm"
              @click="showAddressForm = true"
              class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300"
            >
              新增地址
            </button>
            <button 
              @click="closeAddressModal"
              class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300"
            >
              关闭
            </button>
          </div>
        </div>
      </div>
    </Teleport>

<!-- 新增/编辑地址模态框 -->
  <Teleport to="body">
    <div v-if="showAddressForm" 
        class="fixed inset-0 bg-black/50 backdrop-blur-sm z-[60] flex items-center justify-center">
      <div class="bg-white rounded-2xl p-8 w-full max-w-2xl mx-4 relative animate-modal-fade">
        <h3 class="text-2xl font-bold text-gray-800 mb-6">{{ isEditing ? '编辑地址' : '新增地址' }}</h3>
        
        <form @submit.prevent="handleAddressSubmit" class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">收件人</label>
              <input v-model="addressForm.receiver_name" 
                    type="text" 
                    required
                    class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">联系电话</label>
              <input v-model="addressForm.phone" 
                    type="tel" 
                    required
                    class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
            </div>
          </div>
          
          <div class="grid grid-cols-3 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">省份</label>
              <input v-model="addressForm.province" 
                    type="text" 
                    required
                    class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">城市</label>
              <input v-model="addressForm.city" 
                    type="text" 
                    required
                    class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">区/县</label>
              <input v-model="addressForm.district" 
                    type="text" 
                    required
                    class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
            </div>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">详细地址</label>
            <input v-model="addressForm.detail" 
                  type="text" 
                  required
                  class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent"/>
          </div>
          
          <div class="flex items-center">
            <input v-model="addressForm.is_default" 
                  type="checkbox" 
                  id="isDefault"
                  class="rounded text-purple-500 focus:ring-purple-500"/>
            <label for="isDefault" class="ml-2 text-sm text-gray-700">设为默认地址</label>
          </div>
          
          <div class="flex justify-end gap-4 mt-6">
            <button type="button"
                    @click="cancelAddressForm"
                    class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300">
              取消
            </button>
            <button type="submit"
                    class="px-6 py-2.5 bg-gradient-to-r from-pink-500 to-purple-500 text-white rounded-xl hover:from-pink-600 hover:to-purple-600 transition duration-300">
              {{ isEditing ? '保存修改' : '添加地址' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { ElMessage } from 'element-plus';
import { getUserAddresses, addAddress, updateAddress, deleteAddress, type Address } from '@/api/addresses';
import { getUserInfo} from '@/api/user';


// 只需要这两个定义
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:visible'])

// 关闭模态窗
const close = () => {
  emit('update:visible', false)
}

// 地址管理相关
const showAddressModal = ref(false);
const showAddressForm = ref(false);
const addresses = ref<Address[]>([]);

// 定义表单类型
interface AddressDto extends Address {
  receiver_name: string;
  phone: string;
  province: string;
  city: string;
  district: string;
  detail: string;
  is_default: boolean;
}

// 修改表单的类型声明
const addressForm = ref<AddressDto>({
  receiver_name: '',
  phone: '',
  province: '',
  city: '',
  district: '',
  detail: '',
  is_default: false
});

const isEditing = ref(false);

// 获取地址列表
const fetchAddresses = async () => {
  try {
    const response = await getUserAddresses();
    addresses.value = response.data;
  } catch (error) {
    console.error('获取地址列表失败:', error);
    ElMessage.error('获取地址列表失败');
  }
};

// 修改编辑地址函数
const editAddress = (address: Address) => {
  addressForm.value = {
    ...address,
    province: address.province,
    city: address.city,
    district: address.district,
    detail: address.detail,
    is_default: address.is_default
  };
  isEditing.value = true;
  showAddressForm.value = true;
};

// 修改提交处理
const handleAddressSubmit = async () => {
  try {
    const submitData: Address = {
      id: addressForm.value.id,
      receiver_name: addressForm.value.receiver_name,
      phone: addressForm.value.phone,
      province: addressForm.value.province,
      city: addressForm.value.city,
      district: addressForm.value.district,
      detail: addressForm.value.detail,
      is_default: addressForm.value.is_default
    };

    // 如果设置为默认地址，需要先处理已有的默认地址
    if (submitData.is_default) {
      const currentDefault = addresses.value.find(addr => addr.is_default);
      if (currentDefault && currentDefault.id !== submitData.id) {
        await updateAddress({
          ...currentDefault,
          is_default: false
        });
      }
    }

    if (isEditing.value) {
      await updateAddress(submitData);
      ElMessage.success('地址更新成功');
    } else {
      await addAddress(submitData);
      ElMessage.success('地址添加成功');
    }
    
    await fetchAddresses();
    cancelAddressForm();
  } catch (error) {
    console.error('操作失败:', error);
    ElMessage.error('操作失败，请重试');
  }
};

// 删除地址
const handleDeleteAddress = async (id?: number) => {
  if (!id) return;
  try {
    await deleteAddress(id);
    ElMessage.success('地址删除成功');
    await fetchAddresses();
  } catch (error) {
    console.error('删除失败:', error);
    ElMessage.error('删除失败，请重试');
  }
};

// 设置默认地址
const handleSetDefault = async (id?: number) => {
  if (!id) return;
  try {
    // 找到当前的默认地址
    const currentDefault = addresses.value.find(addr => addr.is_default);
    
    // 如果存在默认地址，先将其更新为非默认
    if (currentDefault) {
      await updateAddress({
        ...currentDefault,
        is_default: false
      });
    }

    // 找到要设置为默认的地址
    const targetAddress = addresses.value.find(addr => addr.id === id);
    if (targetAddress) {
      // 将目标地址设置为默认
      await updateAddress({
        ...targetAddress,
        is_default: true
      });
    }

    ElMessage.success('默认地址设置成功');
    await fetchAddresses(); // 重新获取地址列表
  } catch (error) {
    console.error('设置失败:', error);
    ElMessage.error('设置失败，请重试');
  }
};

// 取消地址表单
const cancelAddressForm = () => {
  showAddressForm.value = false;
  isEditing.value = false;
  addressForm.value = {
    receiver_name: '',
    phone: '',
    province: '',
    city: '',
    district: '',
    detail: '',
    is_default: false
  };
};

// 关闭地址模态框
const closeAddressModal = () => {
  showAddressModal.value = false;
  cancelAddressForm();
  emit('update:visible', false)
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

onMounted(() => {
  fetchUserInfo();
  fetchAddresses(); // 获取地址列表
});
</script>

<style scoped>
/* 添加卡片悬浮动画 */
.hover\:shadow-md:hover {
  transform: translateY(-2px);
}

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