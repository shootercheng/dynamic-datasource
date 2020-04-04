DROP DATABASE IF EXISTS @#db_name#@;
CREATE DATABASE @#db_name#@ charset=utf8mb4;
USE @#db_name#@;

drop table if exists t_test;
create table t_test (
	id  bigint(20) primary key AUTO_INCREMENT,
	name varchar(50)
) charset=utf8mb4;

CREATE TABLE `label` (
  `id` bigint(20) NOT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `reseller_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `batch` int(11) DEFAULT NULL,
  `query_times` int(11) DEFAULT NULL,
  `first_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;