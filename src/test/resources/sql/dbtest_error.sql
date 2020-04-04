DROP DATABASE IF EXISTS error_sql;
CREATE DATABASE error_sql charset=utf8mb4;
USE error_sql;

drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

-- table not exists, rollback
drop table tt;