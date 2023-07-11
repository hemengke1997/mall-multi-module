package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsCouponHistory;

public interface SmsCouponHistoryService extends IService<SmsCouponHistory> {
    Page<SmsCouponHistory> selectPage(Page<SmsCouponHistory> page, Long couponId, Integer useStatus, String orderSn);
}
