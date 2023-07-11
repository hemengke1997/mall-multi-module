package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.SmsFlashPromotionSessionDetail;
import com.minko.mall.model.SmsFlashPromotionSession;

import java.util.List;

public interface SmsFlashPromotionSessionService extends IService<SmsFlashPromotionSession> {
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);

    int updateStatus(Long id, Integer status);

    boolean insert(SmsFlashPromotionSession session);
}
