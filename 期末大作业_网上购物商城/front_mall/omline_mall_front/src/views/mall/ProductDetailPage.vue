<template>
  <div class="product-detail-page">
    <div class="product-container">
      <div class="product-info">
        <div class="product-image">
          <el-image :src="product?.image_url" fit="cover" />
        </div>
        <div class="product-details">
          <h1>{{ product?.name }}</h1>
          <div class="rating">
            <el-rate
              v-model="avgRating"
              disabled
              show-score
              text-color="#ff9900"
            />
          </div>
          <div class="price">¥{{ product?.price }}</div>
          <div class="stock">库存: {{ product?.stock || 0 }}</div>
          <div class="description">{{ product?.description }}</div>
          <div class="actions">
            <el-input-number 
              v-model="quantity" 
              :min="1" 
              :max="isPaid ? (product?.stock || 1) : 99"
              :disabled="isPaid && !product?.stock"
            />
            <el-button 
              type="primary" 
              @click="showOrderDialog"
              :disabled="isPaid && (!product?.stock || quantity > product?.stock)"
              :title="isPaid && !product?.stock ? '商品库存不足' : ''"
            >立即购买</el-button>
            <el-button 
              @click="showOrderDialog(false)"
            >加入购物车</el-button>
          </div>
        </div>
      </div>

      <!-- 评价区域 -->
      <div class="reviews-section">
        <h2>商品评价</h2>
        <div v-if="reviews.length === 0" class="no-reviews">
          暂无评价
        </div>
        <div v-else class="reviews-list">
          <div v-for="review in reviews" :key="review.id" class="review-item">
            <div class="review-header">
              <span class="reviewer">{{ review.username }}</span>
              <el-rate v-model="review.rating" disabled />
              <span class="review-date">{{ formatDate(review.created_at) }}</span>
            </div>
            <div class="review-content">
              {{ review.comment }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 订单对话框 -->
    <el-dialog
      v-model="orderDialogVisible"
      :title="isPaid ? '立即购买' : '加入购物车'"
      width="30%"
    >
      <el-form :model="orderForm" label-width="80px">
        <el-form-item label="商品数量">
          <el-input-number 
            v-model="quantity" 
            :min="1" 
            :max="isPaid ? (product?.stock || 1) : 99"
            @change="handleQuantityChange"
          />
        </el-form-item>
        <el-form-item label="总价">
          <span class="total-price">¥{{ totalPrice }}</span>
        </el-form-item>
        <el-form-item label="收货地址">
          <el-select v-model="orderForm.address_id" placeholder="请选择收货地址" style="width: 100%">
            <el-option
              v-for="address in addresses"
              :key="address.id"
              :label="formatAddress(address)"
              :value="address.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="orderForm.order_note"
            type="textarea"
            placeholder="请输入备注信息（选填）"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="orderDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitOrder">
            {{ isPaid ? '确认购买' : '加入购物车' }}
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.product-detail-page {
  padding: 40px 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.product-container {
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.product-info {
  display: flex;
  gap: 40px;
  margin-bottom: 40px;
}

.product-image {
  flex: 0 0 400px;
  height: 400px;
  border-radius: 8px;
  overflow: hidden;
}

.product-image :deep(.el-image) {
  width: 100%;
  height: 100%;
}

.product-details {
  flex: 1;
}

.product-details h1 {
  margin: 0 0 20px 0;
  font-size: 24px;
  color: #333;
}

.rating {
  margin-bottom: 20px;
}

.price {
  font-size: 28px;
  color: #ff6b6b;
  font-weight: bold;
  margin-bottom: 20px;
}

.stock {
  font-size: 16px;
  color: #666;
  margin: 10px 0;
}

.description {
  color: #666;
  line-height: 1.6;
  margin-bottom: 30px;
}

.actions {
  display: flex;
  gap: 20px;
  align-items: center;
}

/* 评价区域样式 */
.reviews-section {
  margin-top: 40px;
  border-top: 1px solid #eee;
  padding-top: 30px;
}

.reviews-section h2 {
  margin: 0 0 20px 0;
  font-size: 20px;
  color: #333;
}

.no-reviews {
  text-align: center;
  color: #999;
  padding: 40px 0;
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.review-item {
  padding: 20px;
  border-radius: 8px;
  background: #f8f9fa;
}

.review-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 10px;
}

.reviewer {
  font-weight: bold;
  color: #333;
}

.review-date {
  color: #999;
  font-size: 14px;
}

.review-content {
  color: #666;
  line-height: 1.6;
}

.total-price {
  font-size: 20px;
  color: #ff6b6b;
  font-weight: bold;
}
</style>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getProductById, getAvgRating, getAllReviews } from '@/api/mall'
import { createOrder } from '@/api/order'
import { getUserAddresses } from '@/api/addresses'
import type { Address } from '@/api/addresses'
import { ElMessage } from 'element-plus'

const route = useRoute()
const product = ref<any>(null)
const avgRating = ref(0)
const reviews = ref<any[]>([])
const quantity = ref(1)
const addresses = ref<Address[]>([])
const orderDialogVisible = ref(false)
const isPaid = ref(true)
const orderForm = ref({
  address_id: '',
  order_note: ''
})
const totalPrice = ref(0)

// 格式化地址显示
const formatAddress = (address: Address) => {
  return `${address.province} ${address.city} ${address.district} ${address.detail}`
}

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return '暂无日期'
  
  try {
    const date = new Date(dateStr)
    if (!isNaN(date.getTime())) {
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      }).replace(/\//g, '-')
    }
    return '日期格式错误'
  } catch (error) {
    console.error('日期格式化错误:', error, dateStr)
    return '日期格式错误'
  }
}

