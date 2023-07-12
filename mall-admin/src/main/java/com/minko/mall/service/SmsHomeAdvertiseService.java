package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsHomeAdvertise;

import java.util.List;

public interface SmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {
    int create(SmsHomeAdvertise advertise);

    int delete(List<Long> ids);

    int updateStatus(Long id, Integer status);

    SmsHomeAdvertise getItem(Long id);

    int update(Long id, SmsHomeAdvertise advertise);

    Page<SmsHomeAdvertise> list(String name, Integer type, String endTime, Page<SmsHomeAdvertise> page);
}
