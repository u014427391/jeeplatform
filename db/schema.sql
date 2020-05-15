/*
SQLyog Ultimate v11.5 (64 bit)
MySQL - 5.7.27 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `oauth_client_details` (
	`client_id` varchar (768),
	`resource_ids` varchar (768),
	`client_secret` varchar (768),
	`scope` varchar (768),
	`authorized_grant_types` varchar (768),
	`web_server_redirect_uri` varchar (768),
	`authorities` varchar (768),
	`access_token_validity` int (11),
	`refresh_token_validity` int (11),
	`additional_information` varchar (12288),
	`autoapprove` varchar (768)
); 
insert into `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) values('cms',NULL,'{noop}secret','all','authorization_code','http://localhost:8084/cms/login',NULL,'60','60',NULL,'true');
insert into `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) values('oa',NULL,'{noop}secret','all','authorization_code','http://localhost:8082/oa/login',NULL,'60','60',NULL,'true');
