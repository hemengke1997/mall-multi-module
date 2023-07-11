package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsFlashPromotionMapper;
import com.minko.mall.model.SmsFlashPromotion;
import com.minko.mall.service.SmsFlashPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsFlashPromotionServiceImpl extends ServiceImpl<SmsFlashPromotionMapper, SmsFlashPromotion> implements SmsFlashPromotionService {
    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;

    @Override
    public Page<SmsFlashPromotion> selectPage(Page<SmsFlashPromotion> page, String keyword) {
        LambdaQueryWrapper<SmsFlashPromotion> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.like(SmsFlashPromotion::getTitle, keyword);
        }
        return flashPromotionMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        LambdaQueryWrapper<SmsFlashPromotion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsFlashPromotion::getId, id);
        SmsFlashPromotion promotion = new SmsFlashPromotion();
        promotion.setStatus(status);
        return flashPromotionMapper.update(promotion, queryWrapper);
    }
}
