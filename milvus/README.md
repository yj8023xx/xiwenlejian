# Milvus 部署

- 拉取 Milvus Docker 镜像（本地命令行）

```
docker pull milvusdb/milvus:cpu-latest
```

- 运行 Milvus（本地命令行）

```
docker run -d --name milvus_cpu_1.1.1 -p 19530:19530 -p 19121:19121 milvusdb/milvus:cpu-latest
```

- 运行 xiwenlejian-recommender 项目下的 Item2Vec.scala，会在项目根目录下生成 item_embedding 文件夹，将其中的文件重命名为 item_embedding.csv 并放在 milvus 目录下
- 运行 init.py
