package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.dto.SmsFlashPromotionProduct;
import com.minko.mall.model.SmsFlashPromotionProductRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsFlashPromotionProductRelationMapper extends BaseMapper<SmsFlashPromotionProductRelation> {
    Page<SmsFlashPromotionProduct> getList(Page<SmsFlashPromotionProduct> page, @Param("flashPromotionId") Long flashPromotionId, Long flashPromotionSessionId);
}
