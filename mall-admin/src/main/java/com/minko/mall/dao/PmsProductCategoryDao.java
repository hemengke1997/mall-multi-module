package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.dto.PmsProductCategoryWithChildrenItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsProductCategoryDao extends BaseMapper<PmsProductCategory> {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
