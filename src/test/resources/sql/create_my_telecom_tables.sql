USE `my_telecom`;

CREATE TABLE `auth_info` (
	`id` BIGINT NOT NULL AUTO_INCREMENT, 
	`login` VARCHAR(20) NOT NULL UNIQUE COLLATE utf8_bin, -- для чувствительности к регистру
	`password` CHAR(60) NOT NULL COLLATE utf8_bin, 
	`status` ENUM ('ACTIVE','BlOCKED')  DEFAULT 'BLOCKED' NOT NULL, 
	`role` ENUM ('ADMIN', 'USER')  DEFAULT 'USER' NOT NULL,
	CONSTRAINT `pk_users`
	PRIMARY KEY (`id`),
    INDEX (login, password)	
    
);

CREATE TABLE `users` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
    `auth_info_id` BIGINT NOT NULL,
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
`id` BIGINT NOT NULL AUTO_INCREMENT,
`balance` DECIMAL(6,2) NOT NULL,
`registration_date` TIMESTAMP,
`user_id` BIGINT NOT NULL,
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





 


