package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.model.PmsProduct;

import java.util.List;

public interface PmsPortalBrandService extends IService<PmsBrand> {
    /**
     * 获取推荐品牌列表
     */
    List<PmsBrand> recommendList(Integer pageNum, Integer pageSize);

    /**
     * 获取品牌详情
     */
    PmsBrand detail(Long brandId);

    /**
     * 分页获取品牌相关商品
     */
    Page<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);
}
