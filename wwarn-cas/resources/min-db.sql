--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `rid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`rid`, `name`) VALUES
(1, 'anonymous user'),
(2, 'authenticated user'),
(3, 'Site Administrators'),
(4, 'Site Moderators'),
(5, 'Group Creators'),
(6, 'ROLE_CHASSIS_USER'),
(7, 'ROLE_CHASSIS_CONTRIBUTOR'),
(8, 'ROLE_CHASSIS_CURATOR'),
(9, 'ROLE_CHASSIS_PERSONAL_DATA_REVIEWER'),
(10, 'ROLE_CHASSIS_ADMINISTRATOR');

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '',
  `pass` varchar(32) NOT NULL DEFAULT '',
  `mail` varchar(64) DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`),
  KEY `mail` (`mail`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

INSERT INTO `users` (`uid`, `name`, `pass`, `mail`, `status`) VALUES
(1, 'adam@example.org', MD5('bar'), 'adam@example.org', 1),
(2, 'colin@example.org', MD5('bar'), 'colin@example.org', 1),
(3, 'cora@example.org', MD5('bar'), 'cora@example.org', 1),
(4, 'curtis@example.org', MD5('bar'), 'curtis@example.org', 1),
(5, 'muriel@example.org', MD5('bar'), 'muriel@example.org', 1),
(6, 'murray@example.org', MD5('bar'), 'murray@example.org', 1),
(7, 'murphy@example.org', MD5('bar'), 'murphy@example.org', 1),
(8, 'suki@example.org', MD5('bar'), 'suki@example.org', 1),
(9, 'sunil@example.org', MD5('bar'), 'sunil@example.org', 1),
(10, 'pete@example.org', MD5('bar'), 'pete@example.org', 1),
(11, 'ursula@example.org', MD5('bar'), 'ursula@example.org', 1);

--
-- Table structure for table `users_roles`
--

CREATE TABLE IF NOT EXISTS `users_roles` (
  `uid` int(10) unsigned NOT NULL DEFAULT '0',
  `rid` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`,`rid`),
  KEY `rid` (`rid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`uid`, `rid`) VALUES
(1, 6),
(1, 10),
(2, 6),
(2, 7),
(3, 6),
(3, 7),
(4, 6),
(4, 8),
(5, 6),
(5, 7),
(5, 8),
(5, 9),
(6, 6),
(6, 7),
(6, 10),
(7, 6),
(7, 8),
(7, 10),
(8, 6),
(8, 7),
(9, 6),
(9, 7),
(10,6),
(10,9),
(11,6);

