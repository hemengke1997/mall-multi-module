package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductCategoryParam;
import com.minko.mall.mapper.PmsProductCategoryMapper;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {
    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setProductCount(0);

        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        setCategoryLevel(pmsProductCategory);
        int inserted = pmsProductCategoryMapper.insert(pmsProductCategory);
        // todo 创建筛选属性关联

        return inserted;
    }

    @Override
    public Page<PmsProductCategory> selectPage(Page<PmsProductCategory> page, Long parentId) {
        LambdaQueryWrapper<PmsProductCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(PmsProductCategory::getParentId, parentId)
                .orderByDesc(PmsProductCategory::getSort);
        Page<PmsProductCategory> productCategoryPage = pmsProductCategoryMapper.selectPage(page, lambdaQueryWrapper);
        return productCategoryPage;
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        LambdaQueryWrapper<PmsProductCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProductCategory::getId, ids);
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setNavStatus(navStatus);
        int i = pmsProductCategoryMapper.update(pmsProductCategory, lambdaQueryWrapper);
        return i;
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        LambdaQueryWrapper<PmsProductCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProductCategory::getId, ids);
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setShowStatus(showStatus);
        int i = pmsProductCategoryMapper.update(pmsProductCategory, lambdaQueryWrapper);
        return i;
    }

    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PmsProductCategory pmsProductCategory) {
        // 没有父分类时为第一级分类
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            // 有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = pmsProductCategoryMapper.selectById(pmsProductCategory.getParentId());
            if (parentCategory != null) {
                pmsProductCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                pmsProductCategory.setLevel(0);
            }
        }
    }
}
