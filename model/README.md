# 模型训练与部署

## 特征工程

- 提前下载好 Book-Crossing 数据集并放入 data 目录中
- 运行 feature_engineering.ipynb，会在 data 目录中生成处理好的数据



## 模型训练

运行 ncf.ipynb，会在当前目录生成 ncf.pt



## 模型部署

- 生成 mar（本地命令行）

```shell
torch-model-archiver --model-name ncf --version 1.0 --model-file ./ncf.py --serialized-file ncf.pt --export-path model_store --handler ncf_handler.py --force
```

- 拉取 torchserve Docker 镜像（本地命令行）

```shell
docker pull pytorch/torchserve:latest
```

- 启动 torchserve（本地命令行）

```shell
docker run -d --name torchserve -p 3000:8080 -p 3001:8081 -v path_to_your_project\model\model_store:/home/model-server/model-store pytorch/torchserve:latest
```

- 注册模型（Docker 容器）

```shell
curl -X POST "localhost:8081/models?model_name=ncf&url=ncf.mar&batch_size=64&max_batch_delay=0&initial_workers=4&synchronous=true"
```

- 查看模型的信息（Docker 容器）

```shell
curl "http://localhost:8081/models/ncf"
```

- 删除模型（Docker 容器）

```shell
curl -X DELETE http://localhost:8081/models/ncf
```
