package com.pepsiwyl.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:46
 */

@EnableTransactionManagement
@MapperScan("com.pepsiwyl.mapper")

@Configuration
public class MybatisPlusConfig {

}
