import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import App from './App.vue';
import locale from 'element-plus/dist/locale/zh-cn.js'
import router from './router'
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persistedstate';
import '@/style.css'

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPersist);  // 使用持久化插件
app.use(pinia); // 注意：删除 app.use(createPinia())，只使用上面创建的 pinia 实例
app.use(router);
app.use(ElementPlus, { locale });
app.mount('#app');