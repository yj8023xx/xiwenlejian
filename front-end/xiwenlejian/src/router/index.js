import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue'
import Book from '@/views/Book.vue';
import User from '@/views/User.vue';
import Search from '@/views/Search.vue'
import Login from '@/views/Login.vue'

const routes = [
    {
        path: '/',
        redirect: '/home', // 重定向到 /home 路由
    },
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/book/:id', // 使用动态参数 :id
        name: 'Book',
        component: Book,
        props: true // 将动态参数作为组件的 props 传递
    },
    {
        path: '/user/:id', // 使用动态参数 :id
        name: 'User',
        component: User,
        props: true // 将动态参数作为组件的 props 传递
    },
    {
        path: '/search/:keyword', // 使用动态参数 :id
        name: 'Search',
        component: Search,
        props: true // 将动态参数作为组件的 props 传递
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;