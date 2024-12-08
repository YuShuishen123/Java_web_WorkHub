// 定制请求实例
import axios from 'axios'
import { useTokenStore } from '@/stores/token'
import router from '@/router'
import { ElMessage } from 'element-plus'; // 引入 Element Plus 消息提示

// 定义一个变量，记录公共的前缀
const baseURL = '/api'

// 创建一个axios实例
const instance = axios.create({ baseURL })

// 添加请求拦截器
instance.interceptors.request.use(
  config => {
    const tokenStore = useTokenStore()
    const token = tokenStore.getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  }, error => {
    // 对请求错误做些什么
    return Promise.reject(error)
  })

// 添加响应拦截器 
instance.interceptors.response.use(
  result => {
    // 判断返回的数据结构和状态码
    const data = result.data
    if (data.code === 200) {
      // 登录成功时，显示 success 提示
      return data
    }
    else {
      // 登录失败时，显示 error 提示
      ElMessage.error(data.message || '操作失败');
      return Promise.reject(data)
    }
  }, error => {
    // 如果请求失败（网络错误或其他原因），如果为了401，则跳转到登录页面
    if (error.response.status === 401) {
      ElMessage.error('请先登录');
      router.push('/login') // 跳转到登录页面
    } else if (error.response.status === 403) {
      ElMessage.error('权限不足');
    } else if (error.response.status === 500) {
      ElMessage.error('服务器错误');
    } else {
      ElMessage.error('请求失败');
    }


    return Promise.reject(error)
  })

export default instance  // 导出这个实例
