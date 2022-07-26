package com.pepsiwyl.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 19:25
 */

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    // 类成员变量 保存工厂
    private static ApplicationContext applicationContext;

    // 将创建好的工厂 以参数形式传递给这个类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    // 获取工厂中的对象
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }


    public static RedisTemplate<String, Object> getRedisTemplate() {
        return ApplicationContextUtils.getBean("redisTemplate", RedisTemplate.class);
    }
}
