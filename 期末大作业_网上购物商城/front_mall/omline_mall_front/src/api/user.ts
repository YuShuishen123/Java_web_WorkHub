// 引入request
import request from '@/utils/request'


// 登录接口
export function login(username: string, password: string) {
  const params = new URLSearchParams();
  params.append('username', username);
  params.append('password', password);
  
  return request.post('/users/login', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

// 注册接口
export function register(username: string, password: string) {
  const params = new URLSearchParams();
  params.append('username', username);
  params.append('password', password);
  
  return request.post('/users/register', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

// 登出接口
export function logout() {
  return request.post('/users/logout', null, {
  });
}

// 获取用户信息接口
export function getUserInfo() {
  return request.get('/users/info');
}

// 注销账号
export function deleteAccount() {
  return request.delete('/users/delete');
}

// 用户更新信息的 DTO 类型
interface userUpdateDTO {
  password?: string;  // 密码，非必填
  email?: string;     // 邮箱，格式为 email
  phone?: string;     // 电话
  nickname?: string;  // 昵称
}

// 修改用户个人信息
export function updateUserInfo(userUpdateDTO: userUpdateDTO) {
  return request.put('/users/update', userUpdateDTO);
}

// 更新用户头像
export function changeAvatar(file: File) {
  const formData = new FormData();
  formData.append('file', file);

  return request.post('/users/change_avatar', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}



