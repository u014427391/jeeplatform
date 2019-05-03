
## 项目简介 ##
一款企业信息化开发基础平台，拟集成OA(办公自动化)、SCM(供应链系统)、ERP(企业资源管理系统)、CMS(内容管理系统)、CRM(客户关系管理系统)等企业系统的通用业务功能

JeePlatform项目是一款以Spring Framework为核心框架，集ORM框架Mybatis，Web层框架SpringMVC和多种开源组件框架而成的一款通用基础平台，代码已经捐赠给开源中国社区：https://www.oschina.net/p/jeeplatform

## 系统设计 ##
### 系统管理(模块名称jeeplatform-admin) ###
管理系统登录页面，采用Shiro登录验证
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/管理系统登录页面.png)

管理系统主页前端，可以适配移动端页面
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/适配移动端.png)

管理系统主页采用开源前端模板，具有换肤功能
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/系统主页墨绿主题.png)

![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/系统主页清新主题.png)

管理系统主页，获取用户具有的权限，显示菜单
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/管理系统主页.png)

角色进行授权，只有超级管理员才具有权限
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/角色授权.png)

角色进行配置，可以学习一下RBAC(基于角色的权限控制)
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/角色配置.png)

使用JavaEmail插件实现邮件发送，记得需要开启SSl验证
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/发送邮件.png)

### OA管理系统(待开发)

### CMS管理系统(待开发)

## 系统升级
### 单点登录基础(模块名称jeeplatform-sso)(开发中)
> 项目采用CAS登录登录实现，单点登录集群搭建可以参考博客：
> http://blog.csdn.net/u014427391/article/details/78653482
> 项目单点登录：使用nginx作为负载均衡，使用redis存储tomcat session，来实现集群中tomcat session的共享，使用redis作为cas ticket的仓库，来实现集群中cas ticket的一致性。

