/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.20-log : Database - filmindustry
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`filmindustry` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `filmindustry`;

/*Table structure for table `auditions` */

DROP TABLE IF EXISTS `auditions`;

CREATE TABLE `auditions` (
  `audition_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `required_numbers` varchar(50) DEFAULT NULL,
  `venue` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `audition_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`audition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `auditions` */

insert  into `auditions`(`audition_id`,`member_id`,`title`,`required_numbers`,`venue`,`date_time`,`audition_status`) values (1,1,'Excepturi earum quos','524','Dolore quam nesciunt','1985-12-19T11:52','complted'),(3,4,'hello','2','kaloor','2021-03-05T13:19','completed'),(4,5,'camera','5','kochi','2021-03-17T12:00','upcoming');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `complaint_description` varchar(50) DEFAULT NULL,
  `reply_description` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`member_id`,`complaint_description`,`reply_description`,`date_time`) values (1,1,'payment issue','will consider that','2020-02-11'),(2,1,'audition postphoned','ok','2021-02-12'),(3,4,'fdsghfd','okk','2021-02-26'),(4,6,'samplr','hii','2021-02-26 16:02:20'),(5,7,'hAllo','hii','2021-02-26 16:11:19'),(6,10,'samplr','buhahahahah','2021-03-05 14:52:13');

/*Table structure for table `hire` */

DROP TABLE IF EXISTS `hire`;

CREATE TABLE `hire` (
  `hire_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `hired_member_id` int(11) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `hire` */

insert  into `hire`(`hire_id`,`member_id`,`hired_member_id`,`date_time`) values (1,1,1,'2021-02-12'),(2,4,1,'2021-02-26'),(3,4,7,'2021-02-26'),(13,4,6,'2021-03-05'),(14,4,10,'2021-03-05');

/*Table structure for table `hiring` */

DROP TABLE IF EXISTS `hiring`;

CREATE TABLE `hiring` (
  `hire_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `hired_member_id` int(11) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hire_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `hiring` */

/*Table structure for table `industry_types` */

DROP TABLE IF EXISTS `industry_types`;

CREATE TABLE `industry_types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  `type_description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `industry_types` */

insert  into `industry_types`(`type_id`,`type_name`,`type_description`) values (3,'Music Studio','The works related with audio section'),(7,'Stunt','Stunt'),(8,'Videography','Video ');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin','admin'),(2,'fmember','fmember','member'),(6,'sachin','sachin','member'),(7,'nizzam','123456789','member'),(8,'akshaykk','123456','member'),(9,'ss','ss','normal'),(10,'aksa','aksa','normal'),(11,'noufal','noufal','normal'),(12,'noufal','noufal','normal'),(13,'a','a','normal');

/*Table structure for table `members` */

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `house_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `pincode` varchar(50) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `dob` varchar(15) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `members` */

insert  into `members`(`member_id`,`login_id`,`first_name`,`last_name`,`photo`,`house_name`,`place`,`pincode`,`gender`,`dob`,`phone`,`email`) values (1,2,'virat','k',NULL,'mrf','mubai','658980',NULL,NULL,'9846786435','virat@gmail.com'),(2,5,'Mufutau Leonard','Athena Hill','static/images/52935394-f70e-4c65-a089-b7c7f69e197eScreenshot (2).png','Sydney Thomas','Sunt aliquam rerum ','673008',NULL,NULL,'+1 (859) 151-8093','coxaxisecu@mailinator.com'),(3,6,'Leonard Wilkins','Nigel Kelly','static/images/0b24ce4c-5016-4614-9109-743261af1618Screenshot (3).png','Jeremy Trujillo','Commodo elit quae o','Fuga Nihil autem ni','male','2008-03-25','+1 (943) 891-7988','bicodec@mailinator.com'),(4,7,'Nizam','km','static/images/7c6cfa7e-4d99-43ba-918c-d700e006eae9IMG_20200528_214158-01-01.jpeg','Kalaparmbath house','Koolimuttam','680691','male','2000-07-20','7025049580','nizamofficial123@gmail.com'),(5,8,'Akshay','KK','static/images/8436f816-c533-4a9e-a2f3-1ff0bc69de01PicsArt_02-11-08.13.44.jpg','Thekevalappil House','chendrapinni','680691','male','2000-11-20','9846872677','akshaykishor9999@gmail.com'),(6,9,'ghh','bhh','a0ba604c-73d0-47ed-b948-e7c438fdda86.jpg','gg','gg','258096','male','2021-02-26','2587413690','ff@gg.bn'),(7,10,'Aks','kk','ab2988a0-816a-450a-9910-fb73c4d261ce.jpg','thekkrp','avgahaj','686806','male','2000-02-26','8528526938','yeywueuueu@gaha.com'),(8,11,'noufal','rhs','0322ee0e-8d96-4e96-bdf6-25935fa5dc0f.jpg','nafa','katoor','680654','male','2000-03-22','8082858048','nfal@gmial.cok'),(9,12,'noufal','rhs','a2c7da86-1aff-4ffd-b7f8-b1de6bd9efe0.jpg','nafa','katoor','680654','male','2000-03-22','8082858048','nfal@gmial.cok'),(10,13,'ananda','haahhah','static/images/9775751e-19a4-4056-af5f-358050325e58.jpg','hahajjajs','hahshsh','947997','male','1997-03-17','1234567890','abhaha@hah.xk');

/*Table structure for table `notifications` */

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Data for the table `notifications` */

insert  into `notifications`(`notification_id`,`title`,`description`,`date`) values (22,'hello','welcome','2021-02-26'),(23,'hii','halo','2021-02-26');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `payment_date` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

insert  into `payments`(`payment_id`,`member_id`,`payment_date`,`amount`) values (1,1,'12-10-2020','1000'),(2,2,'2021-02-11','2389'),(3,3,'2021-02-26','2389'),(5,4,'2021-02-26','2389'),(6,5,'2021-02-26','2389');

/*Table structure for table `portfolio` */

DROP TABLE IF EXISTS `portfolio`;

CREATE TABLE `portfolio` (
  `portfolio_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `descriptions` varchar(50) DEFAULT NULL,
  `image_path` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`portfolio_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `portfolio` */

insert  into `portfolio`(`portfolio_id`,`member_id`,`title`,`descriptions`,`image_path`) values (1,1,'portfolio1','descriptionnn','static/images/52935394-f70e-4c65-a089-b7c7f69e197eScreenshot (2).png'),(2,7,'niyaz','niyaz','1cd305f4-b15c-4a18-b64d-0b130af67c6d.jpg'),(3,6,'zam','njn','d562de30-8156-4969-a87d-d7a745b994d4.jpg'),(4,10,'nisan','hai','ae9d2d4b-485e-461c-8cb4-1c3a895164b6.jpg'),(5,10,'hallo','haa','static/images/4e62d466-021b-44a1-9285-d65171f0defa.jpg'),(6,10,'hallo','haa','static/images/ad515fe3-4aac-4871-a88e-997e04c3c4d5.jpg'),(7,7,'zam','zam','static/images/eed7f25e-1328-4da3-90c7-cb52aedf91c4.jpg'),(8,7,'zam','zam','static/images/67b8736d-18f4-48eb-a979-d22147d02bd2.jpg');

/*Table structure for table `reviews` */

DROP TABLE IF EXISTS `reviews`;

CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `review_title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `reviews` */

insert  into `reviews`(`review_id`,`member_id`,`review_title`,`description`,`date_time`) values (1,1,'title1','description1','12-10-2020');

/*Table structure for table `vaccancies` */

DROP TABLE IF EXISTS `vaccancies`;

CREATE TABLE `vaccancies` (
  `vaccancy_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `position_vacant` varchar(50) DEFAULT NULL,
  `details` varchar(50) DEFAULT NULL,
  `no_of_vaccancy` varchar(50) DEFAULT NULL,
  `vaccancy_status` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vaccancy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `vaccancies` */

insert  into `vaccancies`(`vaccancy_id`,`member_id`,`type_id`,`position_vacant`,`details`,`no_of_vaccancy`,`vaccancy_status`,`date_time`) values (1,1,1,'music director','classical and western','2','active','2021-02-12'),(4,4,3,'Singer','Need a female singer','5','active','2021-02-26');

/*Table structure for table `vaccancy_applications` */

DROP TABLE IF EXISTS `vaccancy_applications`;

CREATE TABLE `vaccancy_applications` (
  `vaccancy_application_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `vaccancy_id` int(11) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `application_status` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vaccancy_application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `vaccancy_applications` */

insert  into `vaccancy_applications`(`vaccancy_application_id`,`member_id`,`vaccancy_id`,`description`,`application_status`,`date_time`) values (1,1,1,'description1','pending','2020-10-11'),(2,7,4,'pending','pending','2021-02-26 16:20:46'),(3,6,4,'pending','Hired','2021-03-05 11:08:20'),(4,6,4,'pending','Hired','2021-03-05 11:21:24'),(5,6,4,'pending','Hired','2021-03-05 11:39:12'),(6,6,4,'pending','Hired','2021-03-05 12:14:31'),(7,10,4,'pending','Hired','2021-03-05 12:34:56');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
