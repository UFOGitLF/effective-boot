package com.fly.datasources.aspect;

import com.fly.datasources.DataSourceNames;
import com.fly.datasources.DynamicDataSource;
import com.fly.datasources.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author : liufei on 2018/4/8
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {

    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.fly.datasources.annotation.DataSource)")
    public void pointCut(){

    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (dataSource == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            log.debug("####设置数据源为:{}####",DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(dataSource.name());
            log.debug("####设置数据源为:{}####",dataSource.name());
        }

        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            log.debug("####操作完成,数据源已清空");
        }
    }
}
