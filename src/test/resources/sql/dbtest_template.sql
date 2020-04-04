DROP DATABASE IF EXISTS @#db_name#@;
CREATE DATABASE @#db_name#@ charset=utf8mb4;
USE @#db_name#@;

drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;