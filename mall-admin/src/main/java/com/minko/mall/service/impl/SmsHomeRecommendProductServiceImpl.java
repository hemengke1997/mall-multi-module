package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsHomeRecommendProductMapper;
import com.minko.mall.model.SmsHomeRecommendProduct;
import com.minko.mall.service.SmsHomeRecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeRecommendProductServiceImpl extends ServiceImpl<SmsHomeRecommendProductMapper, SmsHomeRecommendProduct> implements SmsHomeRecommendProductService {
    @Autowired
    private SmsHomeRecommendProductMapper recommendProductMapper;

    @Override
    public int create(List<SmsHomeRecommendProduct> homeRecommendProductList) {
        for (SmsHomeRecommendProduct product : homeRecommendProductList) {
            product.setRecommendStatus(1);
            product.setSort(0);
            recommendProductMapper.insert(product);
        }
        return homeRecommendProductList.size();
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendProduct recommendProduct = new SmsHomeRecommendProduct();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        return recommendProductMapper.updateById(recommendProduct);
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<SmsHomeRecommendProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeRecommendProduct::getId, ids);
        return recommendProductMapper.delete(queryWrapper);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<SmsHomeRecommendProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeRecommendProduct::getId, ids);
        SmsHomeRecommendProduct record = new SmsHomeRecommendProduct();
        record.setRecommendStatus(recommendStatus);
        return recommendProductMapper.update(record, queryWrapper);
    }

    @Override
    public Page<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Page<SmsHomeRecommendProduct> page) {
        LambdaQueryWrapper<SmsHomeRecommendProduct> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotEmpty(productName)) {
            queryWrapper.like(SmsHomeRecommendProduct::getProductName, productName);
        }
        if (recommendStatus != null) {
            queryWrapper.eq(SmsHomeRecommendProduct::getRecommendStatus, recommendStatus);
        }
        return recommendProductMapper.selectPage(page, queryWrapper);
    }
}
