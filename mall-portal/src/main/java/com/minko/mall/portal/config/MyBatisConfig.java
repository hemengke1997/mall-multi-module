package com.minko.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.minko.mall.mapper", "com.minko.mall.portal.dao"})
public class MyBatisConfig {

}
