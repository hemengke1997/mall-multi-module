package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.OmsOrderReturnReasonMapper;
import com.minko.mall.model.OmsOrderReturnReason;
import com.minko.mall.service.OmsOrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmsOrderReturnReasonServiceImpl extends ServiceImpl<OmsOrderReturnReasonMapper, OmsOrderReturnReason> implements OmsOrderReturnReasonService {
    @Autowired
    OmsOrderReturnReasonMapper returnReasonMapper;

    @Override
    public Page<OmsOrderReturnReason> selectPage(Page<OmsOrderReturnReason> page) {
        return returnReasonMapper.selectPage(page, null);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        LambdaQueryWrapper<OmsOrderReturnReason> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OmsOrderReturnReason::getId, ids);
        OmsOrderReturnReason reason = new OmsOrderReturnReason();
        reason.setStatus(status);
        int update = returnReasonMapper.update(reason, queryWrapper);
        return update;
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<OmsOrderReturnReason> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OmsOrderReturnReason::getId, ids);
        return returnReasonMapper.delete(wrapper);
    }
}
