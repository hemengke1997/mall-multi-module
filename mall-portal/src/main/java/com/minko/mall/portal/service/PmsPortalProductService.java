package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.portal.domain.PmsPortalProductDetail;
import com.minko.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

public interface PmsPortalProductService {
    /**
     * 商品详情
     */
    PmsPortalProductDetail detail(Long id);

    /**
     * 综合搜索商品
     */
    Page<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    List<PmsProductCategoryNode> categoryTreeList();
}
