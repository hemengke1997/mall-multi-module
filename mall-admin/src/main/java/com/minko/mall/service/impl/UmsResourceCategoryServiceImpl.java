package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.UmsResourceCategoryMapper;
import com.minko.mall.model.UmsResourceCategory;
import com.minko.mall.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper, UmsResourceCategory> implements UmsResourceCategoryService {
    @Autowired
    private UmsResourceCategoryMapper umsResourceCategoryMapper;

    @Override
    public List<UmsResourceCategory> orderList() {
        LambdaQueryWrapper<UmsResourceCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(UmsResourceCategory::getSort);
        List<UmsResourceCategory> umsResourceCategories = umsResourceCategoryMapper.selectList(lambdaQueryWrapper);
        return umsResourceCategories;
    }
}
