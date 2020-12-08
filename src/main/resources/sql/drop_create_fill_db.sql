/*CREATE DB*/
DROP DATABASE IF EXISTS `my_telecom`;
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
/*CREATE TABLES*/
USE `my_telecom`;

CREATE TABLE `auth_info` (
	`id` BIGINT NOT NULL AUTO_INCREMENT, 
	`login` VARCHAR(20) NOT NULL UNIQUE COLLATE utf8_bin, -- для чувствительности к регистру
	`password` CHAR(60) NOT NULL COLLATE utf8_bin, 
	CONSTRAINT `pk_users`
	PRIMARY KEY (`id`),
    INDEX (login, password)	
    
);

CREATE TABLE `users` (
	`id` BIGINT NOT NULL auto_increment,
    `auth_info_id` BIGINT NOT NULL UNIQUE,
	`surname` VARCHAR(25) NOT NULL,
	`name` VARCHAR(25) NOT NULL,
	`address` VARCHAR(40) NOT NULL,
	`phone` VARCHAR(17) NOT NULL,
    `email` VARCHAR(20) , /*NOT NULL UNIQUE*/
	CONSTRAINT `pk_users`
	PRIMARY KEY (`id`),
    CONSTRAINT `fk_users_auth_info`
	FOREIGN KEY (`auth_info_id`)
    REFERENCES `auth_info` (`id`)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);


 CREATE TABLE `promotions`(
 `id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
 `description` VARCHAR(200),
 `date_start` TIMESTAMP,
 `date_end` TIMESTAMP,
 `discount` DECIMAL(4.2),
 CONSTRAINT `pk_promotions`
 PRIMARY KEY (`id`)
 );
 CREATE TABLE `tariffs` (
	`id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
	`name` VARCHAR(60) NOT NULL,
	`description` VARCHAR (1000),
    `speed` INTEGER,
	`price` DECIMAL(6,2) UNSIGNED NOT NULL,     /* unsigned to avoid negative values 6 digits, 2 after decimal point*/
	`promotion_id` BIGINT,
    CONSTRAINT `pk_tariffs`
    PRIMARY KEY (`id`),
	CONSTRAINT `fk_tariffs_promotions`
	FOREIGN KEY (`promotion_id`)
    REFERENCES `promotions` (`id`)
	ON UPDATE CASCADE  
	ON DELETE CASCADE
	
);
CREATE TABLE `accounts`(
`id` BIGINT NOT NULL auto_increment,
`balance` DECIMAL(6,2) NOT NULL default 0,
`registration_date` TIMESTAMP,
`status` ENUM ('ACTIVE','BLOCKED')  DEFAULT 'BLOCKED' NOT NULL, 
`role` ENUM ('ADMIN', 'USER')  DEFAULT 'USER' NOT NULL,
`user_id` BIGINT NOT NULL unique default 0,
CONSTRAINT `pk_accounts`
PRIMARY KEY (`id`),
CONSTRAINT `fk_accounts_users`
FOREIGN KEY (`user_id`)
REFERENCES `users` (`id`)
ON UPDATE CASCADE
ON DELETE CASCADE
);
CREATE TABLE `tariff_notes`(
`id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
`connection_date` TIMESTAMP,
`tariff_id` BIGINT NOT NULL,
`account_id` BIGINT NOT NULL,
CONSTRAINT `pk_tariff_notes`
PRIMARY KEY (`id`),
CONSTRAINT `fk_tariffs_notes_tariffs`
FOREIGN KEY (`tariff_id`)
REFERENCES `tariffs` (`id`)
ON UPDATE CASCADE  
ON DELETE CASCADE,
CONSTRAINT `fk_tariff_notes_accounts`
FOREIGN KEY (`account_id`)
REFERENCES `accounts` (`id`)
ON UPDATE CASCADE  
ON DELETE CASCADE
    
);
/*INIT ADMIN*/
USE `my_telecom`;
INSERT INTO `auth_info` (`login`, `password`) 
VALUES ("admin", "$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /* BCrypt хэш пароля "123456" */);
USE `my_telecom`;
INSERT INTO `users` (`auth_info_id`, `surname`, `name`, `address`, `phone`) 
VALUES ("1", "Иванов", "Иван", "Минск Волгоградская 19А-30", "+375(29)123-12-11");
INSERT INTO `accounts`(`id`, `role`, `status`)
VALUES ("1", 'ADMIN', 'ACTIVE');

/* FILL USERS*/
INSERT INTO `auth_info` (
	`login`,
	`password`

) VALUES (
	"user1",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  BCrypt хэш пароля "123456" */
	
), 
(
	"user2",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user3",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user4",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user5",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user6",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user7",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user8",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user9",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user10",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user11",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user12",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user13",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user14",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user15",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user16",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),

(
	"user17",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user18",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user19",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user20",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user21",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user22",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
),
(
	"user23",
	"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946" /*  хэш пароля "123456" */
	
);

INSERT INTO `users`
(`auth_info_id`, `surname`,  `name`,    `address`, `phone`)
VALUES
(2,          "Иванов",   "Иван",        "ул. Покровского, 13-3",   "+375(29)123-45-67"),
(3,          "Петров",   "Пётр",         "пр-т Будёного, 3А-43",     "+375(44)234-56-78"),
(4,          "Сидоров",  "Сидор",         "переул. Смирнова, 7-10",   "+375(29)345-67-89"),
(5,          "Васильев", "Василий",       "ул. Чапаева, 112-97",       "+375(29)456-77-90"),
(6,          "Антонова", "Марина",       "ул. Руссиянова, 12-5",     "+375(29)458-11-20"),
(7,          "Карпов", "Тимофей",       "ул. Абрикосовая, 14-12",     "+375(29)458-11-00"),
(8,          "Бердник", "Йосеф",       "ул. Автомобилистов, 10-1",     "+375(29)458-11-00"),
(9,          "Захаров", "Эдуард",       "ул. Верещагина, 17-8",     "+375(29)458-51-00"),
(10,          "Уваров", "Фёдор",       "ул. Великоморская, 17-5",     "+375(29)438-11-00"),
(11,          "Сысоев", "Оливер",       "ул. Веснинка, 11-5",     "+375(29)458-11-05"),
(12,          "Лыткин", "Сергей",       "ул. Радужная, 8-69",     "+375(29)457-18-00"),
(13,          "Медяник", "Зигмунд",       "ул. Водозаборная, 3-5",     "+375(29)458-11-00"),
(14,          "Носкова", "Эдуард",       "ул. Восточная, 1-58",     "+375(29)458-11-00"),
(15,          "Единович", "Юзефа",       "ул. Гагарина, 12-23",     "+375(29)428-11-10"),
(16,          "Козуб", "Людмила",       "ул. Гамарника, 28-72",     "+375(29)458-11-00"),
(17,          "Захаревич", "Кристина",       "ул. Герасименко, 12-42",     "+375(29)478-11-40"),
(18,          "Мицкевич", "Елизавета",       "ул. Дворищи, 12-9",     "+375(29)408-11-00"),
(19,          "Параскевич", "Дина",       "ул. Долгобродская, 4-52",     "+375(44)458-16-00"),
(20,          "Харитонова", "Клара",       "ул. Енисейская, 3-5",     "+375(29)458-11-00"),
(21,          "Дмитриева", "Доминика",       "ул. Захарова, 2-5",     "+375(44)457-71-07"),
(22,          "Шумейко", "Злата",       "ул. Кабушкина, 12-4",     "+375(29)458-11-66"),
(23,          "Хохлова", "Ирина",       "ул. Клумова, 17-5",     "+375(29)458-52-00"),
(24,          "Зайцева", "Инесса",       "ул. Козлова, 1-5",     "+375(29)689-11-00");

/*FILL DATA*/
USE `my_telecom`;
INSERT INTO `tariffs` (`name`, `description`, `speed`, `price`) VALUES 
('X5 MTS', 'unlim internet, WI-FI router', 100, 27.5),
('X6 MTS', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 38.5),
('X7 MTS','unlim internet, WI-FI router, TV, dr. Web, TV tunner', 200, 52.5),
('Combi solo A1','unlim internet, WI-FI router, dr. Web', 10, 11.9),
('Combi start A1', 'unlim internet, WI-FI router, dr. Web, TV tunner', 50, 24.9),
('Combi standart A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 70, 34.90),
('Combi super A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 44.90);

INSERT INTO `promotions` (`description`, `date_start`, `date_end`, `discount`) VALUES
('Black November','2020-11-01', '2020-11-30', 0.5),
('Happy winter','2020-12-24', '2020-12-31', 0.3),
('Happy Birthday My Telecom', '2020-10-05', '2020-10-05', 1);

INSERT INTO `accounts` (`balance`, `registration_date`, `user_id`) VALUES
(5, '2020-11-18', 2),
(5, '2020-11-13', 5),
(10,'2020-11-15', 3),
(15, '2020-11-15', 13);

INSERT INTO `tariff_notes` (`connection_date`, `tariff_id`, `account_id`) VALUES
('2020-11-18', 1, 1),
('2020-11-15', 3, 2),
('2020-11-15',5, 4);
