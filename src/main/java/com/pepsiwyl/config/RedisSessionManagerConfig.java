package com.pepsiwyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author by pepsi-wyl
 * @date 2022-07-25 17:04
 */

@Configuration
@EnableRedisHttpSession // 整个应用的session数据交给redis处理
public class RedisSessionManagerConfig {

}
