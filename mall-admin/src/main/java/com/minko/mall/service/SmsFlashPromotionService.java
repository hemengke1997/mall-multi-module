package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsFlashPromotion;

public interface SmsFlashPromotionService extends IService<SmsFlashPromotion> {
    Page<SmsFlashPromotion> selectPage(Page<SmsFlashPromotion> page, String keyword);

    int updateStatus(Long id, Integer status);
}
