- 生成 mar（本地命令行）

```shell
torch-model-archiver --model-name ncf --version 1.0 --model-file ./ncf.py --serialized-file ncf.pt --export-path model_store --handler ncf_handler.py --force
```

- 本地命令行运行如下命令拉取 torchserve Docker 镜像

```shell
docker pull pytorch/torchserve:latest
```

- 启动 torchserve（本地命令行）

```shell
docker run --rm -it -p 3000:8080 -p 3001:8081 -v C:\Nutstore\JavaWeb\xiwenlejian\model\model_store:/home/model-server/model-store pytorch/torchserve:latest
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
