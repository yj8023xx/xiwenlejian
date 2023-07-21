import { createApp } from 'vue';
import App from './App.vue';  // 导入根组件 App.vue
import router from './router'; // 导入 Vue Router 配置
import '@fortawesome/fontawesome-free/css/all.css';
import './assets/styles/main.css'

const app = createApp(App);
app.use(router); // 注入路由配置
app.mount('#app'); // 挂载到 DOM 元素上