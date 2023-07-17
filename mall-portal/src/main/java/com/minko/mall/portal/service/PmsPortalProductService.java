package com.minko.mall.portal.service;

import com.minko.mall.portal.domain.PmsPortalProductDetail;

public interface PmsPortalProductService {
    /**
     * 商品详情
     */
    PmsPortalProductDetail detail(Long id);
}
