USE `my_telecom`;
INSERT INTO `auth_info` (`login`, `password`) 
VALUES ("admin", "$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /* BCrypt хэш пароля "123456" */);
INSERT INTO `users` (`auth_info_id`, `surname`, `name`, `address`, `phone`) 
VALUES ("1", "Иванов", "Иван", "Минск Волгоградская 19А-30", "+375(29)123-12-11");
INSERT INTO `accounts`(`id`, `role`, `status`)
VALUES ("1", 'ADMIN', 'ACTIVE');
