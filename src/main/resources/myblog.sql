/*
SQLyog v10.2 
MySQL - 5.1.32-community : Database - myblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `myblog`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志自增Id',
  `articleName` varchar(100) NOT NULL COMMENT '文章名称',
  `articleTime` date NOT NULL COMMENT '发布时间',
  `articleContent` text NOT NULL COMMENT '文章内容',
  `articleClick` int(11) DEFAULT NULL COMMENT '查看人数',
  `articleSupport` int(11) DEFAULT NULL COMMENT '是否博主推荐。0为否；1为是',
  `articleUp` int(11) DEFAULT NULL COMMENT '是否置顶。0为；1为是',
  `articleType` int(11) NOT NULL COMMENT '文章类别。0为私有，1为公开，2为仅好友查看',
  `typeId` int(11) NOT NULL COMMENT '栏目Id',
  `userId` int(11) NOT NULL COMMENT '博主Id',
  `imgPath` varchar(100) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `article` */

insert  into `article`(`articleId`,`articleName`,`articleTime`,`articleContent`,`articleClick`,`articleSupport`,`articleUp`,`articleType`,`typeId`,`userId`,`imgPath`) values (1,'住在手机里的朋友','2017-03-02','通信时代，无论是初次相见还是老友重逢，交换联系方式，常常是彼此交换名片，然后郑重或是出于礼貌用手机记下对方的电话号码。在快节奏的生活里，我们不知不觉中就成为住在别人手机里的朋友。又因某些意外，变成了别人手机里匆忙的过客，这种快餐式的友谊 ...',1,1,0,1,1,1,'static/images/02.jpg'),(2,'教你怎样用欠费手机拨打电话','2017-03-25','初次相识的喜悦，让你觉得似乎找到了知音。于是，对于投缘的人，开始了较频繁的交往。渐渐地，初识的喜悦退尽，接下来就是仅仅保持着联系，平淡到偶尔在节假曰发短信互致问候...',2,1,0,1,1,1,'static/images/02.jpg'),(3,'你面对的是生活而不是手机','2017-03-10','每一次与别人吃饭，总会有人会拿出手机。以为他们在打电话或者有紧急的短信，但用余光瞟了一眼之后发现无非就两件事：1、看小说，2、上人人或者QQ...',3,1,0,1,1,1,'static/images/02.jpg'),(4,'你面对的是生活而不是手机','2017-03-27','每一次与别人吃饭，总会有人会拿出手机。以为他们在打电话或者有紧急的短信，但用余光瞟了一眼之后发现无非就两件事：1、看小说，2、上人人或者QQ...',4,1,0,1,1,1,'static/images/02.jpg'),(5,'你面对的是生活而不是手机','2017-03-28','每一次与别人吃饭，总会有人会拿出手机。以为他们在打电话或者有紧急的短信，但用余光瞟了一眼之后发现无非就两件事：1、看小说，2、上人人或者QQ...',5,1,0,1,1,1,'static/images/02.jpg'),(6,'测试','2017-03-29','每一次与别人吃饭，总会有人会拿出手机。以为他们在打电话或者有紧急的短信，但用余光瞟了一眼之后发现无非就两件事：1、看小说，2、上人人或者QQ...',6,1,0,1,1,1,'static/images/02.jpg'),(7,'测试数据','2017-03-30','测试',7,1,0,1,1,1,'static/images/02.jpg'),(8,'测试数据','2017-03-01','测试数据',8,1,0,1,1,1,'static/images/02.jpg');

/*Table structure for table `article_comment` */

DROP TABLE IF EXISTS `article_comment`;

CREATE TABLE `article_comment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论Id',
  `postId` int(11) NOT NULL COMMENT '评论者Id',
  `userId` int(11) NOT NULL COMMENT '博主Id',
  `articleId` int(11) NOT NULL COMMENT '文章Id',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `responseId` int(11) NOT NULL COMMENT '回复Id',
  `time` date NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `article_comment` */

/*Table structure for table `article_response` */

DROP TABLE IF EXISTS `article_response`;

CREATE TABLE `article_response` (
  `responseId` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `content` varchar(1000) NOT NULL COMMENT '回复内容',
  `time` date NOT NULL COMMENT '回复时间',
  PRIMARY KEY (`responseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `article_response` */

/*Table structure for table `article_sort` */

DROP TABLE IF EXISTS `article_sort`;

