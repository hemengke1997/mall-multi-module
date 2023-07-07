package com.minko.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductAttrInfo {
    @ApiModelProperty("商品属性ID")
    private Long attributeId;
    @ApiModelProperty("商品属性分类ID")
    private Long attributeCategoryId;
}
