# ElasticSearch 配置

**curl**

```shell
curl -XPUT -H 'Content-Type: application/json' http://localhost:9200/book -d '{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "detail": {
      "properties": {
        "book_id": {
          "type": "long"
        },
        "isbn": {
          "type": "text"
        },
        "title": {
          "type": "keyword"
        },
        "author": {
          "type": "text"
        },
        "release_year": {
          "type": "integer"
        },
        "publisher": {
          "type": "text"
        },
        "average_rating": {
          "type": "double"
        },
        "image_url_s": {
          "type": "text"
        },
        "image_url_m": {
          "type": "text"
        },
        "image_url_l": {
          "type": "text"
        }
      }
    }
  }
}'
```

插入数据

```shell
curl -XPUT "http://localhost:9200/_bulk" -H 'Content-Type: application/json' --data-binary @book.json
```

查询数据

```shell
curl -XGET -H 'Content-Type: application/json' http://localhost:9200/book/_search -d '{
  "query": {
    "match_all": {
    }
  }
}'
```

**Kibana**

创建索引

```shell
PUT book
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "detail": {
      "properties": {
        "book_id": {
          "type": "long"
        },
        "isbn": {
          "type": "text"
        },
        "title": {
          "type": "keyword"
        },
        "author": {
          "type": "text"
        },
        "release_year": {
          "type": "integer"
        },
        "publisher": {
          "type": "text"
        },
        "average_rating": {
          "type": "double"
        },
        "image_url_s": {
          "type": "text"
        },
        "image_url_m": {
          "type": "text"
        },
        "image_url_l": {
          "type": "text"
        }
      }
    }
  }
}
```

插入数据

```shell
POST book/detail/_bulk
{"index":{"_index":"book","_type":"detail"}}
{"book_id":1,"isbn":"0195153448","title":"Classical Mythology","author":"Mark P. O. Morford","release_year":2002,"publisher":"Oxford University Press","average_rating":0,"image_url_s":"http://images.amazon.com/images/P/0195153448.01.THUMBZZZ.jpg","image_url_m":"http://images.amazon.com/images/P/0195153448.01.MZZZZZZZ.jpg","image_url_l":"http://images.amazon.com/images/P/0195153448.01.LZZZZZZZ.jpg"}
```

查询数据

```shell
GET book/_search 
{
  "query":{
    "match_all":{}
  }
}
```
