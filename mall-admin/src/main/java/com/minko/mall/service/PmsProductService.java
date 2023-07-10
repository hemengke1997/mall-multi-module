package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsProductParam;
import com.minko.mall.dto.PmsProductQueryParam;
import com.minko.mall.dto.PmsProductResult;
import com.minko.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductService extends IService<PmsProduct> {
    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    Page<PmsProduct> selectPage(Page<PmsProduct> pmsProductPage, PmsProductQueryParam pmsProductQueryParam);

    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int updateNewStatus(List<Long> ids, Integer newStatus);

    int deleteStatus(List<Long> ids, Integer deleteStatus);

    PmsProductResult getUpdateInfo(Long id);
}
