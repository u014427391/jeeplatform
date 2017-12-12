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

import javax.annotation.Resource;

/**
 * AOP实现Redis缓存处理
 */
@Component
@Aspect
public class RedisAspect {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("redisCache")
	private RedisCache redisCache;

//	/**
//	 * 拦截所有元注解RedisCache注解的方法
//	 */
//	@Pointcut("@annotation(org.muses.common.annotation.RedisCache)")
//	public void pointcutMethod(){
//
//	}
//
//	/**
//	 * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
//	 * 然后再保存到Redis缓存里
//	 * @param joinPoint
//	 * @return
//	 */
//	@Around("pointcutMethod()")
//	public Object around(ProceedingJoinPoint joinPoint){
//
//		//前置：从Redis里获取缓存
//		String appId = null;
//
//		//先获取目标方法参数
//		Object[] args = joinPoint.getArgs();
//		if(args!=null && args.length>0){
//			appId=String.valueOf(args[0]);
//		}
//		//redis中ke的key
//		String redisKey = appId;
//
//		Object obj = redisCache.getDataFromRedis(redisKey);
//
//		if(obj!=null){
//			log.info("从Redis中查到了数据");
//			return obj;
//		}
//
//		log.info("没有从Redis查到数据");
//
//		try{
//			obj = joinPoint.proceed();
//		}catch(Throwable e){
//			e.printStackTrace();
//		}
//
//		//后置：将数据库查到的数据保存到Redis
//		redisCache.saveDataToRedis(redisKey,obj);
//
//		return obj;
//	}

}
