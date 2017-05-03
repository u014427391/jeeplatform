# JEEPlatform
一款企业信息化开发基础平台，可以用于快速构建企业后台管理系统，集成了OA(办公自动化)、SCM(供应链系统)、ERP(企业资源管理系统)、CMS(内容管理系统)、CRM(客户关系管理系统)等企业系统的通用业务功能

JeePlatform项目是一款以Activiti为工作流引擎，以Spring Framework为核心框架，集ORM框架Mybatis，Web层框架SpringMVC和多种开源组件框架而成的一款通用基础平台，基于本平台可以实现快速开发，实现企业信息管理的高效、高性能开发。系统追求安全、性能方面的有效实现。

## 开发团队(按加入顺序) ##

Tornado
Blog：http://blog.caiyuyu.net
Github：https://github.com/yinyizhixian

Wiatingpub
Blog：http://www.jianshu.com/u/6c0bb349990c
Github：https://github.com/wiatingpub

Joryun
Blog：http://www.jianshu.com/u/85656cc35708
github：https://github.com/Joryun

Serious
Blog：http://my.csdn.net/serious_czt
Github：https://github.com/CHEN0409

Simba
Blog：http://my.csdn.net/io277800
Github：https://github.com/Simba9980

Nicky
Blog：http://blog.ittrading.cn/
Github：https://github.com/u014427391

Jasonsama
Blog：https://jasonsama.github.io/
Github：https://github.com/Jasonsama


## 业务方案 ##
### 系统管理通用功能 ####
- 用户管理: 系统用户
- 角色管理: 按照企业系统职能进行角色分配，每个角色具有不同的系统操作权限
- 权限管理: 权限管理细分到系统按钮权限，菜单权限，管理员可以对权限进行细分控制
- 在线管理: 管理在线用户，可以强制下线
- 配置管理: 配置数据字典、系统接口
- 菜单管理: 系统可以配置系统菜单，并分配不同的权限
- 数据源管理: 配置多数据源
- 代码生成器: 在线生成代码,打包下载.可自定义模板
- 定时任务: 配置定时任务,使用动态脚本编写任务内容
- 缓存监控: 监控缓存情况
- 访问日志: 记录用户每次操作情况

### OA系统通用功能 ###
- 文档管理：文档管理、回收站
- 考勤管理：考勤历史、请假流程
- 人事管理：机构管理、部门管理、员工管理
- 日程管理：部门日程、我的日程、我的便签

### CMS系统通用功能 ###
- 信息管理：文章管理、归档管理、文章审核
- 站点管理：栏目管理、数据统计、频道管理

...

## 技术方案 ##
### 后台技术 ###
- 工作流引擎：Activiti5
- ORM框架：Mybatis/Hibernate JPA
- Web框架：SpringMVC
- 核心框架：Spring Framework4.0
- 任务调度：Spring Task
- 权限安全：Apache Shiro/Spring Security
- 全文搜索引擎：Lucene/Solr
- 页面静态化处理：Freemark/Velocity
- 服务器页面包含技术：SSI
- 网页即时通讯：long polling/websocket
- 连接池：Druid（阿里开源）
- 日志处理：SLF4J
- 缓存处理：Redis、EhCache
- Excel表处理：POI

### 前端技术 ###
- 文件上传：JQuery uploadify
- 树形结构：EasyUI Tree
- 日期插件：JQuery Date
- 弹窗框架：zDialog
- Cookie保存：JQuery Cookie
- 富文本编辑器：Baidu UEDitor
- 前端框架：Twitter Bootstrap、ExtJS

### 服务器 ####
- 负载均衡：Nginx
- 分布式：alibaba Dubbo
- 中间件：RocketMQ

### 项目测试 ###
- DeBug：Junit、FindBugs、EclEmma
- 程序质量：Jdepend4eclipse
- 压力测试：JMeter

### 工具软件 ###
- 服务器：SecureCRT
- Java：IntelliJ IDEA/Eclipse
- 远程控制：TeamViewer
- 版本控制：Git
- Jar管理：Maven
- UML建模：ArgoUML
- Eclipse测试插件：EclEmma
- 程序质量检查插件：Jdepend4eclipse(Eclipse平台)



