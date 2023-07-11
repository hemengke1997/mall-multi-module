package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.SmsCouponParam;
import com.minko.mall.mapper.SmsCouponMapper;
import com.minko.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.minko.mall.mapper.SmsCouponProductRelationMapper;
import com.minko.mall.model.SmsCoupon;
import com.minko.mall.model.SmsCouponProductCategoryRelation;
import com.minko.mall.model.SmsCouponProductRelation;
import com.minko.mall.service.SmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements SmsCouponService {
    @Autowired
    private SmsCouponMapper couponMapper;

    @Autowired
    private SmsCouponProductRelationMapper couponProductRelationMapper;

    @Autowired
    private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;

    @Override
    public Page<SmsCoupon> selectList(Page<SmsCoupon> page, String name, Integer type) {
        LambdaQueryWrapper<SmsCoupon> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(name)) {
            queryWrapper.like(SmsCoupon::getName, name);
        }
        if (type != null) {
            queryWrapper.eq(SmsCoupon::getType, type);
        }
        return couponMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int create(SmsCouponParam couponParam) {
        couponParam.setCount(couponParam.getPublishCount());
        couponParam.setUseCount(0);
        couponParam.setReceiveCount(0);
        // 插入优惠券表
        int count = couponMapper.insert(couponParam);
        // 插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
                couponProductRelationMapper.insert(productRelation);
            }
        }
        // 插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation categoryRelation : couponParam.getProductCategoryRelationList()) {
                categoryRelation.setCouponId(couponParam.getId());
                couponProductCategoryRelationMapper.insert(categoryRelation);
            }
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        // 删除优惠券
        int i = couponMapper.deleteById(id);
        // 删除商品关联
        deleteProductRelation(id);
        // 删除商品分类关联
        deleteProductCategoryRelation(id);
        return i;
    }

    @Override
    public int update(Long id, SmsCouponParam couponParam) {
        couponParam.setId(id);
        int count = couponMapper.updateById(couponParam);
        // 删除后插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
                couponProductRelationMapper.insert(productRelation);

            }
            deleteProductRelation(id);
        }
        // 删除后插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
                couponProductCategoryRelationMapper.insert(couponProductCategoryRelation);
            }
            deleteProductCategoryRelation(id);
        }
        return count;
    }

    private void deleteProductRelation(Long couponId) {
        LambdaQueryWrapper<SmsCouponProductRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsCouponProductRelation::getCouponId, couponId);
        couponProductRelationMapper.delete(queryWrapper);
    }

    private void deleteProductCategoryRelation(Long couponId) {
        LambdaQueryWrapper<SmsCouponProductCategoryRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsCouponProductCategoryRelation::getCouponId, couponId);
        couponProductCategoryRelationMapper.delete(queryWrapper);
    }
}
