package com.minko.mall.common.config;

import com.minko.mall.common.domain.SwaggerGlobalPars;
import com.minko.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger基础配置
 */
public abstract class BaseSwaggerConfig {
    @Bean
    public Docket createRestApi() {
        SwaggerProperties swaggerProperties = this.swaggerProperties();
        SwaggerGlobalPars swaggerGlobalPars = this.swaggerGlobalPars();
        List<Parameter> pars = new ArrayList<>();
        if (swaggerGlobalPars != null) {
            ParameterBuilder tokenPar = new ParameterBuilder();
            tokenPar.name(swaggerGlobalPars.getName())
                    .description(swaggerGlobalPars.getDescription())
                    .defaultValue(swaggerGlobalPars.getDefaultValue())
                    .modelRef(swaggerGlobalPars.getModelRef())
                    .parameterType(swaggerGlobalPars.getParamType())
                    .required(swaggerGlobalPars.isRequired())
                    .build();

            pars.add(tokenPar.build());
        }


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                .groupName(swaggerProperties.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    public abstract SwaggerProperties swaggerProperties();

    public abstract SwaggerGlobalPars swaggerGlobalPars();
}
