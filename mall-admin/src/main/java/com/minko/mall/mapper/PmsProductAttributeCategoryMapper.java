package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import com.minko.mall.model.PmsProductAttributeCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductAttributeCategoryMapper extends BaseMapper<PmsProductAttributeCategory> {
    List<PmsProductAttributeCategoryDto> getListWithAttr();
}
