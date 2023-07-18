package com.minko.mall.portal.domain;

import com.minko.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsProductCategoryNode extends PmsProductCategory {
    @ApiModelProperty("子分类集合")
    private List<PmsProductCategoryNode> children;
}
