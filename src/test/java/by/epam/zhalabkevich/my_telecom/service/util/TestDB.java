package by.epam.zhalabkevich.my_telecom.service.util;

public class TestDB {

    public final static String RECREATE_DB ="/*CREATE DB*/\n" +
            "DROP DATABASE IF EXISTS `my_telecom`;\n" +
            "CREATE DATABASE `my_telecom` DEFAULT CHARACTER SET utf8; -- можно не указывать в базе это по дефолту ---\n" +
            "\n" +
            "CREATE USER IF NOT EXISTS telecom_user@localhost IDENTIFIED BY '123456';\n" +
            "CREATE USER IF NOT EXISTS telecom_user@'%' IDENTIFIED BY '123456';\n" +
            "\n" +
            "GRANT SELECT,INSERT,UPDATE,DELETE\n" +
            "ON `my_telecom`.*\n" +
            "TO telecom_user@localhost;\n" +
            "\n" +
            "GRANT SELECT,INSERT,UPDATE,DELETE\n" +
            "ON `my_telecom`.*\n" +
            "TO telecom_user@'%';\n" +
            "\n" +
            "FLUSH PRIVILEGES; -- чистит кэш перегружает грант таблицы --\n" +
            "\n" +
            "SHOW VARIABLES LIKE \"max_connections\";\n" +
            "\n" +
            "SET GLOBAL max_connections = 1000;\n" +
            "/*CREATE TABLES*/\n" +
            "USE `my_telecom`;\n" +
            "\n" +
            "CREATE TABLE `auth_info` (\n" +
            "\t`id` BIGINT NOT NULL AUTO_INCREMENT, \n" +
            "\t`login` VARCHAR(20) NOT NULL UNIQUE COLLATE utf8_bin, -- для чувствительности к регистру\n" +
            "\t`password` CHAR(60) NOT NULL COLLATE utf8_bin, \n" +
            "\tCONSTRAINT `pk_users`\n" +
            "\tPRIMARY KEY (`id`),\n" +
            "    INDEX (login, password)\t\n" +
            "    \n" +
            ");\n" +
            "\n" +
            "CREATE TABLE `users` (\n" +
            "\t`id` BIGINT NOT NULL auto_increment,\n" +
            "    `auth_info_id` BIGINT NOT NULL UNIQUE,\n" +
            "\t`surname` VARCHAR(25) NOT NULL,\n" +
            "\t`name` VARCHAR(25) NOT NULL,\n" +
            "\t`address` VARCHAR(40) NOT NULL,\n" +
            "\t`phone` VARCHAR(17) NOT NULL,\n" +
            "    `email` VARCHAR(20) , /*NOT NULL UNIQUE*/\n" +
            "\tCONSTRAINT `pk_users`\n" +
            "\tPRIMARY KEY (`id`),\n" +
            "    CONSTRAINT `fk_users_auth_info`\n" +
            "\tFOREIGN KEY (`auth_info_id`)\n" +
            "    REFERENCES `auth_info` (`id`)\n" +
            "\tON UPDATE CASCADE\n" +
            "\tON DELETE CASCADE\n" +
            ");\n" +
            "\n" +
            "\n" +
            " CREATE TABLE `promotions`(\n" +
            " `id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,\n" +
            " `description` VARCHAR(200),\n" +
            " `date_start` TIMESTAMP,\n" +
            " `date_end` TIMESTAMP,\n" +
            " `discount` DECIMAL(4.2),\n" +
            " CONSTRAINT `pk_promotions`\n" +
            " PRIMARY KEY (`id`)\n" +
            " );\n" +
            " CREATE TABLE `tariffs` (\n" +
            "\t`id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,\n" +
            "\t`name` VARCHAR(60) NOT NULL,\n" +
            "\t`description` VARCHAR (1000),\n" +
            "    `speed` INTEGER,\n" +
            "\t`price` DECIMAL(6,2) UNSIGNED NOT NULL,     /* unsigned to avoid negative values 6 digits, 2 after decimal point*/\n" +
            "\t`promotion_id` BIGINT,\n" +
            "    CONSTRAINT `pk_tariffs`\n" +
            "    PRIMARY KEY (`id`),\n" +
            "\tCONSTRAINT `fk_tariffs_promotions`\n" +
            "\tFOREIGN KEY (`promotion_id`)\n" +
            "    REFERENCES `promotions` (`id`)\n" +
            "\tON UPDATE CASCADE  \n" +
            "\tON DELETE CASCADE\n" +
            "\t\n" +
            ");\n" +
            "CREATE TABLE `accounts`(\n" +
            "`id` BIGINT NOT NULL auto_increment,\n" +
            "`balance` DECIMAL(6,2) NOT NULL default 0,\n" +
            "`registration_date` TIMESTAMP,\n" +
            "`status` ENUM ('ACTIVE','BlOCKED')  DEFAULT 'BLOCKED' NOT NULL, \n" +
            "`role` ENUM ('ADMIN', 'USER')  DEFAULT 'USER' NOT NULL,\n" +
            "`user_id` BIGINT NOT NULL unique default 0,\n" +
            "CONSTRAINT `pk_accounts`\n" +
            "PRIMARY KEY (`id`),\n" +
            "CONSTRAINT `fk_accounts_users`\n" +
            "FOREIGN KEY (`user_id`)\n" +
            "REFERENCES `users` (`id`)\n" +
            "ON UPDATE CASCADE\n" +
            "ON DELETE CASCADE\n" +
            ");\n" +
            "CREATE TABLE `tariff_notes`(\n" +
            "`id` BIGINT NOT NULL UNIQUE AUTO_INCREMENT,\n" +
            "`connection_date` TIMESTAMP,\n" +
            "`tariff_id` BIGINT NOT NULL,\n" +
            "`account_id` BIGINT NOT NULL,\n" +
            "CONSTRAINT `pk_tariff_notes`\n" +
            "PRIMARY KEY (`id`),\n" +
            "CONSTRAINT `fk_tariffs_notes_tariffs`\n" +
            "FOREIGN KEY (`tariff_id`)\n" +
            "REFERENCES `tariffs` (`id`)\n" +
            "ON UPDATE CASCADE  \n" +
            "ON DELETE CASCADE,\n" +
            "CONSTRAINT `fk_tariff_notes_accounts`\n" +
            "FOREIGN KEY (`account_id`)\n" +
            "REFERENCES `accounts` (`id`)\n" +
            "ON UPDATE CASCADE  \n" +
            "ON DELETE CASCADE\n" +
            "    \n" +
            ");\n" +
            "/*INIT ADMIN*/\n" +
            "USE `my_telecom`;\n" +
            "INSERT INTO `auth_info` (`login`, `password`) \n" +
            "VALUES (\"admin\", \"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /* BCrypt хэш пароля \"123456\" */);\n" +
            "USE `my_telecom`;\n" +
            "INSERT INTO `users` (`auth_info_id`, `surname`, `name`, `address`, `phone`) \n" +
            "VALUES (\"1\", \"Иванов\", \"Иван\", \"Минск Волгоградская 19А-30\", \"+375(29)123-12-11\");\n" +
            "INSERT INTO `accounts`(`id`, `role`, `status`)\n" +
            "VALUES (\"1\", 'ADMIN', 'ACTIVE');\n" +
            "\n" +
            "/* FILL USERS*/\n" +
            "INSERT INTO `auth_info` (\n" +
            "\t`login`,\n" +
            "\t`password`\n" +
            "\n" +
            ") VALUES (\n" +
            "\t\"user1\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  BCrypt хэш пароля \"123456\" */\n" +
            "\t\n" +
            "), \n" +
            "(\n" +
            "\t\"user2\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user3\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user4\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user5\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user6\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user7\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user8\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user9\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user10\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user11\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user12\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user13\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user14\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user15\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user16\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "\n" +
            "(\n" +
            "\t\"user17\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user18\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user19\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user20\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user21\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user22\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            "),\n" +
            "(\n" +
            "\t\"user23\",\n" +
            "\t\"$2a$10$DJE4vIMUnASdydQp00y3/e8f0EdPus7r2miOP5A7kQ3nsjmuQZ946\" /*  хэш пароля \"123456\" */\n" +
            "\t\n" +
            ");\n" +
            "\n" +
            "INSERT INTO `users`\n" +
            "(`auth_info_id`, `surname`,  `name`,    `address`, `phone`)\n" +
            "VALUES\n" +
            "(2,          \"Иванов\",   \"Иван\",        \"ул. Покровского, 13-3\",   \"+375(29)123-45-67\"),\n" +
            "(3,          \"Петров\",   \"Пётр\",         \"пр-т Будёного, 3А-43\",     \"+375(44)234-56-78\"),\n" +
            "(4,          \"Сидоров\",  \"Сидор\",         \"переул. Смирнова, 7-10\",   \"+375(29)345-67-89\"),\n" +
            "(5,          \"Васильев\", \"Василий\",       \"ул. Чапаева, 112-97\",       \"+375(29)456-77-90\"),\n" +
            "(6,          \"Антонова\", \"Марина\",       \"ул. Руссиянова, 12-5\",     \"+375(29)458-11-20\"),\n" +
            "(7,          \"Карпов\", \"Тимофей\",       \"ул. Абрикосовая, 14-12\",     \"+375(29)458-11-00\"),\n" +
            "(8,          \"Бердник\", \"Йосеф\",       \"ул. Автомобилистов, 10-1\",     \"+375(29)458-11-00\"),\n" +
            "(9,          \"Захаров\", \"Эдуард\",       \"ул. Верещагина, 17-8\",     \"+375(29)458-51-00\"),\n" +
            "(10,          \"Уваров\", \"Фёдор\",       \"ул. Великоморская, 17-5\",     \"+375(29)438-11-00\"),\n" +
            "(11,          \"Сысоев\", \"Оливер\",       \"ул. Веснинка, 11-5\",     \"+375(29)458-11-05\"),\n" +
            "(12,          \"Лыткин\", \"Сергей\",       \"ул. Радужная, 8-69\",     \"+375(29)457-18-00\"),\n" +
            "(13,          \"Медяник\", \"Зигмунд\",       \"ул. Водозаборная, 3-5\",     \"+375(29)458-11-00\"),\n" +
            "(14,          \"Носкова\", \"Эдуард\",       \"ул. Восточная, 1-58\",     \"+375(29)458-11-00\"),\n" +
            "(15,          \"Единович\", \"Юзефа\",       \"ул. Гагарина, 12-23\",     \"+375(29)428-11-10\"),\n" +
            "(16,          \"Козуб\", \"Людмила\",       \"ул. Гамарника, 28-72\",     \"+375(29)458-11-00\"),\n" +
            "(17,          \"Захаревич\", \"Кристина\",       \"ул. Герасименко, 12-42\",     \"+375(29)478-11-40\"),\n" +
            "(18,          \"Мицкевич\", \"Елизавета\",       \"ул. Дворищи, 12-9\",     \"+375(29)408-11-00\"),\n" +
            "(19,          \"Параскевич\", \"Дина\",       \"ул. Долгобродская, 4-52\",     \"+375(44)458-16-00\"),\n" +
            "(20,          \"Харитонова\", \"Клара\",       \"ул. Енисейская, 3-5\",     \"+375(29)458-11-00\"),\n" +
            "(21,          \"Дмитриева\", \"Доминика\",       \"ул. Захарова, 2-5\",     \"+375(44)457-71-07\"),\n" +
            "(22,          \"Шумейко\", \"Злата\",       \"ул. Кабушкина, 12-4\",     \"+375(29)458-11-66\"),\n" +
            "(23,          \"Хохлова\", \"Ирина\",       \"ул. Клумова, 17-5\",     \"+375(29)458-52-00\"),\n" +
            "(24,          \"Зайцева\", \"Инесса\",       \"ул. Козлова, 1-5\",     \"+375(29)689-11-00\");\n" +
            "\n" +
            "/*FILL DATA*/\n" +
            "USE `my_telecom`;\n" +
            "INSERT INTO `tariffs` (`name`, `description`, `speed`, `price`) VALUES \n" +
            "('X5 MTS', 'unlim internet, WI-FI router', 100, 27.5),\n" +
            "('X6 MTS', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 38.5),\n" +
            "('X7 MTS','unlim internet, WI-FI router, TV, dr. Web, TV tunner', 200, 52.5),\n" +
            "('Combi solo A1','unlim internet, WI-FI router, dr. Web', 10, 11.9),\n" +
            "('Combi start A1', 'unlim internet, WI-FI router, dr. Web, TV tunner', 50, 24.9),\n" +
            "('Combi standart A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 70, 34.90),\n" +
            "('Combi super A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 44.90);\n" +
            "\n" +
            "INSERT INTO `promotions` (`description`, `date_start`, `date_end`, `discount`) VALUES\n" +
            "('Black November','2020-11-01', '2020-11-30', 0.5),\n" +
            "('Happy winter','2020-12-24', '2020-12-31', 0.3),\n" +
            "('Happy Birthday My Telecom', '2020-10-05', '2020-10-05', 1);\n" +
            "\n" +
            "INSERT INTO `accounts` (`balance`, `registration_date`, `user_id`) VALUES\n" +
            "(5, '2020-11-18', 2),\n" +
            "(5, '2020-11-13', 5),\n" +
            "(10,'2020-11-15', 3),\n" +
            "(15, '2020-11-15', 13);\n" +
            "\n" +
            "INSERT INTO `tariff_notes` (`connection_date`, `tariff_id`, `account_id`) VALUES\n" +
            "('2020-11-18', 1, 1),\n" +
            "('2020-11-15', 3, 2),\n" +
            "('2020-11-15',5, 4);";


    private TestDB() {
    }
}
