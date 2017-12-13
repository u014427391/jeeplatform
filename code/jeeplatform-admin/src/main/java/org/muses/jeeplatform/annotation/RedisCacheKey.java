package org.muses.jeeplatform.annotation;

import java.lang.annotation.*;

/**
 * 元注解 RedisCacheKey 是方法级别的注解，用来标注要查询数据的主键
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheKey {
}
