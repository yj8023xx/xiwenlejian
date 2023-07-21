<template>
    <div class="app">
        <!-- 导航栏 -->
        <div class="header">
            <div class="container">
                <div class="header-content">
                    <router-link class="logo" :to="{ name: 'Home' }">喜文乐荐</router-link>
                    <div class="search-bar">
                        <form class="search-form" @submit.prevent="search">
                            <input type="text" v-model="keyword" placeholder="搜索图书">
                            <button type="submit"><i class="fas fa-search"></i></button>
                        </form>
                    </div>
                    <div class="user-info">
                        <div class="avatar">
                            <router-link to="/login">
                                <img src="../src/assets/images/avatar.png" alt="用户头像">
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 主要内容区域，通常用于展示页面内容 -->
        <router-view :key="$route.fullPath"></router-view>

        <!-- 底部页脚 -->
        <footer class="footer">
            <div class="container">
                <div class="footer-content">
                    <div class="contact-info">
                        <h3>联系方式</h3>
                        <div class="social-links">
                            <a href="mailto:913660289@qq.com"><i class="fas fa-envelope"></i></a>
                            <a href="https://github.com/yj8023xx" target="_blank"><i class="fab fa-github"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</template>

<script>
export default {
    name: 'App',
    data() {
        return {
            keyword: '', // 存储搜索关键词
        };
    },
    methods: {
        search() {
            // 使用编程式导航跳转到 search 页面，并携带 keyword 参数
            this.$router.push({ name: 'Search', params: { keyword: this.keyword } });
        },
    }
};
</script>