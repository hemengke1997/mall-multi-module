package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsProductAttribute;
import com.minko.mall.dto.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductAttributeDao extends BaseMapper<PmsProductAttribute> {
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
