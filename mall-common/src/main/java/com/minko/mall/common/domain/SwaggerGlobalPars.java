package com.minko.mall.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.schema.ModelReference;

@Data
@EqualsAndHashCode
@Builder
public class SwaggerGlobalPars {
    private String name;
    private String description;
    private String defaultValue;
    private boolean required;
    private String paramType;
    private ModelReference modelRef;
}
