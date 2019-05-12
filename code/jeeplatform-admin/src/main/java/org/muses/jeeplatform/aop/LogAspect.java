package org.muses.jeeplatform.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.muses.jeeplatform.annotation.LogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author caiyuyu
 * @date 2017/10/30
 * @since 1.0.1
 */
@Aspect
@Component
public class LogAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //@Pointcut("execution(public * org.muses.jeeplatform.web.controller..*.*(..))")
    //public void controllerAspect() {}
    @Pointcut("@annotation(org.muses.jeeplatform.annotation.LogController)")
    public void controllerAspect(){}

    /*@Pointcut("execution(public * org.muses.jeeplatform.service..*.*(..))")
    public void serviceAspect() {}*/
    @Pointcut("@annotation(org.muses.jeeplatform.annotation.LogService)")
    public void serviceAspect(){}


    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        Object result = new Object();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        LogController logCotroller = null;
        if(method != null){
            logCotroller = method.getAnnotation(LogController.class);
        }
        if(logCotroller == null){
            return result;
        }

        try {
            LOGGER.info("前置通知开始>>");
            LOGGER.info("请求接口:" + request.getMethod() + "[" + request.getRequestURL() + "]");
            LOGGER.info("请求参数:" + request.getQueryString());

            result = joinPoint.proceed();

            LOGGER.info("后置通知开始>>");
            LOGGER.info("接口返回:" + JSON.toJSONString(result));
        }catch (Exception e){
            LOGGER.error("全局异常处理>>");
            LOGGER.error("异常信息:{}", e.getMessage());
        }
        return result;
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
            }
        }
        try {
              /** 控制台输出 **/
            LOGGER.info("异常通知开始>>");
            LOGGER.info("异常代码:" + e.getClass().getName());
            LOGGER.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            LOGGER.info("请求IP:" + ip);
            LOGGER.info("请求参数:" + params);
        }catch (Exception ex) {
            //记录本地异常日志
            LOGGER.error("异常通知异常>>");
            LOGGER.error("异常信息:{}", ex.getMessage());
        }
    }

    /**
     * 判断是否需要记录日志
     */
    @Deprecated
    private boolean needToLog(Method method) {
        return method.getAnnotation(GetMapping.class) == null
                       && !method.getDeclaringClass().equals(ExceptionHandler.class);
    }


}
