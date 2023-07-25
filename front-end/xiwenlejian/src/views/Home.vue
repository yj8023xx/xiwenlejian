<template>
    <div class="body" v-if="books">
        <div class="container">
            <div class="book-category">
                <h2>高分图书榜</h2>
                <div class="book-list">
                    <div class="book-item" v-for="book in books" :key="book.bookId">
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
    name: 'Home',
    data() {
        return {
            books: []
        };
    },
    created() {
        this.getHighRatedBooks();
    },
    methods: {
        getHighRatedBooks() {
            axios.get('http://127.0.0.1:9104/book/highrated', {
                params: {
                    size: 18
                }
            }).then(response => {
                this.books = response.data;
            }).catch(error => {
                console.error(error);
            });
        }
    }
}
</script>