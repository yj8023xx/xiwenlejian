<template>
    <div class="body">
        <div class="container">
            <h2>登录</h2>
            <form id="loginForm" @submit.prevent="login">
                <input type="text" id="username" v-model="username" placeholder="用户名" required>
                <input type="password" id="password" v-model="password" placeholder="密码" required>
                <button type="submit">登录</button>
            </form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            username: '',
            password: ''
        };
    },
    methods: {
        login() {
            // 构造登录请求的数据
            const userDTO = {
                username: this.username,
                password: this.password
            };
            // 发送登录请求
            axios.post('http://127.0.0.1:9105/user/login', userDTO)
                .then(response => {
                    var user = response.data
                    // 处理登录成功的逻辑，如保存登录状态、跳转到其他页面等
                    this.$router.push({ name: 'User', params: { id: user.userId } });
                })
                .catch(error => {
                    // 处理登录失败的逻辑，如显示错误信息等
                    console.error('登录失败', error);
                });
        }
    }
};
</script>

<style scoped>
.body {
    font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.container {
    width: 300px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 20px;
    animation: slide-in 0.3s ease-in-out;
}

.container h2 {
    text-align: center;
    color: #333;
    margin-bottom: 20px;
}

.container input[type="text"],
.container input[type="password"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: none;
    border-radius: 3px;
    box-sizing: border-box;
    background-color: #f2f2f2;
    transition: background-color 0.3s ease-in-out;
}

.container input[type="text"]:focus,
.container input[type="password"]:focus {
    outline: none;
    background-color: #e0e0e0;
}

.container button {
    width: 100%;
    padding: 10px;
    background-color: #4CAF50;
    color: #fff;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    transition: background-color 0.3s ease-in-out;
}

.container button:hover {
    background-color: #45a049;
}

@keyframes slide-in {
    from {
        transform: translateX(-50%);
        opacity: 0;
    }

    to {
        transform: translateX(0);
        opacity: 1;
    }
}
</style>