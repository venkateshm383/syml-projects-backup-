CREATE TABLE IF NOT EXISTS `#__openerp_config` (
`id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,

`ordering` INT(11)  NOT NULL ,
`state` TINYINT(1)  NOT NULL ,
`checked_out` INT(11)  NOT NULL ,
`checked_out_time` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
`created_by` INT(11)  NOT NULL ,
`database_name` VARCHAR(255)  NOT NULL ,
`user_id` INT(11)  NOT NULL ,
`password` VARCHAR(255)  NOT NULL ,
`url` VARCHAR(500)  NOT NULL ,
`email` VARCHAR(255)  NOT NULL ,
`send_mails_to_user` VARCHAR(255)  NOT NULL ,
`text_to_send` VARCHAR(255)  NOT NULL ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT COLLATE=utf8_general_ci;

