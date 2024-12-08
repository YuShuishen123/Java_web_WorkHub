import { createRouter, createWebHistory } from 'vue-router'

// 引入组件
import login from '../views/login.vue'
import register from '../views/register.vue'
import search from '@/views/search.vue'
import Homepage from '@/views/Homepage.vue'
import mall from '@/views/mall.vue'
import categorybyid from '@/views/utile/categorybyid.vue'
import ProductsManager from '@/views/ProductsManager/ProductsManager.vue'
import info from '@/views/users/info.vue'
import ProductDetailPage from '@/views/mall/ProductDetailPage.vue'
// 定义路由
const routes = [
  {
    path: '/login',
    component: login
  },
  {
    path: '/register',
    component: register
  },
  {
    // 设置 mall 作为根路径，所有子路径都基于 /mall
    path: '/mall',
    component: mall,
    name: 'mall',
    redirect: '/mall/homepage', // 默认重定向到 /mall/homepage
    children: [
      {
        path: 'homepage', // 默认的子路由
        component: Homepage,
        name: 'mall-homepage'
      },
      {
        path: 'search',
        component: search,
        name: 'search'
      },
      {
        path: 'categorybyid',
        component: categorybyid,
        name: 'categorybyid'
      },
      {
        path: 'productsmanager',
        component: ProductsManager,
        name: 'productsmanager'
      },
      {
        path: 'info',
        component: info,
        name: 'info'
      },
      {
        path: 'product/:id',
        component: ProductDetailPage,
        name: 'product-detail'
      },
      {
        path: 'productDetail',
        component: ProductDetailPage,
        name: 'productDetail'
      }
    ]
  },
  // 其他路径可以单独定义
  {
    path: '/',
    redirect: '/mall'  // 默认重定向到 /mall
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router
