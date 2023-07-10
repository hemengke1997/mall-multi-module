package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.PmsSkuStock;

import java.util.List;

public interface PmsSkuStockService extends IService<PmsSkuStock> {
    List<PmsSkuStock> getList(Long pid, String keyword);

    int update(Long pid, List<PmsSkuStock> skuStockList);
}
