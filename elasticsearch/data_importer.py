from elasticsearch import Elasticsearch
import mysql.connector

# 连接到 Elasticsearch
es = Elasticsearch(['http://localhost:9200'])

# 连接到 MySQL 数据库
conn = mysql.connector.connect(
    host='localhost',
    port='3306',
    database='xiwenlejian',
    user='root',
    password='580230'
)

# 创建游标对象
cur = conn.cursor(dictionary=True)

# 执行数据库查询
cur.execute('SELECT book_id,isbn,title,author,release_year,publisher,average_rating,image_url_s,image_url_m,image_url_l FROM book')

# 获取所有检索结果
results = cur.fetchall()

# 将数据逐条导入 Elasticsearch
for row in results:
    doc = {
        'book_id': row['book_id'],
        'isbn': row['isbn'],
        'title': row['title'],
        'author': row['author'],
        'release_year': row['release_year'],
        'publisher': row['publisher'],
        'average_rating': row['average_rating'],
        'image_url_s': row['image_url_s'],
        'image_url_m': row['image_url_m'],
        'image_url_l': row['image_url_l']
    }
    # 将文档插入 Elasticsearch
    es.index(index='book', doc_type="detail", body=doc)

# 关闭数据库连接
cur.close()
conn.close()
