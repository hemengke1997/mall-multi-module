package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsProductAttributeParam;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductAttributeService extends IService<PmsProductAttribute> {
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

    Page<PmsProductAttribute> selectPage(Long cid, Integer type, Page<PmsProductAttribute> page);

    int updateItem(Long id, PmsProductAttributeParam productAttributeParam);

    @Transactional
    int create(PmsProductAttributeParam productAttributeParam);

    @Transactional
    int delete(List<Long> ids);
}
