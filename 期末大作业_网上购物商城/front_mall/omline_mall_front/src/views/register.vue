<template>
  <div class="register-container">
    <div class="register-box">
      <!-- 标题部分 -->
      <h1 class="main-title">线上简约购物商城</h1> <!-- 大标题 -->
      <h2 class="register-title">创建账号</h2> <!-- 注册标题 -->

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
          <input type="password" id="password" v-model="password" placeholder="请输入密码" class="input-field"
            :class="{ 'input-error': passwordError }" @blur="validatePassword" />
          <button class="password-toggle-btn" @click="togglePasswordVisibility">
            <span v-if="showPassword">🙈</span>
            <span v-else>👁️</span>
          </button>
        </div>
        <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
      </div>

      <!-- 确认密码输入框 -->
      <div class="input-group">
        <label for="confirmPassword" class="input-label">确认密码</label>
        <div class="password-container">
          <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="确认密码" class="input-field"
            :class="{ 'input-error': confirmPasswordError }" @blur="validateConfirmPassword" />
          <button class="password-toggle-btn" @click="toggleConfirmPasswordVisibility">
            <span v-if="showConfirmPassword">🙈</span>
            <span v-else>👁️</span>
          </button>
        </div>
        <p v-if="confirmPasswordError" class="error-message">{{ confirmPasswordError }}</p>
      </div>

      <!-- 注册按钮 -->
      <button @click="handleRegister" class="register-btn">注册</button>

      <!-- 返回登录链接 -->
      <div class="login-link">
        <span>已有账号？</span>
        <router-link to="/login" class="login-btn-link">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { register } from '@/api/user';  // 导入注册接口
import { ElMessage } from 'element-plus'; // 引入 Element Plus 消息提示

const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const usernameError = ref('');
const passwordError = ref('');
const confirmPasswordError = ref('');
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const router = useRouter();

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

// 确认密码校验
const validateConfirmPassword = () => {
  if (password.value !== confirmPassword.value) {
    confirmPasswordError.value = '两次密码输入不一致';
  } else {
    confirmPasswordError.value = '';
  }
};

// 校验表单
const validateForm = () => {
  validateUsername();
  validatePassword();
  validateConfirmPassword();

  return !usernameError.value && !passwordError.value && !confirmPasswordError.value;
};

// 切换密码显示状态
const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
  const passwordField = document.getElementById('password');
  passwordField.type = showPassword.value ? 'text' : 'password';
};

const toggleConfirmPasswordVisibility = () => {
  showConfirmPassword.value = !showConfirmPassword.value;
  const confirmPasswordField = document.getElementById('confirmPassword');
  confirmPasswordField.type = showConfirmPassword.value ? 'text' : 'password';
};

// 处理注册
const handleRegister = async () => {
  if (!validateForm()) return;  // 如果表单不通过校验，则不继续执行
  // 调用注册接口
  const response = await register(username.value, password.value);

  // 如果后端返回 code 为 200，说明注册成功
  if (response.code === 200) {
    ElMessage.success('注册成功'); // 显示后端返回的成功消息
    router.push('/login');  // 注册成功后跳转到登录页面
  }
};
</script>

<style scoped>
/* 页面背景图片和磨砂玻璃效果 */
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('../assets/【自然】2024-10-08 19_08_25.png') no-repeat center center/cover;
  /* 使用你的背景图片 */
  position: relative;
}

.register-box {
  background: rgba(255, 255, 255, 0.8);
  /* 背景磨砂玻璃效果 */
  padding: 40px;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  transition: transform 0.3s ease-in-out;
}

.register-box:hover {
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

.register-title {
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

/* 密码输入框按钮样式 */
.password-container {
  position: relative;
}

.password-toggle-btn {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #2575fc;
}

/* 按钮样式 */
.register-btn {
  width: 100%;
  padding: 12px;
  background-color: #2575fc;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
}

.register-btn:hover {
  background-color: #2168d0;
}

/* 返回登录的链接样式 */
.login-link {
  text-align: center;
  margin-top: 20px;
}

.login-btn-link {
  color: #2575fc;
  font-weight: 600;
  text-decoration: none;
}

.login-btn-link:hover {
  text-decoration: underline;
}
</style>
