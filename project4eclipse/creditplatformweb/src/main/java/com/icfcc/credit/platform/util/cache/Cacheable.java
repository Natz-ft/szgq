package com.icfcc.credit.platform.util.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable
{
    
    public String key() default ""; // 缓存key
    
    public int expire() default 0; // 缓存多少秒,默认无限期
}
