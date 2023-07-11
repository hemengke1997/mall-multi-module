package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsHomeNewProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeNewProductService extends IService<SmsHomeNewProduct> {
    @Transactional
    int create(List<SmsHomeNewProduct> homeNewProductList);

    int updateSort(Long id, Integer sort);

    @Transactional
    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Page<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Page<SmsHomeNewProduct> page);
}
