package com.minko.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.minko.mall.mapper", "com.minko.mall.dao"})
public class MyBatisConfig {

}