CREATE TABLE `article_sort` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '博客栏目Id',
  `name` varchar(100) NOT NULL COMMENT '栏目名称',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  PRIMARY KEY (`typeId`,`name`,`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `article_sort` */

insert  into `article_sort`(`typeId`,`name`,`userId`) values (1,'个人博客',1),(2,'算法分析',1),(3,'WebService',1),(4,'JVM',1),(5,'JavaEE框架',1),(6,'Html5',1),(7,'JavaScript',1),(8,'Android',1),(9,'网络编程',1),(10,'Lucene',1),(11,'Shiro',1),(12,'Jquery',1),(13,'SpringMVC',1),(14,'Struts2',1),(15,'设计模式',1);

/*Table structure for table `friendly_link` */

DROP TABLE IF EXISTS `friendly_link`;

CREATE TABLE `friendly_link` (
  `linkId` int(11) NOT NULL AUTO_INCREMENT COMMENT '友情链接Id',
  `linkName` varchar(100) NOT NULL COMMENT '链接名称',
  `linkUrl` varchar(100) NOT NULL COMMENT '链接url',
  PRIMARY KEY (`linkId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `friendly_link` */

insert  into `friendly_link`(`linkId`,`linkName`,`linkUrl`) values (1,'Nicky\'s blog','https://u014427391.github.io/'),(2,'Android开发技术周报','http://androidweekly.cn/');

/*Table structure for table `secret_message` */

DROP TABLE IF EXISTS `secret_message`;

CREATE TABLE `secret_message` (
  `secretId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `sendId` int(11) NOT NULL COMMENT '发信者Id',
  `receiveId` int(11) NOT NULL COMMENT '接收者Id',
  `messageContent` varchar(100) NOT NULL COMMENT '私信内容',
  PRIMARY KEY (`secretId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `secret_message` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `identity` varchar(100) DEFAULT NULL COMMENT '标志',
  `parentId` int(11) DEFAULT NULL COMMENT '上级Id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menuIcon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `menuUrl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `menuType` varchar(10) DEFAULT NULL COMMENT '菜单类型',
  `menuOrder` varchar(10) DEFAULT NULL COMMENT '菜单排序',
  PRIMARY KEY (`menuId`),
  UNIQUE KEY `uk` (`identity`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menuId`,`identity`,`parentId`,`name`,`menuIcon`,`menuUrl`,`menuType`,`menuOrder`) values (1,'user',0,'用户管理','icon-search','#','1','1'),(2,NULL,1,'管理员管理','',NULL,'2','2'),(3,NULL,1,'博客用户','',NULL,'2','3'),(4,NULL,0,'文章管理','icon-bkgl','#','1','4'),(5,NULL,4,'写文章','icon-writeblog',NULL,'2','5'),(6,NULL,4,'文章管理','icon-bkgl',NULL,'2','6'),(7,NULL,0,'系统管理','icon-item','#','1','7'),(8,NULL,7,'角色管理',NULL,NULL,'2','8'),(9,NULL,7,'权限管理','icon-search',NULL,'2','9'),(10,NULL,7,'菜单管理',NULL,NULL,'2','10'),(11,NULL,0,'博客配置','icon-bkgl','#','1','11'),(12,NULL,11,'链接管理','icon-link','link/doLoadData.do','2','12'),(13,NULL,11,'标签管理','icon-bklb',NULL,'2','13'),(14,NULL,11,'广告管理',NULL,NULL,'2','14');

/*Table structure for table `sys_operation` */

DROP TABLE IF EXISTS `sys_operation`;

CREATE TABLE `sys_operation` (
  `id` int(11) NOT NULL COMMENT '操作Id，主键',
  `desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `name` varchar(100) DEFAULT NULL COMMENT '操作名称',
  `operation` varchar(100) DEFAULT NULL COMMENT '操作标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_o_1` (`operation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_operation` */

insert  into `sys_operation`(`id`,`desc`,`name`,`operation`) values (1,'创建操作','创建','create'),(2,'编辑权限','编辑','edit'),(3,'删除权限','删除','delete'),(4,'浏览权限','浏览','view');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL COMMENT '权限Id',
  `desc` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`),
  KEY `p_fk_1` (`menuId`),
  CONSTRAINT `p_fk_1` FOREIGN KEY (`menuId`) REFERENCES `sys_menu` (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`desc`,`name`,`menuId`) values (1,'用户管理相关权限','用户管理',1),(2,NULL,NULL,4),(3,NULL,NULL,7),(4,NULL,NULL,11),(5,NULL,NULL,3);

/*Table structure for table `sys_permission_operation` */

DROP TABLE IF EXISTS `sys_permission_operation`;

CREATE TABLE `sys_permission_operation` (
  `permissionId` int(11) NOT NULL,
  `operationId` int(11) NOT NULL,
  PRIMARY KEY (`permissionId`,`operationId`),
  KEY `po_fk_1` (`operationId`),
  CONSTRAINT `po_fk_1` FOREIGN KEY (`operationId`) REFERENCES `sys_operation` (`id`),
  CONSTRAINT `po_fk_2` FOREIGN KEY (`permissionId`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission_operation` */

insert  into `sys_permission_operation`(`permissionId`,`operationId`) values (1,1),(2,2),(3,3);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `role` varchar(100) DEFAULT NULL COMMENT '角色标志',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`roleId`,`name`,`desc`,`role`) values (1,'拥有所有权限','超级管理员','admin'),(2,'用户管理权限','用户管理员','admin_user'),(3,'角色管理权限','角色管理员','admin_role'),(4,'资源管理权限','资源管理员','admin_resource'),(6,'操作权限管理','操作权限管理员','admin_operation'),(7,'查看系统权限','查看员','checker');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `permissionId` int(11) NOT NULL COMMENT '权限Id',
  UNIQUE KEY `rp_fk_2` (`permissionId`),
  KEY `rp_fk_1` (`roleId`),
  CONSTRAINT `rp_fk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`),
  CONSTRAINT `rp_fk_2` FOREIGN KEY (`permissionId`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`roleId`,`permissionId`) values (1,1),(1,2),(1,3),(1,4),(2,5);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` int(12) DEFAULT NULL COMMENT '手机',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mark` varchar(30) DEFAULT NULL COMMENT '备注',
  `rank` varchar(10) DEFAULT NULL COMMENT '账号等级',
  `lastLogin` varchar(100) DEFAULT NULL COMMENT '最后一次登录时间',
  `loginIp` varchar(30) DEFAULT NULL COMMENT '登录ip',
  `imageUrl` varchar(100) DEFAULT NULL COMMENT '头像图片路径',
  `regTime` date NOT NULL COMMENT '注册时间',
  `locked` tinyint(1) DEFAULT NULL COMMENT '账号是否被锁定',
  `rights` varchar(100) DEFAULT NULL COMMENT '权限（没有使用）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_u_1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`phone`,`sex`,`email`,`mark`,`rank`,`lastLogin`,`loginIp`,`imageUrl`,`regTime`,`locked`,`rights`) values (1,'admin','28dca2a7b33b7413ad3bce1d58c26dd679c799f1',1552323312,'男','313222@foxmail.com','超级管理员','admin','2016-12-12','127.0.0.1','/static/images/','2017-03-15',0,NULL),(2,'sys','28dca2a7b33b7413ad3bce1d58c26dd679c799f1',1552323312,'男','313222@foxmail.com','系统管理员','sys','2016-12-12','127.0.0.1','/static/images/','2017-03-15',0,NULL),(3,'user','28dca2a7b33b7413ad3bce1d58c26dd679c799f1',1552323312,'男','313222@foxmail.com','用户','user','2016-12-12','127.0.0.1','/static/images/','2017-03-15',0,NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `userId` int(11) NOT NULL COMMENT '用户Id,联合主键',
  `roleId` int(11) NOT NULL COMMENT '角色Id，联合主键',
  PRIMARY KEY (`userId`,`roleId`),
  KEY `ur_fk_2` (`roleId`),
  CONSTRAINT `ur_fk_1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `ur_fk_2` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`userId`,`roleId`) values (1,1),(2,2),(3,3),(3,4),(3,7);

/*Table structure for table `user_attention` */

DROP TABLE IF EXISTS `user_attention`;

CREATE TABLE `user_attention` (
  `aId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `attentionId` int(11) NOT NULL COMMENT '关注者Id',
  PRIMARY KEY (`aId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_attention` */

/*Table structure for table `web_ad` */

DROP TABLE IF EXISTS `web_ad`;

CREATE TABLE `web_ad` (
  `adId` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告Id',
  `adTitle` varchar(100) NOT NULL COMMENT '广告标题',
  `adImage` varchar(100) NOT NULL COMMENT '图片路径',
  `adDesc` varchar(100) DEFAULT NULL COMMENT '广告描述',
  PRIMARY KEY (`adId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `web_ad` */

insert  into `web_ad`(`adId`,`adTitle`,`adImage`,`adDesc`) values (1,'测试1','static/images/a1.jpg','add your description here'),(2,'测试2','static/images/a2.jpg','add your description here'),(3,'测试3','static/images/a3.jpg','add your description here'),(4,'测试4','static/images/a4.jpg','add your description here');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
