DROP DATABASE IF EXISTS test1;
DROP DATABASE IF EXISTS test2;
CREATE DATABASE test1 charset=utf8mb4;
CREATE DATABASE test2 charset=utf8mb4;

DROP DATABASE IF EXISTS dev_1;
DROP DATABASE IF EXISTS dev_2;
CREATE DATABASE dev_1 charset=utf8mb4;
CREATE DATABASE dev_2 charset=utf8mb4;

use test1;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use test2;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use dev_1;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use dev_2;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use dev1;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use dev2;
drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

use test1;
create table t_task_param (
	id int primary key auto_increment,
	param_type varchar(20),
	param_value varchar(50)
) charset = utf8mb4;

insert into t_task_param(param_type, param_value) values('timetask','0/20 * * * * ?');
update t_task_param set param_value = "0/20 * * * * ?" where id = 1;
update t_task_param set param_value = "0 0/1 * * * ?" where id = 1;

CREATE TABLE `t_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `content` json DEFAULT NULL,
  `info` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;