// 显示订单对话框
const showOrderDialog = (paid = true) => {
  if (addresses.value.length === 0) {
    ElMessage.warning('请先添加收货地址')
    return
  }
  isPaid.value = paid
  orderDialogVisible.value = true
  calculateTotal()
}

// 处理数量变化
const handleQuantityChange = (value: number) => {
  if (isPaid.value && product.value && value > product.value.stock) {
    ElMessage.warning('超出商品库存数量')
    quantity.value = product.value.stock
  }
  calculateTotal()
}

// 计算总价
const calculateTotal = () => {
  if (product.value && quantity.value) {
    totalPrice.value = parseFloat((product.value.price * quantity.value).toFixed(2))
  }
}

// 获取商品信息
const fetchProductInfo = async () => {
  try {
    const productId = Number(route.params.id)
    const productRes = await getProductById(productId)
    if (productRes.code === 200) {
      product.value = productRes.data
      calculateTotal()
    } else {
      ElMessage.error('获取商品详情失败')
    }
  } catch (error) {
    console.error('获取商品详情失败:', error)
    ElMessage.error('获取商品详情失败')
  }
}

// 提交订单
const submitOrder = async () => {
  if (!orderForm.value.address_id) {
    ElMessage.warning('请选择收货地址')
    return
  }

  // 只在立即购买时检查库存
  if (isPaid.value && (!product.value?.stock || quantity.value > product.value.stock)) {
    ElMessage.error('商品库存不足')
    return
  }

  try {
    const orderData = {
      address_id: parseInt(orderForm.value.address_id),
      product_id: parseInt(product.value.id),
      quantity: quantity.value,
      total_amount: totalPrice.value.toString(),  
      order_status: isPaid.value ? '已支付' : '未支付',
      order_note: orderForm.value.order_note || ''
    }

    console.log('提交订单数据:', orderData)
    const res = await createOrder(orderData)
    if (res.code === 200) {
      ElMessage.success(isPaid.value ? '下单并支付成功' : '已加入购物车')
      orderDialogVisible.value = false
      orderForm.value.address_id = ''
      orderForm.value.order_note = ''
      quantity.value = 1
      
      // 重新获取商品信息以更新库存
      await fetchProductInfo()
    } else {
      ElMessage.error(res.message || '下单失败')
    }
  } catch (error) {
    console.error('提交订单失败:', error)
    ElMessage.error('下单失败，请重试')
  }
}

onMounted(async () => {
  try {
    // 1. 获取商品详情
    await fetchProductInfo()

    // 2. 立即获取地址列表（不依赖于评价）
    try {
      const addressRes = await getUserAddresses()
      console.log('地址列表响应:', addressRes)
      if (addressRes.code === 200) {
        addresses.value = addressRes.data || []
        if (addresses.value.length === 0) {
          ElMessage.warning('您还没有添加收货地址，请先添加地址')
        }
      } else {
        console.error('获取地址列表失败:', addressRes)
        ElMessage.error('获取地址列表失败')
      }
    } catch (addressError) {
      console.error('获取地址列表错误:', addressError)
      ElMessage.error('获取地址列表失败')
    }

    // 3. 获取评分和评价（即使失败也不影响其他功能）
    try {
      const ratingRes = await getAvgRating(Number(route.params.id))
      if (ratingRes.code === 200) {
        avgRating.value = ratingRes.data || 0
      }

      const reviewsRes = await getAllReviews(Number(route.params.id))
      console.log('评价列表响应:', reviewsRes)
      if (reviewsRes.code === 200 && reviewsRes.data) {
        reviews.value = reviewsRes.data.map(review => ({
          ...review
        }))
        console.log('处理后的评价数据:', reviews.value)
      }
    } catch (reviewError) {
      console.error('获取评价信息失败:', reviewError)
      reviews.value = []
      avgRating.value = 0
    }
  } catch (error) {
    console.error('初始化数据失败:', error)
    ElMessage.error('加载数据失败，请刷新页面重试')
  }
})
</script>
