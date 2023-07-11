package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.SmsCouponParam;
import com.minko.mall.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

public interface SmsCouponService extends IService<SmsCoupon> {
    Page<SmsCoupon> selectList(Page<SmsCoupon> page, String name, Integer type);

    /**
     * 添加优惠券
     */
    @Transactional
    int create(SmsCouponParam coupon);

    /**
     * 根据优惠券id删除优惠券
     */
    @Transactional
    int delete(Long id);

    @Transactional
    int update(Long id, SmsCouponParam couponParam);
}
