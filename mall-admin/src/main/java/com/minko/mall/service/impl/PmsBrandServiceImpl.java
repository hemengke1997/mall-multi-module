package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dao.PmsProductDao;
import com.minko.mall.dto.PmsBrandParam;
import com.minko.mall.mapper.PmsBrandMapper;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.service.PmsBrandSerive;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandSerive {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Autowired
    private PmsProductDao pmsProductDao;

    @Override
    public int create(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        // 如果创建时首字母为空，取名称的第一个为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        return pmsBrandMapper.insert(pmsBrand);
    }

    @Override
    public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        pmsBrand.setId(id);
        // 如果创建时首字母为空，取名称的第一个为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        // 更新品牌时要更新商品中的品牌名称
        PmsProduct product = new PmsProduct();
        product.setBrandName(pmsBrand.getName());
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PmsProduct::getBrandId, id);
        pmsProductDao.update(product, lambdaQueryWrapper);
        return pmsBrandMapper.updateById(pmsBrand);
    }

    @Override
    public Page<PmsBrand> selectPage(Page<PmsBrand> pmsBrandPage, String keyword, Integer showStatus) {
        LambdaQueryWrapper<PmsBrand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(PmsBrand::getSort);
        if (StrUtil.isNotEmpty(keyword)) {
            lambdaQueryWrapper.like(PmsBrand::getName, keyword);
        }
        if (showStatus != null) {
            lambdaQueryWrapper.eq(PmsBrand::getShowStatus, showStatus);
        }
        Page<PmsBrand> page = pmsBrandMapper.selectPage(pmsBrandPage, lambdaQueryWrapper);
        return page;
    }
}
