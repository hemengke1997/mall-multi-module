package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.PmsSkuStockMapper;
import com.minko.mall.model.PmsSkuStock;
import com.minko.mall.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        LambdaQueryWrapper<PmsSkuStock> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PmsSkuStock::getProductId, pid);
        if (StrUtil.isNotEmpty(keyword)) {
            lambdaQueryWrapper.like(PmsSkuStock::getSkuCode, keyword);
        }
        return pmsSkuStockMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return pmsSkuStockMapper.replaceList(skuStockList);
    }
}
