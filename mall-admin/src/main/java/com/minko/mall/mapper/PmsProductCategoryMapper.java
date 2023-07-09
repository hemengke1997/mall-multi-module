package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.PmsProductCategoryWithChildrenItem;
import com.minko.mall.model.PmsProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
