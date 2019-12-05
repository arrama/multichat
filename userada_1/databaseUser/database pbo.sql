/*
SQLyog Professional v13.1.1 (64 bit)
MySQL - 10.1.36-MariaDB : Database - pbo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pbo` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `pbo`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_id`,`user_type`,`username`,`password`) values 
(1,NULL,'SUPER ADMIN','admin','admin'),
(2,1,'NORMAL','robin','robin'),
(3,2,'ADMIN','taylor','taylor'),
(4,3,'ADMIN','vivian','vivian'),
(5,4,'NORMAL','harry','harry'),
(6,7,'ADMIN','melinda','melinda'),
(7,8,'NORMAL','harley','harley'),
(8,NULL,'SUPER ADMIN','admin','admin'),
(9,1,'NORMAL','robin','robin'),
(10,2,'ADMIN','taylor','taylor'),
(11,3,'ADMIN','vivian','vivian'),
(12,4,'NORMAL','harry','harry'),
(13,7,'ADMIN','melinda','melinda'),
(14,8,'NORMAL','harley','harley'),
(15,NULL,'SUPER ADMIN','admin','admin'),
(16,1,'NORMAL','robin','robin'),
(17,2,'ADMIN','taylor','taylor'),
(18,3,'ADMIN','vivian','vivian'),
(19,4,'NORMAL','harry','harry'),
(20,7,'ADMIN','melinda','melinda'),
(21,8,'NORMAL','harley','harley');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
