<template>
  <div class="login-container">
    <div class="login-overlay"></div> <!-- 背景覆盖层 -->
    <div class="login-box">
      <h1 class="main-title">线上简约购物商城</h1> <!-- 标题 -->

      <h2 class="login-title">登录</h2>

      <!-- 用户名输入框 -->
      <div class="input-group">
        <label for="username" class="input-label">用户名</label>
        <input type="text" id="username" v-model="username" placeholder="请输入用户名" class="input-field"
          :class="{ 'input-error': usernameError }" @blur="validateUsername" />
        <p v-if="usernameError" class="error-message">{{ usernameError }}</p>
      </div>

      <!-- 密码输入框 -->
      <div class="input-group">
        <label for="password" class="input-label">密码</label>
        <div class="password-container">
          <input :type="isPasswordVisible ? 'text' : 'password'" id="password" v-model="password" placeholder="请输入密码"
            class="input-field" :class="{ 'input-error': passwordError }" @blur="validatePassword" />
          <button type="button" @click="togglePasswordVisibility" class="password-toggle-btn">
            <span v-if="isPasswordVisible">🙈</span>
            <span v-else>👁️</span>
          </button>
        </div>
        <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
      </div>

      <!-- 登录按钮 -->
      <button @click="handleLogin" class="login-btn">登录</button>

      <!-- 返回注册页面链接 -->
      <div class="register-link">
        <span>没有账号？</span>
        <router-link to="/register" class="register-btn-link">注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/user' // 导入登录接口
import { ElMessage } from 'element-plus' // 引入 Element Plus 消息提示
import { useTokenStore } from '@/stores/token' // 导入 token 状态管理

// 设置状态
const username = ref('')
const password = ref('')
const usernameError = ref('')
const passwordError = ref('')
const isPasswordVisible = ref(false) // 控制密码是否可见
const router = useRouter()

// 用户名校验
const validateUsername = () => {
  const usernamePattern = /^[A-Za-z0-9_-]{6,16}$/;  // 6-16位字母、数字、下划线和短横线
  if (!username.value) {
    usernameError.value = '用户名不能为空';
  } else if (!usernamePattern.test(username.value)) {
    usernameError.value = '用户名必须为6-16位，且只能包含字母、数字、下划线和短横线';
  } else {
    usernameError.value = '';
  }
}

// 密码校验
const validatePassword = () => {
  const passwordPattern = /^[A-Za-z0-9!@#$%^&*()_+={}\[\]:;"'<>,.?/\\|`~]{6,16}$/;  // 6-16位字母、数字和常见符号
  if (!password.value) {
    passwordError.value = '密码不能为空';
  } else if (!passwordPattern.test(password.value)) {
    passwordError.value = '密码必须为6-16位，且只能包含字母、数字和标准符号';
  } else {
    passwordError.value = '';
  }
}

// 校验表单
const validateForm = () => {
  validateUsername()
  validatePassword()

  return !usernameError.value && !passwordError.value
}

// 处理登录
// 处理登录
const handleLogin = async () => {
  if (!validateForm()) return; // 如果表单不通过校验，则不继续执行

  try {
    // 调用登录接口
    const response = await login(username.value, password.value);

    // 在这里检查 response 对象是否为空或者没有 code 字段
    if (response && response.code === 200) {
      // 登录成功，显示消息
      ElMessage.success(response.message);
      
      // 获取 token 并存入 Pinia store
      const token = response.data?.token;
      if (token) {
        const tokenStore = useTokenStore();
        tokenStore.setToken(token); // 将 token 存入 Pinia
      }

      // 清理表单数据
      username.value = '';
      password.value = '';
      localStorage.removeItem('username');
      localStorage.removeItem('password');

      // 登录成功后跳转到产品页面
      router.push('/mall');
    }
  } catch (error) {
    ElMessage.error('登录失败，请稍后重试');
    console.error('登录错误:', error);
  }
}

// 切换密码可见性
const togglePasswordVisibility = () => {
  isPasswordVisible.value = !isPasswordVisible.value;
}

</script>

<style scoped>
/* 页面背景设置为图片 */
.login-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('../assets/【自然】2024-10-08 19_08_25.png');
  /* 设置背景图片 */
  background-size: cover;
  /* 背景图片覆盖整个容器 */
  background-position: center;
  background-repeat: no-repeat;
}

/* 背景覆盖层 - 磨砂玻璃效果 */
.login-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.4);
  /* 半透明背景 */
  backdrop-filter: blur(10px);
  /* 磨砂玻璃效果 */
  z-index: -1;
}

/* 登录框样式 */
.login-box {
  background-color: rgba(255, 255, 255, 0.8);
  /* 半透明背景 */
  padding: 40px;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  transition: transform 0.3s ease-in-out;
}

.login-box:hover {
  transform: scale(1.01);
  /* 鼠标悬停时缩放 */
}

/* 标题样式 */
.main-title {
  font-size: 32px;
  text-align: center;
  margin-bottom: 20px;
  color: #333;
  font-weight: 600;
  font-family: 'Arial', sans-serif;
}

.login-title {
  font-size: 26px;
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-weight: 600;
  font-family: 'Arial', sans-serif;
}

/* 输入框组样式 */
.input-group {
  margin-bottom: 20px;
}

/* 输入框标签样式 */
.input-label {
  font-size: 14px;
  color: #555;
  margin-bottom: 5px;
  font-weight: 500;
}

/* 输入框样式 */
.input-field {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.3s ease;
}

.input-field:focus {
  border-color: #2575fc;
  /* 聚焦时的边框颜色 */
  outline: none;
}

.input-error {
  border-color: #e74c3c;
  /* 错误时的边框颜色 */
}

.error-message {
  color: #e74c3c;
  font-size: 12px;
  margin-top: 5px;
}

/* 密码可见性切换按钮样式 */
.password-container {
  position: relative;
}

.password-toggle-btn {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #2575fc;
}

.password-toggle-btn:focus {
  outline: none;
}

/* 按钮样式 */
.login-btn {
  width: 100%;
  padding: 12px;
  background-color: #2575fc;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.login-btn:hover {
  background-color: #6a11cb;
  /* 鼠标悬停时按钮颜色 */
  transform: scale(1.05);
}

/* 返回注册按钮样式 */
.register-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
}

.register-btn-link {
  color: #2575fc;
  font-weight: 600;
  cursor: pointer;
  text-decoration: none;
  transition: color 0.3s ease;
}

.register-btn-link:hover {
  color: #6a11cb;
  /* 鼠标悬停时改变颜色 */
}
</style>
