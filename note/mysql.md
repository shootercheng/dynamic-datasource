# 事务

​     默认情况下， MySQL 是自动提交的,  InnoDB 支持行级锁定

> 原子性（Atomicity）、一致性（Consistency）、隔离性（lsolation）、持久性（Durability）



```mysql
-- 开启事务
start transaction
-- DML
-- 提交事务
commit
-- 回滚
rollback
```

