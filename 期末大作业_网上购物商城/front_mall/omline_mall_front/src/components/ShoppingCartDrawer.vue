<template>
  <div class="shopping-cart-drawer">
    <el-button 
      class="cart-trigger"
      type="primary"
      circle
      @click="drawerVisible = true"
    >
      <el-badge :value="cartItems.length" :hidden="cartItems.length === 0">
        <el-icon><ShoppingCart /></el-icon>
      </el-badge>
    </el-button>

    <el-drawer
      v-model="drawerVisible"
      title="购物车"
      direction="rtl"
      size="400px"
    >
      <div v-if="cartItems.length === 0" class="empty-cart">
        <el-empty description="购物车是空的" />
      </div>
      <div v-else class="cart-items">
        <div v-for="item in cartItems" :key="item.orderId" class="cart-item">
          <div class="item-info">
            <img :src="item.productImage" :alt="item.productName" class="item-image">
            <div class="item-details">
              <h3>{{ item.productName }}</h3>
              <p class="item-quantity">数量: {{ item.quantity }}</p>
              <p class="price">¥{{ item.totalPrice }}</p>
              <p class="address">地址: {{ item.address || '未设置' }}</p>
            </div>
          </div>
          <div class="item-actions">
            <el-button 
              type="danger" 
              size="small" 
              @click="deleteItem(item.orderId)"
            >
              删除
            </el-button>
            <el-button 
              type="warning" 
              size="small"
              @click="updateAddress(item.orderId)"
            >
              修改地址
            </el-button>
            <el-button 
              type="primary" 
              size="small"
              @click="payOrder(item.orderId)"
            >
              去付款
            </el-button>
          </div>
        </div>
      </div>
    </el-drawer>

    <!-- 地址选择对话框 -->
    <el-dialog
      v-model="addressDialogVisible"
      title="选择收货地址"
      width="30%"
    >
      <el-select v-model="selectedAddressId" placeholder="请选择收货地址" style="width: 100%">
        <el-option
          v-for="address in addresses"
          :key="address.id"
          :label="formatAddress(address)"
          :value="address.id"
        />
      </el-select>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addressDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmUpdateAddress">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ShoppingCart } from '@element-plus/icons-vue'
import { getOrders, deleteOrder, updateOrderStatus } from '@/api/order'
import { getUserAddresses } from '@/api/addresses'
import type { Address } from '@/api/addresses'

const drawerVisible = ref(false)
const cartItems = ref<any[]>([])
const addresses = ref<Address[]>([])
const addressDialogVisible = ref(false)
const selectedAddressId = ref('')
const currentOrderId = ref('')

// 获取购物车商品
const fetchCartItems = async () => {
  try {
    const res = await getOrders()
    if (res.code === 200) {
      cartItems.value = res.data.filter(order => order.status === 'unpaid')
    }
  } catch (error) {
    ElMessage.error('获取购物车商品失败')
  }
}

// 获取地址列表
const fetchAddresses = async () => {
  try {
    const res = await getUserAddresses()
    addresses.value = res.data
  } catch (error) {
    ElMessage.error('获取地址列表失败')
  }
}

// 删除商品
const deleteItem = async (orderId: string) => {
  try {
    await ElMessageBox.confirm('确定要删除这个商品吗？', '提示', {
      type: 'warning'
    })
    
    const res = await deleteOrder(orderId)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      await fetchCartItems()
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 支付订单
const payOrder = async (orderId: string) => {
  try {
    const res = await updateOrderStatus(orderId, '已支付')
    if (res.code === 200) {
      ElMessage.success('支付成功')
      await fetchCartItems()
    }
  } catch (error) {
    ElMessage.error('支付失败')
  }
}

// 更新地址
const updateAddress = (orderId: string) => {
  currentOrderId.value = orderId
  selectedAddressId.value = ''
  addressDialogVisible.value = true
}

// 确认更新地址
const confirmUpdateAddress = async () => {
  if (!selectedAddressId.value) {
    ElMessage.warning('请选择收货地址')
    return
  }

}

// 格式化地址显示
const formatAddress = (address: Address) => {
  return `${address.province} ${address.city} ${address.district} ${address.detail}`
}

onMounted(() => {
  fetchCartItems()
  fetchAddresses()
})
</script>

<style scoped>
.shopping-cart-drawer {
  position: fixed;
  right: 20px;
  bottom: 100px;
  z-index: 1000;
}

.cart-trigger {
  width: 50px;
  height: 50px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.cart-items {
  padding: 10px;
}

.cart-item {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.item-info {
  display: flex;
  gap: 15px;
}

.item-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.item-details {
  flex: 1;
}

.item-details h3 {
  margin: 0 0 5px 0;
  font-size: 16px;
}

.item-quantity {
  color: #666;
  margin: 5px 0;
}

.price {
  color: #ff6b6b;
  font-weight: bold;
  margin: 5px 0;
}

.address {
  color: #666;
  margin: 5px 0;
  font-size: 14px;
}

.item-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.empty-cart {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}
</style>
