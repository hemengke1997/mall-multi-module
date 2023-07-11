package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsHomeNewProductMapper;
import com.minko.mall.model.SmsHomeNewProduct;
import com.minko.mall.service.SmsHomeNewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeNewProductServiceImpl extends ServiceImpl<SmsHomeNewProductMapper, SmsHomeNewProduct> implements SmsHomeNewProductService {
    @Autowired
    private SmsHomeNewProductMapper productMapper;

    @Override
    public int create(List<SmsHomeNewProduct> homeNewProductList) {
        for (SmsHomeNewProduct product : homeNewProductList) {
            product.setSort(0);
            product.setRecommendStatus(1);
            productMapper.insert(product);
        }
        return homeNewProductList.size();
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeNewProduct smsHomeNewProduct = new SmsHomeNewProduct();
        smsHomeNewProduct.setId(id);
        smsHomeNewProduct.setSort(sort);
        return productMapper.updateById(smsHomeNewProduct);
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<SmsHomeNewProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeNewProduct::getId, ids);
        return productMapper.delete(queryWrapper);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<SmsHomeNewProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeNewProduct::getId, ids);
        SmsHomeNewProduct product = new SmsHomeNewProduct();
        product.setRecommendStatus(recommendStatus);
        return productMapper.update(product, queryWrapper);
    }

    @Override
    public Page<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Page<SmsHomeNewProduct> page) {
        LambdaQueryWrapper<SmsHomeNewProduct> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(productName)) {
            queryWrapper.like(SmsHomeNewProduct::getProductName, productName);
        }
        if (recommendStatus != null) {
            queryWrapper.eq(SmsHomeNewProduct::getRecommendStatus, recommendStatus);
        }
        return productMapper.selectPage(page, queryWrapper);
    }
}
