package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsHomeBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeBrandService extends IService<SmsHomeBrand> {
    Page<SmsHomeBrand> selectPage(Page<SmsHomeBrand> page, String brandName, Integer recommendStatus);

    @Transactional
    int create(List<SmsHomeBrand> brandList);

    @Transactional
    int delete(List<Long> ids);

    @Transactional
    int updateStatus(List<Long> ids, Integer status);
}
