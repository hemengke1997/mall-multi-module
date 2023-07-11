package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.SmsFlashPromotionProduct;
import com.minko.mall.model.SmsFlashPromotionProductRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsFlashPromotionProductRelationService extends IService<SmsFlashPromotionProductRelation> {
    /**
     * 根据活动和场次id获取商品关系数量
     *
     * @param flashPromotionId        限时购id
     * @param flashPromotionSessionId 限时购场次id
     */
    long getCount(Long flashPromotionId, Long flashPromotionSessionId);

    Page<SmsFlashPromotionProduct> selectPage(Page<SmsFlashPromotionProduct> page, @Param("flashPromotionId") Long flashPromotionId, @Param("flashPromotionSessionId") Long flashPromotionSessionId);

    @Transactional
    int create(List<SmsFlashPromotionProductRelation> relationList);
}
