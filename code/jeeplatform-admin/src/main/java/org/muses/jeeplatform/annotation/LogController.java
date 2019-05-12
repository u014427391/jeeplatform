package org.muses.jeeplatform.annotation;

import java.lang.annotation.*;

/**
 * 加了@LogController的地方才打印日志
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogController {
//    RedisCacheNamespace nameSpace();

    /**
     * 模块
     * @return
     */
    String title() default "";

    /**
     * 功能
     * @return
     */
    String action() default "";
}