单点登录集群如图
![Image text](https://github.com/u014427391/jeeplatform/raw/master/screenshot/单点登录集群.png)

### SpringBoot集成Redis缓存处理(Spring AOP实现)
先从Redis里获取缓存,查询不到，就查询MySQL数据库，然后再保存到Redis缓存里，下次查询时直接调用Redis缓存

```
package org.muses.jeeplatform.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * AOP实现Redis缓存处理
 */
@Component
@Aspect
public class RedisAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisAspect.class);

	@Autowired
    @Qualifier("redisCache")
	private RedisCache redisCache;

	/**
	 * 拦截所有元注解RedisCache注解的方法
	 */
	@Pointcut("@annotation(org.muses.jeeplatform.annotation.RedisCache)")
	public void pointcutMethod(){

	}

	/**
	 * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
	 * 然后再保存到Redis缓存里
	 * @param joinPoint
	 * @return
	 */
	@Around("pointcutMethod()")
	public Object around(ProceedingJoinPoint joinPoint){
		//前置：从Redis里获取缓存
		//先获取目标方法参数
		long startTime = System.currentTimeMillis();
		String applId = null;
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			applId = String.valueOf(args[0]);
		}

		//获取目标方法所在类
		String target = joinPoint.getTarget().toString();
		String className = target.split("@")[0];

		//获取目标方法的方法名称
		String methodName = joinPoint.getSignature().getName();

		//redis中key格式：    applId:方法名称
		String redisKey = applId + ":" + className + "." + methodName;

		Object obj = redisCache.getDataFromRedis(redisKey);

		if(obj!=null){
			LOGGER.info("**********从Redis中查到了数据**********");
			LOGGER.info("Redis的KEY值:"+redisKey);
			LOGGER.info("REDIS的VALUE值:"+obj.toString());
			return obj;
		}
		long endTime = System.currentTimeMillis();
		LOGGER.info("Redis缓存AOP处理所用时间:"+(endTime-startTime));
		LOGGER.info("**********没有从Redis查到数据**********");
		try{
			obj = joinPoint.proceed();
		}catch(Throwable e){
			e.printStackTrace();
		}
		LOGGER.info("**********开始从MySQL查询数据**********");
		//后置：将数据库查到的数据保存到Redis
		String code = redisCache.saveDataToRedis(redisKey,obj);
		if(code.equals("OK")){
			LOGGER.info("**********数据成功保存到Redis缓存!!!**********");
			LOGGER.info("Redis的KEY值:"+redisKey);
			LOGGER.info("REDIS的VALUE值:"+obj.toString());
		}
		return obj;
	}


}

```
![这里写图片描述](http://img.blog.csdn.net/20171214104250995?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

可以看到Redis里保存到了缓存

![这里写图片描述](http://img.blog.csdn.net/20171214104303308?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxNDQyNzM5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 业务方案 ##
### 系统管理通用功能 ####
- [x] 用户管理: 系统用户	
- [x] 角色管理: 按照企业系统职能进行角色分配，每个角色具有不同的系统操作权限	OK
- [x] 权限管理: 权限管理细分到系统按钮权限，菜单权限，管理员可以对权限进行细分控制
- [ ] 在线管理：管理在线用户，可以强制下线
- [x] 菜单管理：系统可以配置系统菜单，并分配不同的权限	OK
- [ ] 报表统计：数据报表、用户分析
- [ ] 系统监控：数据监控、系统日志(用户登录记录)
- [ ] 通用接口：SMS(短信)、系统邮件、Excel表导出导入操作...
### OA系统通用功能(待开发) ###
- [ ] 考勤管理：请假流程
- [ ] 人事管理：机构管理、部门管理、员工管理

### CMS系统通用功能(待开发) ###
- [ ] 信息管理：文章管理、文章审核
...

## 技术方案 ##
### 后台技术 ###
* 工作流引擎：Activiti5
* ORM框架：Mybatis/Hibernate JPA
* Web框架：SpringMVC
* 核心框架：Spring Framework4.0
* 任务调度：Spring Task
* 权限安全：Apache Shiro/Spring Security
* 全文搜索引擎：Lucene/Solr
* 页面静态化处理：Freemark/Velocity
* 服务器页面包含技术：SSI
* 网页即时通讯：long polling/websocket
* 连接池：Druid（阿里开源）
* 日志处理：SLF4J
* 缓存处理：Redis、EhCache
* Excel表处理：POI

### 前端技术 ###
* 文件上传：JQuery uploadify
* 树形结构：EasyUI Tree
* 日期插件：JQuery Date
* 弹窗框架：zDialog
* Cookie保存：JQuery Cookie
* 富文本编辑器：Baidu UEDitor
* 前端框架：Twitter Bootstrap、ExtJS

### 服务器 ####
* 负载均衡：Nginx
* 分布式：alibaba Dubbo
* 中间件：RocketMQ

### 项目测试 ###
* DeBug：Junit、FindBugs、EclEmma
* 程序质量：Jdepend4eclipse
* 压力测试：JMeter

### 工具软件 ###
* 服务器：SecureCRT
* Java：IntelliJ IDEA/Eclipse
* 远程控制：TeamViewer
* 版本控制：Git
* Jar管理：Maven
* UML建模：ArgoUML
* Eclipse测试插件：EclEmma
* 程序质量检查插件：Jdepend4eclipse(Eclipse平台)
## 常见问题 ##
运行jeeplatform打开页面404，如果是用idea的，就可以edit configurations->configuration->edit working directory设置为：$MODULE_DIR$
## 项目技术博客介绍 ##
为了帮助学习者更好地理解代码，下面给出自己写的一些博客链接

### Java框架
* [基于RBAC模型的权限系统设计(Github开源项目)](http://blog.csdn.net/u014427391/article/details/78889378)
* [Spring Data Jpa+SpringMVC+Jquery.pagination.js实现分页](http://blog.csdn.net/u014427391/article/details/77434664)
* [SpringMVC+ZTree实现树形菜单权限配置](https://blog.csdn.net/u014427391/article/details/78889378)
* [Github开源项目(企业信息化基础平台)](https://blog.csdn.net/u014427391/article/details/78867439)
* [基于权限安全框架Shiro的登录验证功能实现](http://blog.csdn.net/u014427391/article/details/78307766)

SpringBoot
* [SpringBoot热部署配置](https://smilenicky.blog.csdn.net/article/details/89765909)
* [SpringBoot profles配置多环境](https://smilenicky.blog.csdn.net/article/details/89792248)

### Redis知识
* [Redis学习笔记之基本数据结构](https://blog.csdn.net/u014427391/article/details/82860694)
* [SpringBoot集成Redis实现缓存处理(Spring AOP技术)](http://blog.csdn.net/u014427391/article/details/78799623)
* [Redis学习笔记之位图](https://blog.csdn.net/u014427391/article/details/87923407)
* [Redis学习笔记之延时队列](https://blog.csdn.net/u014427391/article/details/87905450)
* [Redis学习笔记之分布式锁](https://blog.csdn.net/u014427391/article/details/84934045)

### Oracle知识
* [Oracle知识整理笔录](https://blog.csdn.net/u014427391/article/details/82317376)
* [Oracle笔记之锁表和解锁](https://blog.csdn.net/u014427391/article/details/83046148)
* [Oracle笔记之修改表字段类型](https://blog.csdn.net/u014427391/article/details/83046006)
* [Oracle merge合并更新函数](https://blog.csdn.net/u014427391/article/details/87898729)
* [oracle select in超过1000条报错解决方法](https://blog.csdn.net/u014427391/article/details/87922878)

### 单点登录
* [ 单点登录集群安装教程](http://blog.csdn.net/u014427391/article/details/78653482)


### SQL调优知识
* [Oracle优化器基础知识](https://blog.csdn.net/u014427391/article/details/88650696)
* [Oracle性能调优之虚拟索引用法简介](https://smilenicky.blog.csdn.net/article/details/89761234)
* [Oracle性能调优之物化视图用法简介](https://smilenicky.blog.csdn.net/article/details/89762680)
* [Orace执行计划学习笔记](https://smilenicky.blog.csdn.net/article/details/89604262)
* [Oracle sql共享池$sqlarea分析SQL资源使用情况](https://blog.csdn.net/u014427391/article/details/86562755)



