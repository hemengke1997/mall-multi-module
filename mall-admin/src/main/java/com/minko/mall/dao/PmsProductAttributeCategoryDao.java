package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsProductAttributeCategory;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductAttributeCategoryDao extends BaseMapper<PmsProductAttributeCategory> {
    List<PmsProductAttributeCategoryDto> getListWithAttr();
}
