package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsHomeBrandMapper;
import com.minko.mall.model.SmsHomeBrand;
import com.minko.mall.service.SmsHomeBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeBrandServiceImpl extends ServiceImpl<SmsHomeBrandMapper, SmsHomeBrand> implements SmsHomeBrandService {
    @Autowired
    private SmsHomeBrandMapper brandMapper;


    @Override
    public Page<SmsHomeBrand> selectPage(Page<SmsHomeBrand> page, String brandName, Integer recommendStatus) {
        LambdaQueryWrapper<SmsHomeBrand> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(brandName)) {
            queryWrapper.like(SmsHomeBrand::getBrandName, brandName);
        }
        if (recommendStatus != null) {
            queryWrapper.eq(SmsHomeBrand::getRecommendStatus, recommendStatus);
        }
        return brandMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int create(List<SmsHomeBrand> brandList) {
        for (SmsHomeBrand brand : brandList) {
            brandMapper.insert(brand);
        }

        return brandList.size();
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<SmsHomeBrand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeBrand::getId, ids);
        int i = brandMapper.delete(queryWrapper);
        return i;
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        SmsHomeBrand brand = new SmsHomeBrand();
        brand.setRecommendStatus(status);
        LambdaQueryWrapper<SmsHomeBrand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeBrand::getId, ids);
        return brandMapper.update(brand, queryWrapper);
    }
}
