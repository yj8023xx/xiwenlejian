<template>
    <div class="body">
        <div class="container">
            <div class="book-category">
                <h2>搜索结果</h2>
                <div class="book-list" v-if="books">
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
    name: 'Search',
    data() {
        return {
            keyword: '',
            books: []
        };
    },
    created() {
        this.keyword = this.$route.params.keyword;
        this.searchBooks();
    },
    methods: {
        searchBooks() {
            axios.get('http://127.0.0.1:8000/search/' + this.keyword, {
                params: {
                    pageNum: 0,
                    pageSize: 24,
                }
            }).then(response => {
                this.books = response.data.data;
            }).catch(error => {
                console.error(error);
            });
        }
    }
}
</script>