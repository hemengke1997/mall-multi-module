package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.model.PmsSkuStock;
import com.minko.mall.dao.PmsSkuStockDao;
import com.minko.mall.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockDao, PmsSkuStock> implements PmsSkuStockService {
    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        LambdaQueryWrapper<PmsSkuStock> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PmsSkuStock::getProductId, pid);
        if (StrUtil.isNotEmpty(keyword)) {
            lambdaQueryWrapper.like(PmsSkuStock::getSkuCode, keyword);
        }
        return pmsSkuStockDao.selectList(lambdaQueryWrapper);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return pmsSkuStockDao.replaceList(skuStockList);
    }
}
