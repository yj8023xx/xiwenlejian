- 生成mar
```shell
torch-model-archiver --model-name ncf --version 1.0 --model-file ./ncf.py --serialized-file ncf.pt --export-path model_store --handler ncf_handler.py --force
```

- 运行torchserve
```shell
docker run --rm -it -p 3000:8080 -p 3001:8081 -v C:\Nutstore\JavaWeb\xiwenlejian\model\model_store:/home/model-server/model-store pytorch/torchserve:latest
```

- 注册模型
```shell
curl -X POST "localhost:8081/models?model_name=ncf&url=ncf.mar&batch_size=1&max_batch_delay=5000&initial_workers=1&synchronous=true"
```

- 查看模型的信息
```shell
curl "http://localhost:8081/models/ncf"
```

- 删除模型
```shell
curl -X DELETE http://localhost:8081/models/ncf
```