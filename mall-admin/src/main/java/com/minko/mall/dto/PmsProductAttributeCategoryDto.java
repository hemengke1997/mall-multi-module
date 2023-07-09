package com.minko.mall.dto;

import com.minko.mall.model.PmsProductAttribute;
import com.minko.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductAttributeCategoryDto extends PmsProductAttributeCategory {
    @Getter
    @Setter
    @ApiModelProperty("商品属性列表")
    private List<PmsProductAttribute> productAttributeList;
}
