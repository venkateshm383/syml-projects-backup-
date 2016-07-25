CREATE TABLE IF NOT EXISTS `#__social_networks_` (
`id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,

`ordering` INT(11)  NOT NULL ,
`state` TINYINT(1)  NOT NULL ,
`checked_out` INT(11)  NOT NULL ,
`checked_out_time` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
`created_by` INT(11)  NOT NULL ,
`facebook` VARCHAR(255)  NOT NULL ,
`twitter` VARCHAR(255)  NOT NULL ,
`google_plus` VARCHAR(255)  NOT NULL ,
`dribble` VARCHAR(255)  NOT NULL ,
`tutorial9` VARCHAR(255)  NOT NULL ,
`rss` VARCHAR(255)  NOT NULL ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT COLLATE=utf8_general_ci;

