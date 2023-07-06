package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.UmsResourceMapper;
import com.minko.mall.model.UmsResource;
import com.minko.mall.service.UmsAdminCacheService;
import com.minko.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
    @Autowired
    UmsResourceMapper umsResourceMapper;

    @Autowired
    UmsAdminCacheService umsAdminCacheService;

    @Override
    public Page<UmsResource> page(Page<UmsResource> page, Long categoryId, String nameKeyword, String urlKeyword) {
        LambdaQueryWrapper<UmsResource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            lambdaQueryWrapper.eq(UmsResource::getCategoryId, categoryId);
        }
        if (!StrUtil.isEmpty(nameKeyword)) {
            lambdaQueryWrapper.and(i -> i.like(UmsResource::getName, nameKeyword));
        }
        if (!StrUtil.isEmpty(urlKeyword)) {
            lambdaQueryWrapper.and(i -> i.like(UmsResource::getUrl, urlKeyword));
        }

        Page<UmsResource> umsResourcePage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return umsResourcePage;
    }

    @Override
    public int update(UmsResource umsResource) {
        int i = umsResourceMapper.updateById(umsResource);
        umsAdminCacheService.delResourceListByResource(umsResource.getId());
        return i;
    }

    @Override
    public int remove(Long id) {
        int i = umsResourceMapper.deleteById(id);
        umsAdminCacheService.delResourceListByResource(id);
        return i;
    }
}
