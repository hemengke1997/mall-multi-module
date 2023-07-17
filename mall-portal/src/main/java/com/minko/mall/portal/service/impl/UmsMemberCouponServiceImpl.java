package com.minko.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minko.mall.mapper.PmsProductMapper;
import com.minko.mall.mapper.SmsCouponMapper;
import com.minko.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.minko.mall.mapper.SmsCouponProductRelationMapper;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.SmsCoupon;
import com.minko.mall.model.SmsCouponProductCategoryRelation;
import com.minko.mall.model.SmsCouponProductRelation;
import com.minko.mall.portal.service.UmsMemberCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {
    @Autowired
    private SmsCouponProductRelationMapper couponProductRelationMapper;
    @Autowired
    private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private SmsCouponMapper couponMapper;

    @Override
    public List<SmsCoupon> listByProduct(Long productId) {
        List<Long> allCouponIds = new ArrayList<>();
        // 获取指定商品优惠券
        LambdaQueryWrapper<SmsCouponProductRelation> couponProductRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        couponProductRelationLambdaQueryWrapper.eq(SmsCouponProductRelation::getProductId, productId);
        List<SmsCouponProductRelation> cprList = couponProductRelationMapper.selectList(couponProductRelationLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(cprList)) {
            List<Long> couponIds = cprList.stream().map(SmsCouponProductRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //获取指定分类优惠券
        PmsProduct product = productMapper.selectById(productId);
        LambdaQueryWrapper<SmsCouponProductCategoryRelation> productCategoryRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productCategoryRelationLambdaQueryWrapper.eq(SmsCouponProductCategoryRelation::getProductCategoryId, product.getProductCategoryId());
        List<SmsCouponProductCategoryRelation> cpcrList = couponProductCategoryRelationMapper.selectList(productCategoryRelationLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(cpcrList)) {
            List<Long> couponIds = cpcrList.stream().map(SmsCouponProductCategoryRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //所有优惠券
        LambdaQueryWrapper<SmsCoupon> couponLambdaQueryWrapper = new LambdaQueryWrapper<>();
        couponLambdaQueryWrapper.gt(SmsCoupon::getEnableTime, new Date())
                .lt(SmsCoupon::getStartTime, new Date())
                .eq(SmsCoupon::getUseType, 0);

        if (CollUtil.isNotEmpty(allCouponIds)) {

            couponLambdaQueryWrapper.or()
                    .gt(SmsCoupon::getEndTime, new Date())
                    .lt(SmsCoupon::getStartTime, new Date())
                    .eq(SmsCoupon::getUseType, 0)
                    .in(SmsCoupon::getId, allCouponIds);
        }
        
        return couponMapper.selectList(couponLambdaQueryWrapper);
    }
}
