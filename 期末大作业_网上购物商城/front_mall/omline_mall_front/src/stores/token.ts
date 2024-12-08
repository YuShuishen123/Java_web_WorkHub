import { defineStore } from 'pinia'
import { ref } from 'vue'

// 直接定义 store 即可
export const useTokenStore = defineStore('token', () => {
  const token = ref<string>('');

  const setToken = (newToken: string) => {
    token.value = newToken;
  };

  const getToken = () => {
    return token.value;
  };

  return { token, setToken, getToken };
}, {
  persist: true
});