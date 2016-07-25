CREATE TABLE IF NOT EXISTS `#__mortgageapplication_mortgageapplicationedit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `createddate` DATE NOT NULL,
  `updateddate` DATE NOT NULL,
  `status` varchar(256) NOT NULL,
  `checked_out` int(11) NOT NULL,
  `checked_out_time` DATETIME NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;