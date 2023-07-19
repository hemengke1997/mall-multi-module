package com.minko.mall.search.config;

import com.minko.mall.common.config.BaseSwaggerConfig;
import com.minko.mall.common.domain.SwaggerGlobalPars;
import com.minko.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.minko.mall.search.controller")
                .groupName("搜索网站分组")
                .title("搜索系统")
                .description("mall搜索相关接口文档")
                .contactName("hemengke")
                .version("1.0")
                .build();
    }

    @Override
    public SwaggerGlobalPars swaggerGlobalPars() {
        return null;
    }
}
