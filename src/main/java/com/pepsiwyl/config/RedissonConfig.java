package com.pepsiwyl.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by pepsi-wyl
 * @date 2022-07-25 21:39
 */

@Configuration
public class RedissonConfig {
    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://192.168.131.130:6379");
        singleServerConfig.setPassword("root");
        return Redisson.create(config);
    }
}
