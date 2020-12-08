CREATE DATABASE `my_telecom` DEFAULT CHARACTER SET utf8; -- можно не указывать в базе это по дефолту ---

CREATE USER IF NOT EXISTS telecom_user@localhost IDENTIFIED BY '123456';
CREATE USER IF NOT EXISTS telecom_user@'%' IDENTIFIED BY '123456';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `my_telecom`.*
TO telecom_user@localhost;

GRANT SELECT,INSERT,UPDATE,DELETE
ON `my_telecom`.*
TO telecom_user@'%';

FLUSH PRIVILEGES; -- чистит кэш перегружает грант таблицы --


SHOW VARIABLES LIKE "max_connections";

SET GLOBAL max_connections = 1000;