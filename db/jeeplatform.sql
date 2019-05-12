/*
SQLyog v10.2 
MySQL - 5.1.32-community : Database - jeeplatform
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jeeplatform` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jeeplatform`;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `parentId` int(11) DEFAULT NULL COMMENT '上级Id',
  `menuName` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menuIcon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `menuUrl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `menuType` varchar(10) DEFAULT NULL COMMENT '菜单类型',
  `menuOrder` varchar(10) DEFAULT NULL COMMENT '菜单排序',
  `menuStatus` varchar(10) DEFAULT NULL COMMENT '菜单状态',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menuId`,`parentId`,`menuName`,`menuIcon`,`menuUrl`,`menuType`,`menuOrder`,`menuStatus`) values (1,0,'用户管理','&#xe610','#','1','1','1'),(2,1,'用户管理','&#xe604','user/queryAll.do','2','2','1'),(3,1,'用户统计','&#xe604','#','2','3','1'),(4,0,'在线管理','&#xe610','#','1','4','1'),(5,4,'在线情况','&#xe604','#','2','5','1'),(6,4,'在线聊天','&#xe604','article/list.do','2','6','1'),(7,0,'系统管理','&#xe610','#','1','7','1'),(8,7,'角色管理','&#xe604','role/queryAll.do','2','8','1'),(9,7,'权限管理','&#xe604','permission/queryAll.do','2','9','1'),(10,7,'菜单管理','&#xe604','menu/getMenus.do','2','10','1'),(11,0,'系统监控','&#xe610','druid/index.html','1','11','1'),(12,11,'Druid监控','&#xe610','druid/index.html','1','12','1'),(13,11,'SwaggerUI','&#xe610','swagger-ui.html','1','13','1'),(14,1,'个人信息','&#xe610','#','1','14','1');

/*Table structure for table `sys_operation` */

DROP TABLE IF EXISTS `sys_operation`;

CREATE TABLE `sys_operation` (
  `id` int(11) NOT NULL COMMENT '操作Id，主键',
  `odesc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `name` varchar(100) DEFAULT NULL COMMENT '操作名称',
  `operation` varchar(100) DEFAULT NULL COMMENT '操作标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_o_1` (`operation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_operation` */

insert  into `sys_operation`(`id`,`odesc`,`name`,`operation`) values (1,'创建操作','创建','create'),(2,'编辑权限','编辑','edit'),(3,'删除权限','删除','delete'),(4,'浏览权限','浏览','view');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限Id',
  `pdesc` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`),
  KEY `p_fk_1` (`menuId`),
  CONSTRAINT `p_fk_1` FOREIGN KEY (`menuId`) REFERENCES `sys_menu` (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`pdesc`,`name`,`menuId`) values (1,'用户管理的权限','用户管理',1),(2,'管理员管理的权限','管理员管理',2),(3,'用户统计的权限','用户统计',3),(4,'在线管理的权限','在线管理',4),(5,'在线情况的权限','在线情况',5),(6,'在线聊天的权限','在线聊天',6),(7,'系统管理的权限','系统管理',7),(8,'角色管理的权限','角色管理',8),(9,'权限管理的权限','权限管理',9),(10,'菜单管理的权限','菜单管理',10),(11,'平台资料的权限','平台资料',11),(12,'Druid监控的权限','Druid监控的权限',12),(13,'SwaggerUI','SwaggerUI',13),(14,'个人信息的权限','个人信息的权限',14);

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

insert  into `sys_permission_operation`(`permissionId`,`operationId`) values (2,2),(3,3);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `roleName` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `roleDesc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `role` varchar(100) DEFAULT NULL COMMENT '角色标志',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`roleId`,`roleName`,`roleDesc`,`role`) values (1,'超级管理员','超级管理员拥有所有权限','admin'),(2,'用户管理员','用户管理权限','user'),(3,'角色管理员','角色管理权限','role'),(4,'资源管理员','资源管理权限','role'),(6,'操作权限管理员','操作权限管理','role'),(7,'查看员','查看系统权限','role'),(9,'用户','可以查看','role');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `rpId` varchar(12) NOT NULL COMMENT '表Id',
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `permissionId` int(11) NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`rpId`),
  KEY `rp_fk_2` (`permissionId`),
  KEY `rp_fk_1` (`roleId`),
  CONSTRAINT `rp_fk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`),
  CONSTRAINT `rp_fk_2` FOREIGN KEY (`permissionId`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`rpId`,`roleId`,`permissionId`) values ('02a97146f6f4',2,1),('346def9fe9d6',1,12),('3bdce30473ca',1,10),('47c998f93395',1,6),('4a1834ad6e17',1,7),('4b76f155fd74',9,1),('4ccc2dda4892',1,8),('547008dd157a',1,2),('55eb164457e2',9,2),('59084a9f6914',2,2),('5eb035bc97b2',1,11),('69171de938a6',1,5),('6a93a8e6e94d',1,3),('6dde43386281',1,4),('8adc5d180670',1,13),('9fa9725142c1',2,3),('b1729374cea8',1,9),('ccb535546f97',1,1);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mark` varchar(100) DEFAULT NULL COMMENT '备注',
  `rank` varchar(10) DEFAULT NULL COMMENT '账号等级',
  `lastLogin` date DEFAULT NULL COMMENT '最后一次登录时间',
  `loginIp` varchar(30) DEFAULT NULL COMMENT '登录ip',
  `imageUrl` varchar(100) DEFAULT NULL COMMENT '头像图片路径',
  `regTime` date NOT NULL COMMENT '注册时间',
  `locked` tinyint(1) DEFAULT NULL COMMENT '账号是否被锁定',
  `rights` varchar(100) DEFAULT NULL COMMENT '权限（没有使用）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_u_1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`phone`,`sex`,`email`,`mark`,`rank`,`lastLogin`,`loginIp`,`imageUrl`,`regTime`,`locked`,`rights`) values (1,'admin','28dca2a7b33b7413ad3bce1d58c26dd679c799f1','1552323312','男','313222@foxmail.com','超级管理员','admin','2017-08-12','0:0:0:0:0:0:0:1','/static/images/','2017-03-15',0,NULL),(2,'sys','e68feeafe796b666a2e21089eb7aae9c678bf82d','1552323312','男','313222@foxmail.com','系统管理员','sys','2017-08-25','127.0.0.1','/static/images/','2017-03-15',0,NULL),(3,'user','adf8e0d0828bde6e90c2bab72e7a2a763d88a0de','1552323312','男','313222@foxmail.com','用户','user','2017-08-18','127.0.0.1','/static/images/','2017-03-15',0,NULL),(4,'test','123','12332233212','保密','2312@qq.com','没有备注','user','2017-11-25','127.0.0.1',NULL,'2017-11-25',0,NULL);

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

insert  into `sys_user_role`(`userId`,`roleId`) values (1,1),(1,2),(2,2),(1,3),(2,3),(3,3),(1,4),(3,4),(1,6),(1,7),(3,7),(1,9),(9,9);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
