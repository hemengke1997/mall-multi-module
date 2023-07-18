package com.minko.mall.portal.service;

import com.minko.mall.model.SmsCoupon;
import com.minko.mall.portal.domain.CartPromotionItem;
import com.minko.mall.portal.domain.SmsCouponHistoryDetail;

import java.util.List;

public interface UmsMemberCouponService {
    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);

    List<SmsCoupon> list(Integer useStatus);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type);
}
