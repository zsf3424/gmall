package com.atguigu.gmall.index.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zsf
 * @create 2019-11-11 19:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GmallCache {

    /**
     * 缓存前缀
     * @return
     */
    String prefix() default "cache";

    /**
     * 单位是秒
     * @return
     */
    long timeout() default 300L;

    /**
     * 为了防止缓存雪崩，而设置的过期时间的随机值范围
     * @return
     */
    long random() default 300L;
}
