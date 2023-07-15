package com.minko.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.minko.mall.portal.mapper", "com.minko.mall.portal.dao"})
public class MyBatisConfig {

}
