package com.minko.mall.portal.config;

import com.minko.mall.common.config.BaseSwaggerConfig;
import com.minko.mall.common.domain.SwaggerGlobalPars;
import com.minko.mall.common.domain.SwaggerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends BaseSwaggerConfig {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.minko.mall.portal.controller")
                .groupName("前台网站分组")
                .title("前台网站")
                .description("mall前台相关接口文档")
                .contactName("hemengke")
                .version("1.0")
                .build();
    }

    @Override
    public SwaggerGlobalPars swaggerGlobalPars() {
        return SwaggerGlobalPars.builder()
                .name(tokenHeader)
                .defaultValue("")
                .description("认证token")
                .modelRef(new ModelRef("string"))
                .paramType("header")
                .required(false)
                .build();
    }
}
