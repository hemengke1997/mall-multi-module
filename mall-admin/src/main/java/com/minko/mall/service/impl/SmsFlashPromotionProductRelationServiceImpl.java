package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.SmsFlashPromotionProduct;
import com.minko.mall.mapper.SmsFlashPromotionProductRelationMapper;
import com.minko.mall.model.SmsFlashPromotionProductRelation;
import com.minko.mall.service.SmsFlashPromotionProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsFlashPromotionProductRelationServiceImpl extends ServiceImpl<SmsFlashPromotionProductRelationMapper, SmsFlashPromotionProductRelation> implements SmsFlashPromotionProductRelationService {
    @Autowired
    private SmsFlashPromotionProductRelationMapper flashPromotionProductRelationMapper;

    @Override
    public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
        LambdaQueryWrapper<SmsFlashPromotionProductRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsFlashPromotionProductRelation::getFlashPromotionId, flashPromotionId);
        queryWrapper.eq(SmsFlashPromotionProductRelation::getFlashPromotionSessionId, flashPromotionSessionId);
        return flashPromotionProductRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public Page<SmsFlashPromotionProduct> selectPage(Page<SmsFlashPromotionProduct> page, Long flashPromotionId, Long flashPromotionSessionId) {
        return flashPromotionProductRelationMapper.getList(page, flashPromotionId, flashPromotionSessionId);
    }

    @Override
    public int create(List<SmsFlashPromotionProductRelation> relationList) {
        for (SmsFlashPromotionProductRelation relation : relationList) {
            flashPromotionProductRelationMapper.insert(relation);
        }
        return relationList.size();
    }
}
