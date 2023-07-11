package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsHomeRecommendProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeRecommendProductService extends IService<SmsHomeRecommendProduct> {
    @Transactional
    int create(List<SmsHomeRecommendProduct> homeRecommendProductList);

    int updateSort(Long id, Integer sort);

    @Transactional
    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Page<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Page<SmsHomeRecommendProduct> page);
}
