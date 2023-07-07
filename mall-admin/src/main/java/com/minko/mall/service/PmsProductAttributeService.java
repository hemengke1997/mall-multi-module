package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.model.PmsProductAttribute;

import java.util.List;

public interface PmsProductAttributeService extends IService<PmsProductAttribute> {
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
