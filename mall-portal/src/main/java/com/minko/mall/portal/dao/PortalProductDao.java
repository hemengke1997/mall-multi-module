package com.minko.mall.portal.dao;

import com.minko.mall.model.SmsCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortalProductDao {
    /**
     * 获取可用优惠券列表
     */
    List<SmsCoupon> getAvailableCouponList(@Param("productId") Long id, @Param("productCategoryId") Long productCategoryId);
}
