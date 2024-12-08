// 引入request
import request from '@/utils/request'

// 商品接口

// 获取全部商品信息
export function getProducts(page: number, Size: number): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get('/products' + '?page=' + page + '&size=' + Size)
}

// 根据关键字搜索商品
export function searchProduct(page: number, Size: number, keyword: string): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get(`/products/search?keyword=${keyword}&page=${page}&size=${Size}`)
}

// 获取商品详情
export function getProductById(id: number): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get(`/products/detail?id=${id}`)
}

// 根据分类获取商品列表
export function getProductsByCategoryId(categoryId: number, page: number, size: number): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get(`/products/category?categoryId=${categoryId}&page=${page}&size=${size}`)
}

// 查询商品的平均分
export function getAvgRating(productId: number): Promise<{
  code: number;
  data: number;
  message?: string;
}> {
  return request.get(`/reviews/avg_rating/${productId}`)
}

// 查询商品的全部评价及分数
export function getAllReviews(productId: number): Promise<{
  code: number;
  data: ReviewsDto[];
  message?: string;
}> {
  return request.get(`/reviews/all_reviews/${productId}`)
}

// 定义 ReviewsDto 类型
interface ReviewsDto {
  product_id: number;  // 商品 ID
  rating: number;      // 评分
  comment: string;     // 评价内容
  created_at?: string; // 添加可选的创建时间属性
}

// 添加评价的请求函数
export function addReview(reviewsDto: ReviewsDto): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.post('/reviews/add_reviews', reviewsDto);
}


// 添加分类
export function addCategory(name: string): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.post('/categories/add', { name })
}

// 根据分类 ID 查询分类
export function getCategoryById(id: number): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get(`/categories/byId/${id}`)
}

// 获取所有分类
export function getAllCategories(): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.get('/categories')
}

// 更新订单地址
export function updateOrderAddress(orderId: string, addressId: number): Promise<{
  code: number;
  data: any;
  message?: string;
}> {
  return request.put(`/orders/update_address`, { orderId, addressId })
}