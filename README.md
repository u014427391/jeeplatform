[![Build Status](https://travis-ci.org/crossoverJie/JCSprout.svg?branch=master)](https://travis-ci.org/crossoverJie/jeeplatform) [![Join the chat at https://gitter.im/jeeplatform/community](https://badges.gitter.im/jeeplatform/community.svg)](https://gitter.im/jeeplatform/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## 一、项目简介 
JeePlatform项目是一款以SpringBoot为核心框架，集ORM框架Mybatis，Web层框架SpringMVC和多种开源组件框架而成的一款通用基础平台，代码已经捐赠给开源中国社区：https://www.oschina.net/p/jeeplatform

代码结构图：
```
├─jeeplatform----------------------------父项目，公共依赖
│  │
│  ├─jeeplatform-admin--------------------------基础管理系统
│  │
│  ├─jeeplatform-cms-----------------------内容管理系统
│  │
│  ├─jeeplatform-common--------------------------通用工程
│  │
│  ├─jeeplatform-oa--------------------------协调办公系统
|  |
|  |─jeeplatform-provider-----------------------平台服务中心
│  │
│  ├─jeeplatform-provider-api-----------------------平台服务API
|  |
│  ├─jeeplatform-sso-cas-----------------------CAS单点登录服务端
│  │
│  ├─jeeplatform--sso-oauth2---------------OAuth2.0单点登录服务端
│  │ 

```

## 二、系统设计 
### 系统管理(模块名称jeeplatform-admin) 
管理系统登录页面
ps:登录链接一般为：http://127.0.0.1:8080/jeeplatform/login
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/管理系统登录页面.png)

管理系统主页前端，可以适配移动端页面
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/适配移动端.png)

管理系统主页采用开源前端模板，具有换肤功能
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/系统主页墨绿主题.png)

![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/系统主页清新主题.png)

管理系统主页，获取用户具有的权限，显示菜单
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/管理系统主页.png)

角色进行授权，只有超级管理员才具有权限
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/角色授权.png)

角色进行配置，可以学习一下RBAC(基于角色的权限控制)
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/角色配置.png)

使用JavaEmail插件实现邮件发送，记得需要开启SSl验证
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/发送邮件.png)

### OA管理系统(待开发)
接入CAS Server实现单点登录
### CMS管理系统(待开发)
暂时接入Oauth2.0实现的单点登录系统

## 三、关键技术
### CAS单点登录基础(模块名称jeeplatform-sso-cas)(功能修整中)
> 项目采用CAS实现单点登录，单点登录集群搭建可以参考博客：
> http://blog.csdn.net/u014427391/article/details/78653482
> 项目单点登录：使用nginx作为负载均衡，使用redis存储tomcat session，来实现集群中tomcat session的共享，使用redis作为cas ticket的仓库，来实现集群中cas ticket的一致性。OA已经对接CAS，admin工程暂时不对接CAS


图来自官网，这里简单介绍一下，从图可以看出，CAS支持多种方式的认证，一种是LDAP的、比较常见的数据库Database的JDBC，还有Active Directory等等；支持的协议有Custom Protocol 、 CAS 、 OAuth 、 OpenID 、 RESTful API 、 SAML1.1 、 SAML2.0 等

