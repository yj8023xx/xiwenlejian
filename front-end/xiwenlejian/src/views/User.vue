<template>
    <div class="body">
        <div class="container" v-if="user">
            <div class="user-detail">
                <img src="../assets/images/avatar_2.png" alt="书籍封面">
                <div class="user-detail-content">
                    <h2>{{ user.username }}</h2>
                    <p>用户ID: {{ user.userId }}</p>
                    <p>地址: {{ user.location }}</p>
                    <p>年龄: {{ user.age }}</p>
                </div>
            </div>
            <div class="book-category">
                <h2>看过的书</h2>
                <div class="book-list" v-if="historyBooks">
                    <div class="book-item" v-for="book in historyBooks" :key="book.bookId">
                        <router-link :to="'/book/' + book.bookId">
                            <img :src="book.imageUrlL" alt="书籍封面">
                        </router-link>
                        <h3>{{ book.title }}</h3>
                        <div class="rating">
                            <span class="star">&#9733;</span>
                            <span class="rating-score">{{ book.averageRating }}</span>
                        </div>
                    </div>
                    <!-- 添加更多书籍项 -->
                </div>
            </div>
            <div class="book-category">
                <h2>猜你喜欢</h2>
                <div class="book-list" v-if="recommendBooks">
                    <div class="book-item" v-for="book in recommendBooks" :key="book.bookId">
                        <router-link :to="'/book/' + book.bookId">
                            <img :src="book.imageUrlL" alt="书籍封面">
                        </router-link>
                        <h3>{{ book.title }}</h3>
                        <div class="rating">
                            <span class="star">&#9733;</span>
                            <span class="rating-score">{{ book.averageRating }}</span>
                        </div>
                    </div>
                    <!-- 添加更多书籍项 -->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: "User",
    data() {
        return {
            userId: null,
            user: null,
            historyBooks: [],
            recommendBooks: []
        };
    },
    created() {
        this.userId = this.$route.params.id
        this.getUserInfo();
        this.getHistoryBooks();
        this.getRecommendBooks();
    },
    methods: {
        getUserInfo() {
            axios.get('http://127.0.0.1:8000/user/' + this.userId).then(response => {
                this.user = response.data;
            }).catch(error => {
                console.error(error);
            });
        },
        getHistoryBooks() {
            axios.get('http://127.0.0.1:8000/book/history/' + this.userId, {
                params: {
                    size: 12
                }
            }).then(response => {
                this.historyBooks = response.data;
            }).catch(error => {
                console.error(error);
            });
        },
        getRecommendBooks() {
            axios.get('http://127.0.0.1:8000/book/recommend/' + this.userId, {
                params: {
                    size: 24
                }
            }).then(response => {
                this.recommendBooks = response.data;
            }).catch(error => {
                console.error(error);
            });
        }
    }
}
</script>

<style>
.user-detail {
    display: flex;
    align-items: flex-start;
    margin-bottom: 40px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.user-detail img {
    width: 180px;
    aspect-ratio: 3/4;
    margin-right: 20px;
}

.user-detail-content {
    flex-grow: 1;
}

.user-detail h2 {
    color: #333;
    font-size: 24px;
    margin-bottom: 10px;
    margin-top: 0;
}

.user-detail p {
    color: #666;
    font-size: 14px;
    margin-bottom: 20px;
}
</style>