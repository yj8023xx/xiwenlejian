<template>
    <div class="body" v-if="book">
        <div class="container">
            <div class="book-detail">
                <img :src="book.imageUrlL" alt="书籍封面">
                <div class="book-detail-content">
                    <h2>{{ book.title }}</h2>
                    <p>ISBN: {{ book.isbn }}</p>
                    <p>评分: {{ book.averageRating }}</p>
                    <p>作者: {{ book.author }}</p>
                    <p>发行日期: {{ book.releaseYear }} 年</p>
                    <p>出版社: {{ book.publisher }}</p>
                </div>
            </div>
            <div class="book-category">
                <h2>喜欢这本书的人也喜欢</h2>
                <div class="book-list" v-if="similarBooks">
                    <div class="book-item" v-for="book in similarBooks" :key="book.bookId">
                        <a :href="'/book/' + book.bookId" @click="goToBookDetails(book.bookId)">
                            <img :src="book.imageUrlL" alt="书籍封面">
                        </a>
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
    name: 'Book',
    data() {
        return {
            bookId: null,
            book: null,
            similarBooks: []
        };
    },
    created() {
        this.bookId = this.$route.params.id;
        this.getBookDetails();
        this.getSimilarBooks();
    },
    methods: {
        goToBookDetails(bookId) {
            this.$router.push({ name: 'Book', params: { id: bookId } });
        },
        getBookDetails() {
            axios.get('http://127.0.0.1:8000/book/' + this.bookId).then(response => {
                this.book = response.data;
            }).catch(error => {
                console.error(error);
            });
        },
        getSimilarBooks() {
            axios.get('http://127.0.0.1:8000/book/similar/' + this.bookId, {
                params: {
                    size: 12
                }
            }).then(response => {
                this.similarBooks = response.data;
            }).catch(error => {
                console.error(error);
            });
        }
    }
}
</script>

<style>
.book-detail {
    display: flex;
    align-items: flex-start;
    margin-bottom: 40px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.book-detail img {
    width: 180px;
    aspect-ratio: 3/4;
    margin-right: 20px;
}

.book-detail-content {
    flex-grow: 1;
}

.book-detail h2 {
    color: #333;
    font-size: 24px;
    margin-bottom: 10px;
    margin-top: 0;
}

.book-detail p {
    color: #666;
    font-size: 14px;
    margin-bottom: 20px;
}
</style>