package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.mapper.PmsProductAttributeMapper;
import com.minko.mall.model.PmsProductAttribute;
import com.minko.mall.service.PmsProductAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        
        return null;
    }
}
