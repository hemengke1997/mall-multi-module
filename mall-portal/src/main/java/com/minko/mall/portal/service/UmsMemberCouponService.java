package com.minko.mall.portal.service;

import com.minko.mall.model.SmsCoupon;

import java.util.List;

public interface UmsMemberCouponService {
    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);
}
