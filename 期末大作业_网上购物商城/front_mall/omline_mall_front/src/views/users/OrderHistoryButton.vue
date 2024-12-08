<template>
  <Teleport to="body">
    <div v-if="visible" class="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center">
      <div class="bg-white rounded-2xl p-8 w-full max-w-9xl mx-4 relative animate-modal-fade">
        <h3 class="text-2xl font-bold text-gray-800 mb-6">购买记录</h3>
        
        <!-- 表格容器 -->
        <div class="max-h-[60vh] overflow-y-auto custom-scrollbar">
          <div v-if="loading" class="flex justify-center items-center py-8">
            <el-loading />
          </div>
          <table v-else class="w-full min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50 sticky top-0">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">订单编号</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">商品名称</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">数量</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">总价</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">收货地址</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">备注</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">购买时间</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="order in purchaseRecords" :key="order.order_id" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ order.order_number }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ order.productName }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ order.quantity }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-green-600">¥{{ order.total_amount.toFixed(2) }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ order.address }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ order.order_note || '-' }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ order.created_at }}</td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span :class="getStatusClass(order.order_status)">
                    {{ order.order_status }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex space-x-2">
                    <template v-if="order.order_status === '未支付'">
                      <button
                        @click="handlePay(order.order_id.toString())"
                        class="px-3 py-1 text-xs bg-blue-500 text-white rounded hover:bg-blue-600"
                      >
                        支付
                      </button>
                      <button
                        @click="showAddressDialog(order.order_id.toString())"
                        class="px-3 py-1 text-xs bg-green-500 text-white rounded hover:bg-green-600"
                      >
                        修改地址
                      </button>
                      <button
                        @click="handleCancel(order.order_id.toString())"
                        class="px-3 py-1 text-xs bg-yellow-500 text-white rounded hover:bg-yellow-600"
                      >
                        取消
                      </button>
                    </template>
                    <button
                      @click="handleDelete(order.order_id.toString())"
                      class="px-3 py-1 text-xs bg-red-500 text-white rounded hover:bg-red-600"
                    >
                      删除
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 底部按钮 -->
        <div class="mt-6 flex justify-end">
          <button 
            @click="close" 
            class="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-300"
          >
            关闭
          </button>
        </div>
      </div>
    </div>
  </Teleport>

  <!-- 地址选择对话框 -->
  <el-dialog
    v-model="addressDialogVisible"
    title="选择收货地址"
    width="30%"
  >
    <el-form label-width="80px">
      <el-form-item label="收货地址">
        <el-select v-model="selectedAddressId" placeholder="请选择收货地址" style="width: 100%">
          <el-option
            v-for="address in addresses"
            :key="address.id"
            :label="formatAddress(address)"
            :value="address.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addressDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmUpdateAddress">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { getOrders, updateOrderStatus, updateOrderAddress, deleteOrder } from '@/api/order'
import { getUserAddresses } from '@/api/addresses'
import type { Address } from '@/api/addresses'
import { ElMessage, ElLoading, ElMessageBox } from 'element-plus'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

// 使用 v-model 的约定事件名
const emit = defineEmits(['update:visible'])

// 加载状态
const loading = ref(false)

// 关闭模态窗
const close = () => {
  emit('update:visible', false)
}

// 定义订单接口
interface Order {
  order_id: number;
  order_number: string;
  address: string;
  productName: string;
  quantity: number;
  total_amount: number;
  order_status: "未支付" | "已支付" | "已取消";
  order_note?: string;
  created_at: string;
  updated_at: string;
}

// 获取状态样式
const getStatusClass = (status: string) => {
  const baseClasses = 'px-2 py-1 text-xs font-medium rounded-full';
  switch (status) {
    case '已支付':
      return `${baseClasses} bg-green-100 text-green-800`;
    case '未支付':
      return `${baseClasses} bg-yellow-100 text-yellow-800`;
    case '已取消':
      return `${baseClasses} bg-red-100 text-red-800`;
    default:
      return `${baseClasses} bg-gray-100 text-gray-800`;
  }
}

// 订单操作函数
const handlePay = async (orderId: string) => {
  try {
    await ElMessageBox.confirm('确定要支付此订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'info'
    })
    await updateOrderStatus(orderId, '已支付')
    ElMessage.success('支付成功')
    fetchOrders()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('支付失败')
    }
  }
}

const handleCancel = async (orderId: string) => {
  try {
    await ElMessageBox.confirm('确定要取消此订单吗？取消后不可恢复', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await updateOrderStatus(orderId, '已取消')
    ElMessage.success('订单已取消')
    fetchOrders()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消失败')
    }
  }
}

const handleDelete = async (orderId: string) => {
  try {
    await ElMessageBox.confirm('确定要删除此订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteOrder(orderId)
    ElMessage.success('订单已删除')
    fetchOrders()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 地址选择相关
const addressDialogVisible = ref(false)
const addresses = ref<Address[]>([])
const selectedAddressId = ref('')
const currentOrderId = ref('')

// 获取地址列表
const fetchAddresses = async () => {
  try {
    const res = await getUserAddresses()
    if (res.code === 200) {
      addresses.value = res.data
    }
  } catch (error) {
    ElMessage.error('获取地址列表失败')
  }
}

// 格式化地址显示
const formatAddress = (address: Address) => {
  return `${address.receiver_name} ${address.phone} ${address.province}${address.city}${address.district}${address.detail}`
}

// 显示地址选择对话框
const showAddressDialog = (orderId: string) => {
  currentOrderId.value = orderId
  selectedAddressId.value = ''
  addressDialogVisible.value = true
  fetchAddresses()
}

// 确认修改地址
const confirmUpdateAddress = async () => {
  if (!selectedAddressId.value) {
    ElMessage.warning('请选择收货地址')
    return
  }

  try {
    await updateOrderAddress(currentOrderId.value, parseInt(selectedAddressId.value))
    ElMessage.success('地址已更新')
    addressDialogVisible.value = false
    fetchOrders()
  } catch (error) {
    ElMessage.error('更新地址失败')
  }
}

// 初始化数据
const purchaseRecords = ref<Order[]>([])

// 获取订单数据
const fetchOrders = async () => {
  loading.value = true
  try {
    const res = await getOrders()
    if (res.code === 200 && res.data) {
      // 先将数据转为 unknown，再转为 Order[]
      const orderData = res.data as unknown as Order[]
      purchaseRecords.value = orderData.map(order => ({
        ...order,
        total_amount: Number(order.total_amount), // 确保总金额是数字类型
        created_at: new Date(order.created_at).toLocaleString(), // 格式化日期
      }))
    } else {
      ElMessage.error(res.message || '获取订单数据失败')
    }
  } catch (error) {
    console.error('获取订单失败:', error)
    ElMessage.error('获取订单数据失败，请重试')
  } finally {
    loading.value = false
  }
}

// 监听 visible 变化，当显示时获取数据
watch(() => props.visible, (newValue) => {
  if (newValue) {
    fetchOrders()
  }
})

// 导出方法供父组件调用
defineExpose({
  fetchOrders
})
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
