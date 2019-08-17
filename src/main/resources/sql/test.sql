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