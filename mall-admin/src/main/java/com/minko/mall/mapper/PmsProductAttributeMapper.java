package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.model.PmsProductAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductAttributeMapper extends BaseMapper<PmsProductAttribute> {
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
