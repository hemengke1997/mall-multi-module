package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsCouponHistoryMapper;
import com.minko.mall.model.SmsCouponHistory;
import com.minko.mall.service.SmsCouponHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryMapper, SmsCouponHistory> implements SmsCouponHistoryService {
    @Autowired
    private SmsCouponHistoryMapper historyMapper;

    @Override
    public Page<SmsCouponHistory> selectPage(Page<SmsCouponHistory> page, Long couponId, Integer useStatus, String orderSn) {
        LambdaQueryWrapper<SmsCouponHistory> queryWrapper = new LambdaQueryWrapper<>();

        if (couponId != null) {
            queryWrapper.eq(SmsCouponHistory::getCouponId, couponId);
        }

        if (useStatus != null) {
            queryWrapper.eq(SmsCouponHistory::getUseStatus, useStatus);
        }
        if (StrUtil.isNotEmpty(orderSn)) {
            queryWrapper.eq(SmsCouponHistory::getOrderSn, orderSn);
        }
        return historyMapper.selectPage(page, queryWrapper);
    }
}