![这里写图片描述](https://images.gitee.com/uploads/images/2020/0517/212349_a195821a_355133.png)

![这里写图片描述](https://images.gitee.com/uploads/images/2020/0517/212349_384ef37d_355133.png)

单点登录集群方案如图
![Image text](https://gitee.com/362330721/jeeplatform/raw/master/screenshot/单点登录集群.png)

### OAuth2.0单点登录基础(模块名称jeeplatform-sso-oauth2)(功能修整中)

![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0517/212350_327aefca_355133.png)

### SpringBoot集成Redis缓存处理(Spring AOP实现)
先从Redis里获取缓存,查询不到，就查询MySQL数据库，然后再保存到Redis缓存里，下次查询时直接调用Redis缓存，详情参考博客：[链接](https://blog.csdn.net/u014427391/article/details/78799623)

![这里写图片描述](http://img.blog.csdn.net/20171214104250995?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

可以看到Redis里保存到了缓存

![这里写图片描述](http://img.blog.csdn.net/20171214104303308?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 四、业务方案 
### 系统管理通用功能 
- [ ] 单点登录: OAuth2.0+JWT单点登录/CAS单点登录
- [x] 用户管理: 系统用户	
- [x] 角色管理: 按照企业系统职能进行角色分配，每个角色具有不同的系统操作权限	
- [x] 权限管理: 权限管理细分到系统菜单权限
- [ ] 在线管理：管理在线用户，可以强制下线
- [x] 菜单管理：系统可以配置系统菜单，并分配不同的权限	
- [ ] 报表统计：数据报表、用户分析
- [x] 系统监控：数据库等方面监控(采用Druid提供的)
- [x] 在线文档：SwaggerUI API在线文档管理
- [x] 通用接口：系统邮件发送功能、Excel报表功能
### OA系统通用功能(待开发) 
- [x] 单点登录: CAS单点登录
- [ ] 考勤管理：请假流程
- [ ] 人事管理：机构管理、部门管理、员工管理

### CMS系统通用功能(待开发) 
- [x] 单点登录: OAuth2.0+JWT单点登录
- [ ] 信息管理：文章管理、文章审核
...

## 五、技术方案
### 后台技术 
* 工作流引擎：Activiti5(待定)
* ORM框架：Mybatis
* Web框架：SpringMVC
* 核心框架：SpringBoot
* 任务调度：Spring Task(待定)
* 权限安全：Apache Shiro、Spring Security
* 全文搜索引擎：Lucene(待定)
* 模板引擎：JSP(还没使用Thymeleaf，前端需要重构)
* 服务器页面包含技术：SSI(待定)
* 网页即时通讯：websocket
* 连接池：Druid（阿里开源）
* 日志处理：SLF4J(日志门面框架)、logback
* 缓存处理：Redis
* Excel表处理：POI

### 前端技术 
* 文件上传：JQuery uploadify
* 树形结构：EasyUI Tree
* 日期插件：JQuery Date
* 弹窗框架：zDialog
* Cookie保存：JQuery Cookie
* 富文本编辑器：Baidu UEDitor
* 前端框架：Twitter Bootstrap

## 六、常见问题
运行jeeplatform打开页面404，如果是用idea的，就可以edit configurations->configuration->edit working directory设置为：$MODULE_DIR$

## 七、版本说明
* master版本
主干版本，实现简单的权限管理，单点登录方案有CAS和OAuth2.0+JWT两种方案，admin暂时没接单点，oa工程对接cas，cms对接OAuth2.0实现单点登录，微服务只是做了个demo，还没进行项目服务处理，所以并没有merge代码
* dev版本
dev版本代码和master分支基本一致
* 1.0.0版本
基础版，基本实现简单的权限管理，功能还需改善，权限控制还需要进行细粒度控制

* 1.1.0版本
进行单点登录对接实验的版本，拟采用两种方案，CAS实现的单点登录和OAuth2.0+JWT单点登录，admin工程暂时还没对接，oa工程对接CAS，cms工程对接OAuth2.0

## 八、项目技术博客介绍 
为了帮助学习者更好地理解代码，下面给出自己写的一些博客链接

### Java框架
* [基于RBAC模型的权限系统设计](http://blog.csdn.net/u014427391/article/details/78889378)
* [Spring Data Jpa实现分页](http://blog.csdn.net/u014427391/article/details/77434664)
* [SpringMVC+ZTree实现树形菜单权限配置](https://blog.csdn.net/u014427391/article/details/78889378)
* [企业信息化基础平台项目介绍](https://blog.csdn.net/u014427391/article/details/78867439)
* [基于Shiro的登录验证功能实现](http://blog.csdn.net/u014427391/article/details/78307766)

### SpringBoot
我的Springboot系列博客可以参考我的专栏：[SpringBoot系列博客](https://blog.csdn.net/u014427391/category_9195353.html)
* [SpringBoot热部署配置](https://smilenicky.blog.csdn.net/article/details/89765909)
* [SpringBoot集成Redis实现缓存处理](http://blog.csdn.net/u014427391/article/details/78799623)
* [SpringBoot profles配置多环境](https://smilenicky.blog.csdn.net/article/details/89792248)
* [SpringBoot集成Swagger2](https://smilenicky.blog.csdn.net/article/details/90706219)

### RPC框架
* [Dubbo服务注册与发现](https://smilenicky.blog.csdn.net/article/details/96754952)

### 单点登录
* [ 单点登录集群安装教程](http://blog.csdn.net/u014427391/article/details/78653482)
* [CAS单点登录系列之原理简单介绍](https://blog.csdn.net/u014427391/article/details/82083995)
* [CAS系列之使用cas overlay搭建服务端（一）](https://blog.csdn.net/u014427391/article/details/105818468)
* [CAS 5.3.1系列之支持JDBC认证登录（二）](https://blog.csdn.net/u014427391/article/details/105603895)
* [CAS 5.3.1系列之自定义JDBC认证策略（三）](https://blog.csdn.net/u014427391/article/details/105820486)
* [CAS 5.3.1系列之自定义Shiro认证策略（四）](https://blog.csdn.net/u014427391/article/details/105820586)

### Docker笔记
* [Docker简介和安装教程](https://smilenicky.blog.csdn.net/article/details/97613891)




