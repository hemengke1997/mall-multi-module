package com.minko.mall.portal.service;

import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.portal.domain.HomeContentResult;

import java.util.List;

public interface HomeService {
    HomeContentResult content();

    /**
     * 获取推荐商品列表
     */
    List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum);

    /**
     * 获取商品分类
     *
     * @param parentId 0:获取一级分类；其他：获取指定二级分类
     */
    List<PmsProductCategory> getProductCateList(Long parentId);

    /**
     * 获取人气商品
     */
    List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize);

    /**
     * 获取新品推荐
     */
    List<PmsProduct> newProductList(Integer pageNum, Integer pageSize);
}
