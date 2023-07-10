package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductCategoryParam;
import com.minko.mall.dto.PmsProductCategoryWithChildrenItem;
import com.minko.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.minko.mall.mapper.PmsProductCategoryMapper;
import com.minko.mall.mapper.PmsProductMapper;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.model.PmsProductCategoryAttributeRelation;
import com.minko.mall.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {
    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper pmsProductCategoryAttributeRelationMapper;

    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setProductCount(0);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        setCategoryLevel(pmsProductCategory);
        int inserted = pmsProductCategoryMapper.insert(pmsProductCategory);
        // 创建筛选属性关联
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            insertRelationList(pmsProductCategory.getId(), productAttributeIdList);
        }
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

    @Override
    public int updateItem(Long id, PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setId(id);
        BeanUtils.copyProperties(pmsProductCategoryParam, productCategory);
        setCategoryLevel(productCategory);
        // 更新商品分类时要更新商品中的名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(productCategory.getName());
        LambdaQueryWrapper<PmsProduct> pmsProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductLambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, id);
        pmsProductMapper.updateById(product);

        // 同时更新筛选属性的信息
        LambdaQueryWrapper<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductCategoryAttributeRelationLambdaQueryWrapper.eq(PmsProductCategoryAttributeRelation::getProductCategoryId, id);
        pmsProductCategoryAttributeRelationMapper.delete(pmsProductCategoryAttributeRelationLambdaQueryWrapper);

        if (!CollectionUtils.isEmpty(pmsProductCategoryParam.getProductAttributeIdList())) {
            insertRelationList(id, pmsProductCategoryParam.getProductAttributeIdList());
        }

        int i = pmsProductCategoryMapper.updateById(productCategory);
        return i;
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return pmsProductCategoryMapper.listWithChildren();
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

    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param productCategoryId      商品分类id
     * @param productAttributeIdList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(productCategoryId);
            relationList.add(relation);
        }
        for (PmsProductCategoryAttributeRelation relation : relationList) {
            pmsProductCategoryAttributeRelationMapper.insert(relation);
        }
    }
}
