# 数据库部署

- 创建 xiwenlejian 数据库
- 运行 xiwenlejian.sql 创建数据库表
- 导入 data 目录下的 user.csv、book.csv 和 rating.csv
- 更新 user 表

```sql
UPDATE `user` SET username = CONCAT('U',user_id);
UPDATE `user` SET `password` = '123456';
```



