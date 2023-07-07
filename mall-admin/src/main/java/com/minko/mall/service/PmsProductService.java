package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsProductParam;
import com.minko.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface PmsProductService extends IService<PmsProduct> {
    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);
}
