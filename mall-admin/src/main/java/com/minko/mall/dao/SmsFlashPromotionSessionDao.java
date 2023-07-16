package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.SmsFlashPromotionSession;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsFlashPromotionSessionDao extends BaseMapper<SmsFlashPromotionSession> {
    boolean insertSession(SmsFlashPromotionSession session);
}
