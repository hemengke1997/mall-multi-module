package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductAttributeParam;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.mapper.PmsProductAttributeCategoryMapper;
import com.minko.mall.mapper.PmsProductAttributeMapper;
import com.minko.mall.model.PmsProductAttribute;
import com.minko.mall.model.PmsProductAttributeCategory;
import com.minko.mall.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;

    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return pmsProductAttributeMapper.getProductAttrInfo(productCategoryId);
    }

    @Override
    public Page<PmsProductAttribute> selectPage(Long cid, Integer type, Page<PmsProductAttribute> page) {
        LambdaQueryWrapper<PmsProductAttribute> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId, cid);
        lambdaQueryWrapper.eq(PmsProductAttribute::getType, type);
        return pmsProductAttributeMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public int updateItem(Long id, PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        pmsProductAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        return pmsProductAttributeMapper.updateById(pmsProductAttribute);
    }

    @Override
    public int create(PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);

        int i = pmsProductAttributeMapper.insert(pmsProductAttribute);
        // 新增商品属性以后需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectById(pmsProductAttribute.getProductAttributeCategoryId());
        if (pmsProductAttribute.getType() == 0) {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        } else if (pmsProductAttribute.getType() == 1) {
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        }

        pmsProductAttributeCategoryMapper.updateById(pmsProductAttributeCategory);

        return i;
    }

    @Override
    public int delete(List<Long> ids) {
        PmsProductAttribute pmsProductAttribute = pmsProductAttributeMapper.selectById(ids.get(0));
        Integer type = pmsProductAttribute.getType();
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectById(pmsProductAttribute.getProductAttributeCategoryId());
        LambdaQueryWrapper<PmsProductAttribute> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProductAttribute::getId, ids);
        int count = pmsProductAttributeMapper.delete(lambdaQueryWrapper);

        if (type == 0) {
            if (pmsProductAttributeCategory.getAttributeCount() >= count) {
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() - count);
            } else {
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        } else if (type == 1) {
            if (pmsProductAttributeCategory.getParamCount() >= count) {
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() - count);
            } else {
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        pmsProductAttributeCategoryMapper.updateById(pmsProductAttributeCategory);

        return count;
    }
}
