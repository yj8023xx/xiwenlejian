# Milvus 配置

拉取镜像

```shell
docker pull milvusdb/milvus:cpu-latest
```

运行 milvus

```shell
docker run -d --name milvus_cpu_1.1.1 -p 19530:19530 -p 19121:19121 milvusdb/milvus:cpu-latest
```

