DROP TABLE IF EXISTS `UserCategory`;
DROP TABLE IF EXISTS `Type`;
DROP TABLE IF EXISTS `Category`;
DROP TABLE IF EXISTS `User`;

CREATE TABLE IF NOT EXISTS `User` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(16) UNIQUE NOT NULL,
    `password` varchar(16) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Category` (
    `category_id` int NOT NULL AUTO_INCREMENT,
    `category_name` varchar(50) UNIQUE NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`category_id`),
    CONSTRAINT `fk_user_id_category_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Type` (
    `type_id` int NOT NULL AUTO_INCREMENT,
    `type_name` varchar(50) NOT NULL,
    `category_id` int NOT NULL,
    PRIMARY KEY (`type_id`),
    CONSTRAINT `fk_category_id_type_id` FOREIGN KEY (`category_id`) REFERENCES `Category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `UserCategory` (
    `user_category_id` int NOT NULL AUTO_INCREMENT,
    `value` varchar(50) NULL,
    `date` datetime NULL,
    `type_id` int NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`user_category_id`),
    CONSTRAINT `fk_user_category_id_type_id` FOREIGN KEY (`type_id`) REFERENCES `Type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_user_category_id_user_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





