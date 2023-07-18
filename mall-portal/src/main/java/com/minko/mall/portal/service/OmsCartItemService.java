package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.OmsCartItem;
import com.minko.mall.portal.domain.CartPromotionItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsCartItemService extends IService<OmsCartItem> {
    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    @Transactional
    int add(OmsCartItem cartItem);

    List<OmsCartItem> list(Long id);

    /**
     * 修改购物车中某个商品的数量
     */
    int updateQuantity(Long id, Long id1, Integer quantity);

    /**
     * 清空购物车
     */
    int clear();

    /**
     * 获取包含促销活动信息的购物车列表
     */
    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);

    /**
     * 批量删除购物车中的商品
     */
    int delete(Long memberId, List<Long> ids);
}
