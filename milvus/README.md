# Milvus 配置

- 拉取 Milvus Docker 镜像（本地命令行）

```shell
docker pull milvusdb/milvus:cpu-latest
```

- 运行 Milvus（本地命令行）

```shell
docker run -d --name milvus_cpu_1.1.1 -p 19530:19530 -p 19121:19121 milvusdb/milvus:cpu-latest
```

- 将 Spark 生成的 item_embedding 放入到当前目录中
- 运行 init.py
