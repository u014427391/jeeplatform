package org.muses.jeeplatform.common.annotation;

import org.apache.ibatis.annotations.CacheNamespace;

import java.lang.annotation.*;

/**
 * 元注解申明查询缓存的接口，设置Retention在JVM里
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCache {
    CacheNamespace nameSpace();
}
