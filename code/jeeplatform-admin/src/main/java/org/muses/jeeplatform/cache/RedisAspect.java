package org.muses.jeeplatform.cache;

import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.classfile.MethodParameters;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.muses.jeeplatform.common.RedisCacheNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

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
//	@Resource
//    private RedisTemplate redisCache;

//	/**
//	 * 拦截所有元注解RedisCache注解的方法
//	 */
//	@Pointcut("@annotation(org.muses.jeeplatform.annotation.RedisCache)")
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
//	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//	    long startTime = System.currentTimeMillis();
//		//前置：从Redis里获取缓存
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Method method = signature.getMethod();
//        RedisCacheNamespace redisCacheNamespace = method.getAnnotation(RedisCache.class).nameSpace();
//
//		String key = null;
//		int i = 0;
//
//		//Object obj = redisCache.getDataFromRedis(redisKey);
//		for(Object arg : joinPoint.getArgs()){
//            MethodParameter methodParameter = new SynthesizingMethodParameter(method,i);
//			Annotation[] annotations = methodParameter.getParameterAnnotations();
//
//			for(Annotation annotation:annotations){
//				if(annotation instanceof RedisCacheKey){
//					RedisCacheKey redisCacheKey = (RedisCacheKey)annotation;
//					key += redisCacheNamespace +"&"+arg;
//				}
//			}
//			i++;
//        }
//
//		// 获取不到key值
//		if (StringUtils.isBlank(key)){
//			throw new NullPointerException("缓存Key值不存在");
//		}
//		LOGGER.info("获取到的缓存Key值>>>"+key);
//
//		ValueOperations<String,Object> valueOperations = redisCache.opsForValue();
//		boolean hasKey = redisCache.hasKey(key);
//		//有Key值就从缓存中获取数据
//		if(hasKey){
//			Object obj = valueOperations.get(key);
//			LOGGER.info("从Redis缓存中获取到数据>>>"+obj.toString());
//		}
//		long endTime = System.currentTimeMillis();
//		LOGGER.info("Redis缓存AOP处理所用时间:"+(endTime-startTime));
//		//后置：缓存中没有数据，数据库查询，并保存到缓存里
//		Object obj = joinPoint.proceed();
//		valueOperations.set(key,obj,30, TimeUnit.MINUTES);
//		return obj;
//	}
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
			LOGGER.info("从Redis中查到了数据");
			return obj;
		}

		LOGGER.info("没有从Redis查到数据");

		try{
			obj = joinPoint.proceed();
		}catch(Throwable e){
			e.printStackTrace();
		}

		//后置：将数据库查到的数据保存到Redis
		redisCache.saveDataToRedis(redisKey,obj);

		return obj;
	}


}
