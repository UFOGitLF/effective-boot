package com.fly.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContext 工具类
 * @author liufei
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 为防止出现数据错乱问题
     */
    public static final ThreadLocal<ApplicationContext> APPLICATION_CONTEXT = new ThreadLocal<>();

    public static Object getBean(String name) {
        return APPLICATION_CONTEXT.get().getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return APPLICATION_CONTEXT.get().getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return APPLICATION_CONTEXT.get().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return APPLICATION_CONTEXT.get().isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return APPLICATION_CONTEXT.get().getType(name);
    }

    public static void remove(){
        APPLICATION_CONTEXT.remove();
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        APPLICATION_CONTEXT.set(applicationContext);
    }
}
