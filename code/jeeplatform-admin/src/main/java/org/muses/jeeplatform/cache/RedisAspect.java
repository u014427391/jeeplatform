//package org.muses.jeeplatform.cache;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class RedisAspect {
//
//	@Autowired
//	@Qualifier("redisCache")
//	private RedisCache redisCache;
//
//	//@Pointcut("@annotation(com.muses.jeeplatform.getCache)")
//	@Pointcut("")
//	public void pointcutMethod(){
//
//	}
//
//	@Around("pointCutMethod()")
//	public Object around(ProceedingJoinPoint joinPoint){
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
//			System.out.println("从Redis中查到了数据");
//			return obj;
//		}
//
//		System.out.println("没有从Redis查到数据");
//
//		try{
//			obj = joinPoint.proceed();
//		}catch(Throwable e){
//			e.printStackTrace();
//		}
//
//		System.out.println("从数据库中查数据");
//
//		redisCache.saveDataToRedis(redisKey,obj);
//
//		return obj;
//	}
//
//
//
//}
