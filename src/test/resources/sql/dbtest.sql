DROP DATABASE IF EXISTS test4;
CREATE DATABASE test4 charset=utf8mb4;
USE test4;

drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;