# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` char(8) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
`userkey` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS board;
CREATE TABLE IF NOT EXISTS board ( 
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  subject varchar(255) NOT NULL,
  content mediumtext,
  created datetime DEFAULT current_timestamp(),
  email varchar(128) NOT NULL,
  uid varchar(8) NOT NULL,
  tag varchar(128), 
  hit int(10) unsigned NOT NULL default '0',  
  PRIMARY KEY (id)
) CHARSET=utf8;
insert into board (subject, content, email,uid) values("subject1", "content 00001", "test@test.com", "test");
insert into board (subject, content, email,uid) values("subject2", "content 00002", "test@test.com", "test");
 insert into board (subject, content, email, uid,tag) values("subject0", "content 0000", "test@test.com", "test","test");

CREATE TABLE tb_comment (
    idx INT NOT NULL AUTO_INCREMENT ,
    board_idx INT NOT NULL ,
    content VARCHAR(3000) NOT NULL ,
    writer VARCHAR(20) NOT NULL ,
    delete_yn ENUM('Y', 'N') NOT NULL DEFAULT 'N',
    insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    update_time DATETIME DEFAULT NULL ,
    delete_time DATETIME DEFAULT NULL ,
    PRIMARY KEY (idx)
);