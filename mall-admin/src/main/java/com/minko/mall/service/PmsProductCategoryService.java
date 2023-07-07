package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsProductCategoryParam;
import com.minko.mall.model.PmsProductCategory;

import java.util.List;

public interface PmsProductCategoryService extends IService<PmsProductCategory> {
    int create(PmsProductCategoryParam pmsProductCategoryParam);


    Page<PmsProductCategory> selectPage(Page<PmsProductCategory> page, Long parentId);

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);
}
