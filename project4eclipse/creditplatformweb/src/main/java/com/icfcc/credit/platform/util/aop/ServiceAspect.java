package com.icfcc.credit.platform.util.aop;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.util.cache.CacheEvict;
import com.icfcc.credit.platform.util.cache.Cacheable;
import com.icfcc.credit.platform.util.redis.RedisService;

/**
 * 系统服务组件Aspect切面Bean
 * 
 * @author 
 * @date 2016-3-28
 */
// 声明这是一个组件
@Component
// 声明这是一个切面Bean
@Aspect
public class ServiceAspect
{
    
    private final static Log log = LogFactory.getLog(ServiceAspect.class);
    
    @Autowired
    private RedisService redisService;
    
    @Pointcut("execution(* com.icfcc.credit.service.system.*.*(..))")
    public void fooExecution()
    {
    }
    
    /*
     * @Pointcut(value = "@annotation(cache)") public void doCache(Cacheable cache) { }
     */
    
    @Pointcut(value = "@annotation(cacheEvict)")
    public void clearCache(CacheEvict cacheEvict)
    {
    }
    
    @Before("fooExecution()")
    public void doRelease(JoinPoint point)
    {
        /*
         * log.info("@@Before：模拟释放资源..."); System.out.println("@@Before：目标方法为：" +
         * point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
         * System.out.println("@@Before：参数为：" + Arrays.toString(point.getArgs()));
         * System.out.println("@@Before：被织入的目标对象为：" + point.getTarget().toString());
         */
        
    }
    
    @After("fooExecution()")
    public void doReleaseLock(JoinPoint point)
    {
        /*
         * log.info("@After：模拟释放资源..."); System.out.println("@After：目标方法为：" +
         * point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
         * System.out.println("@After：参数为：" + Arrays.toString(point.getArgs())); System.out.println("@After：被织入的目标对象为："
         * + point.getTarget().toString());
         */
    }
    
    @Around("@annotation(cache)")
    public Object cacheble(final ProceedingJoinPoint pjp, Cacheable cache)
        throws Throwable
    {
        log.info("执行添加缓存操作");
        String key = getCacheKey(pjp, cache);
        ValueOperations<Object, Object> valueOper = redisService.getOpsForValue();
        // 从缓存获取数据
        Object value = valueOper.get(key);
        if (value != null)
        {
            log.info("缓存存在直接返回");
            // 如果有数据,则直接返回
            return value;
        }
        // 跳过缓存,到后端查询数据
        value = pjp.proceed();
        if (cache.expire() <= 0)
        {
            // 如果没有设置过期时间,则无限期缓存
            valueOper.set(key, value);
        }
        else
        {
            // 否则设置缓存时间
            valueOper.set(key, value, cache.expire(), TimeUnit.SECONDS);
        }
        log.info("添加缓存操作完成,返回最新的缓存值");
        return value;
    }
    
    @After("@annotation(cacheEvict)")
    public void cacheEvict(final JoinPoint pjp, CacheEvict cacheEvict)
        throws Throwable
    {
        log.info("执行清理缓存操作-开始");
        String key = getCacheEvictKey(pjp, cacheEvict);
        redisService.delBykey(0, key);
        log.info("执行清理缓存操作-结束");
    }
    
    /**
     * 获取缓存的key值
     * 
     * @param pjp
     * @param cache
     * @return
     */
    private String getCacheKey(ProceedingJoinPoint pjp, Cacheable cache)
    {
        StringBuilder buf = new StringBuilder();
        if (cache.key().length() > 0)
        {
            buf.append(cache.key());
        }
        
        Object[] args = pjp.getArgs();
        
        for (Object arg : args)
        {
            buf.append(".").append(arg.toString());
        }
        return buf.toString();
    }
    
    private String getCacheEvictKey(JoinPoint pjp, CacheEvict cache)
    {
        StringBuilder buf = new StringBuilder();
        if (cache.key().length() > 0)
        {
            buf.append(cache.key());
        }
        
        Object[] args = pjp.getArgs();
        
        for (Object arg : args)
        {
//            if (arg instanceof SystemButton)
//            {
//                buf.append(".").append(((SystemButton)arg).getButtonCode());
//            }
//            else
//            {
                buf.append(".").append(arg.toString());
//            }
        }
        return buf.toString();
    }
    
}