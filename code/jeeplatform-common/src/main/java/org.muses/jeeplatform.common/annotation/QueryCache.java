package org.muses.jeeplatform.common.annotation;

import org.apache.ibatis.annotations.CacheNamespace;

import java.lang.annotation.*;

/**
 * 申明接口在JVM使用
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCache {
    CacheNamespace nameSpace();
}
