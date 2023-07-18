package com.minko.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.mapper.*;
import com.minko.mall.model.*;
import com.minko.mall.portal.dao.HomeDao;
import com.minko.mall.portal.domain.FlashPromotionProduct;
import com.minko.mall.portal.domain.HomeContentResult;
import com.minko.mall.portal.domain.HomeFlashPromotion;
import com.minko.mall.portal.service.HomeService;
import com.minko.mall.portal.util.MDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDao homeDao;
    @Autowired
    private SmsHomeAdvertiseMapper advertiseMapper;
    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;
    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Override
    public HomeContentResult content() {
        HomeContentResult result = new HomeContentResult();
        // 获取首页广告
        result.setAdvertiseList(getHomeAdvertiseList());
        // 获取推荐品牌
        result.setBrandList(homeDao.getRecommendBrandList(0, 6));
        // 获取秒杀信息
        result.setHomeFlashPromotion(getHomeFlashPromotion());
        // 获取新品推荐
        result.setNewProductList(homeDao.getNewProductList(0, 4));
        // 获取人气推荐
        result.setHotProductList(homeDao.getHotProductList(0, 4));
        // 获取推荐专题
        result.setSubjectList(homeDao.getRecommendSubjectList(0, 4));
        return result;
    }

    @Override
    public List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum) {
        Page<PmsProduct> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PmsProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsProduct::getDeleteStatus, 0).eq(PmsProduct::getPublishStatus, 1);
        return productMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public List<PmsProductCategory> getProductCateList(Long parentId) {
        LambdaQueryWrapper<PmsProductCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsProductCategory::getShowStatus, 1)
                .eq(PmsProductCategory::getParentId, parentId)
                .orderByDesc(PmsProductCategory::getSort);
        return productCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize) {
        int offset = pageSize * (pageNum - 1);
        return homeDao.getHotProductList(offset, pageSize);
    }

    @Override
    public List<PmsProduct> newProductList(Integer pageNum, Integer pageSize) {
        Integer offset = pageSize * (pageNum - 1);
        return homeDao.getNewProductList(offset, pageSize);
    }

    private List<SmsHomeAdvertise> getHomeAdvertiseList() {
        LambdaQueryWrapper<SmsHomeAdvertise> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SmsHomeAdvertise::getType, 1)
                .eq(SmsHomeAdvertise::getStatus, 1)
                .orderByDesc(SmsHomeAdvertise::getOrderCount);
        return advertiseMapper.selectList(queryWrapper);
    }

    private HomeFlashPromotion getHomeFlashPromotion() {
        HomeFlashPromotion homeFlashPromotion = new HomeFlashPromotion();
        // 获取当前秒杀活动
        Date now = new Date();
        SmsFlashPromotion flashPromotion = getFlashPromotion(now);
        if (flashPromotion != null) {
            // 获取当前秒杀场次
            SmsFlashPromotionSession flashPromotionSession = getFlashPromotionSession(now);
            if (flashPromotionSession != null) {
                Calendar t = Calendar.getInstance();
                Calendar start = Calendar.getInstance();
                start.setTime(flashPromotionSession.getStartTime());
                Calendar end = Calendar.getInstance();
                end.setTime(flashPromotionSession.getEndTime());

                start.set(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DATE));
                end.set(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DATE));

                homeFlashPromotion.setStartTime(start.getTime());
                homeFlashPromotion.setEndTime(end.getTime());
                // 获取下一个秒杀场次
                SmsFlashPromotionSession nextSession = getNextFlashPromotionSession(homeFlashPromotion.getStartTime());
                if (nextSession != null) {
                    homeFlashPromotion.setNextStartTime(nextSession.getStartTime());
                    homeFlashPromotion.setNextEndTime(nextSession.getEndTime());
                }
                // 获取秒杀商品
                List<FlashPromotionProduct> flashProductList = homeDao.getFlashProductList(flashPromotion.getId(), flashPromotionSession.getId());
                homeFlashPromotion.setProductList(flashProductList);
            }
        }
        return homeFlashPromotion;
    }

    private SmsFlashPromotionSession getNextFlashPromotionSession(Date date) {
        LambdaQueryWrapper<SmsFlashPromotionSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(SmsFlashPromotionSession::getStartTime, date).orderByAsc(SmsFlashPromotionSession::getStartTime);


        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    /**
     * 根据时间获取秒杀场次
     */
    private SmsFlashPromotionSession getFlashPromotionSession(Date date) {
        Date currTime = MDateUtil.getTime(date);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String s = format.format(currTime);
        LambdaQueryWrapper<SmsFlashPromotionSession> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(SmsFlashPromotionSession::getStartTime, s)
                .ge(SmsFlashPromotionSession::getEndTime, s);
        List<SmsFlashPromotionSession> promotionSessionList = promotionSessionMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(promotionSessionList)) {
            return promotionSessionList.get(0);
        }
        return null;
    }

    /**
     * 根据时间获取秒杀信息
     */
    private SmsFlashPromotion getFlashPromotion(Date date) {
        Date currDate = MDateUtil.getDate(date);
        LambdaQueryWrapper<SmsFlashPromotion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .lt(SmsFlashPromotion::getStartDate, currDate)
                .gt(SmsFlashPromotion::getEndDate, currDate)
                .eq(SmsFlashPromotion::getStatus, 1);
        List<SmsFlashPromotion> flashPromotionList = flashPromotionMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(flashPromotionList)) {
            return flashPromotionList.get(0);
        }
        return null;
    }
}
