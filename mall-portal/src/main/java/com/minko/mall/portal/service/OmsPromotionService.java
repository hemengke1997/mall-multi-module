package com.minko.mall.portal.service;

import com.minko.mall.model.OmsCartItem;
import com.minko.mall.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsPromotionService {
    /**
     * 计算购物车中的促销活动信息
     *
     * @param cartItemList 购物车
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
