package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import com.minko.mall.model.PmsProductAttributeCategory;

import java.util.List;

public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {
    List<PmsProductAttributeCategoryDto> getListWithAttr();

    Page<PmsProductAttributeCategory> selectPage(Page<PmsProductAttributeCategory> page);
}
