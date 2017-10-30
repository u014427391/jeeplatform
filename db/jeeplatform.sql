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

insert  into `sys_menu`(`menuId`,`identity`,`parentId`,`name`,`menuIcon`,`menuUrl`,`menuType`,`menuOrder`) values (1,'user',0,'用户管理','icon-search','#','1','1'),(2,NULL,1,'管理员管理','',NULL,'2','2'),(3,NULL,1,'博客用户','',NULL,'2','3'),(4,NULL,0,'文章管理','icon-bkgl','#','1','4'),(5,NULL,4,'写文章','icon-writeblog',NULL,'2','5'),(6,NULL,4,'文章管理','icon-bkgl','article/list.do','2','6'),(7,NULL,0,'系统管理','icon-item','#','1','7'),(8,NULL,7,'角色管理',NULL,NULL,'2','8'),(9,NULL,7,'权限管理','icon-search',NULL,'2','9'),(10,NULL,7,'菜单管理','icon-search','menu/getMenus.do','2','10'),(11,NULL,0,'博客配置','icon-bkgl','#','1','11'),(12,NULL,11,'链接管理','icon-link','link/doLoadData.do','2','12'),(13,NULL,11,'标签管理','icon-bklb','articleSort/labellist.do','2','13'),(14,NULL,11,'广告管理',NULL,'adv/advList.do','2','14');

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

insert  into `sys_permission`(`id`,`desc`,`name`,`menuId`) values (1,'用户管理相关权限','用户管理',1),(2,NULL,NULL,4),(3,NULL,NULL,7),(4,NULL,NULL,11),(5,NULL,NULL,10);

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

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE sys_log
(
  id INT PRIMARY KEY NOT NULL COMMENT 'id' AUTO_INCREMENT,
  logType INT NOT NULL COMMENT '日志类型',
  logOperation VARCHAR(30) NOT NULL COMMENT '操作名',
  createdTime DATETIME NOT NULL COMMENT '创建时间',
  updatedTime DATETIME NOT NULL COMMENT '修改时间',
  interface VARCHAR(200) NOT NULL COMMENT '调用的接口',
  param TEXT COMMENT '接口参数'
);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

