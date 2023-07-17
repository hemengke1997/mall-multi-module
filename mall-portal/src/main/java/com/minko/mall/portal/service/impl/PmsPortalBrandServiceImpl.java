package com.minko.mall.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.PmsBrandMapper;
import com.minko.mall.mapper.PmsProductMapper;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.portal.dao.HomeDao;
import com.minko.mall.portal.service.PmsPortalBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsPortalBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsPortalBrandService {
    @Autowired
    private HomeDao homeDao;

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        Integer offset = pageSize * (pageNum - 1);
        return homeDao.getRecommendBrandList(offset, pageSize);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectById(brandId);
    }

    @Override
    public Page<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        Page<PmsProduct> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PmsProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsProduct::getBrandId, brandId);
        return productMapper.selectPage(page, queryWrapper);
    }
}
