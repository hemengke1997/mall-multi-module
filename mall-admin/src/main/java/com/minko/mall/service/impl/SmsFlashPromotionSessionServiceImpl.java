package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.SmsFlashPromotionSessionDetail;
import com.minko.mall.mapper.SmsFlashPromotionSessionMapper;
import com.minko.mall.model.SmsFlashPromotionSession;
import com.minko.mall.service.SmsFlashPromotionProductRelationService;
import com.minko.mall.service.SmsFlashPromotionSessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsFlashPromotionSessionServiceImpl extends ServiceImpl<SmsFlashPromotionSessionMapper, SmsFlashPromotionSession> implements SmsFlashPromotionSessionService {
    @Autowired
    private SmsFlashPromotionSessionMapper flashPromotionSessionMapper;

    @Autowired
    private SmsFlashPromotionProductRelationService relationService;

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
        LambdaQueryWrapper<SmsFlashPromotionSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsFlashPromotionSession::getStatus, 1);
        List<SmsFlashPromotionSession> list = flashPromotionSessionMapper.selectList(queryWrapper);

        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();

        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            long count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        LambdaQueryWrapper<SmsFlashPromotionSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsFlashPromotionSession::getId, id);
        SmsFlashPromotionSession session = new SmsFlashPromotionSession();
        session.setStatus(status);
        int update = flashPromotionSessionMapper.update(session, queryWrapper);
        return update;
    }

    @Override
    public boolean insert(SmsFlashPromotionSession session) {
        return flashPromotionSessionMapper.insertSession(session);
    }
}
