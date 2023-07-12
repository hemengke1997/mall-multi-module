package com.minko.mall.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsHomeAdvertiseMapper;
import com.minko.mall.model.SmsHomeAdvertise;
import com.minko.mall.service.SmsHomeAdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmsHomeAdvertiseServiceImpl extends ServiceImpl<SmsHomeAdvertiseMapper, SmsHomeAdvertise> implements SmsHomeAdvertiseService {
    @Autowired
    private SmsHomeAdvertiseMapper advertiseMapper;

    @Override
    public int create(SmsHomeAdvertise advertise) {
        advertise.setClickCount(0);
        advertise.setOrderCount(0);
        return advertiseMapper.insert(advertise);
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<SmsHomeAdvertise> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SmsHomeAdvertise::getId, ids);
        return advertiseMapper.delete(wrapper);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsHomeAdvertise advertise = new SmsHomeAdvertise();
        advertise.setStatus(status);
        advertise.setId(id);
        return advertiseMapper.updateById(advertise);
    }

    @Override
    public SmsHomeAdvertise getItem(Long id) {
        return advertiseMapper.selectById(id);
    }

    @Override
    public int update(Long id, SmsHomeAdvertise advertise) {
        advertise.setId(id);
        return advertiseMapper.updateById(advertise);
    }

    @Override
    public Page<SmsHomeAdvertise> list(String name, Integer type, String endTime, Page<SmsHomeAdvertise> page) {
        LambdaQueryWrapper<SmsHomeAdvertise> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(name)) {
            queryWrapper.like(SmsHomeAdvertise::getName, name);
        }
        if (type != null) {
            queryWrapper.eq(SmsHomeAdvertise::getType, type);
        }
        if (StrUtil.isNotEmpty(endTime)) {
            Date date = DateUtil.parse(endTime);
            Date endOfDay = DateUtil.endOfDay(date);
            Date startOfDay = DateUtil.beginOfDay(date);
            queryWrapper.gt(SmsHomeAdvertise::getEndTime, startOfDay).le(SmsHomeAdvertise::getEndTime, endOfDay);
        }

        return advertiseMapper.selectPage(page, queryWrapper);
    }
}
