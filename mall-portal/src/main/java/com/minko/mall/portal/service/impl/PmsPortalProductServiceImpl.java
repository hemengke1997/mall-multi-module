package com.minko.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.mapper.*;
import com.minko.mall.model.*;
import com.minko.mall.portal.dao.PortalProductDao;
import com.minko.mall.portal.domain.PmsPortalProductDetail;
import com.minko.mall.portal.domain.PmsProductCategoryNode;
import com.minko.mall.portal.service.PmsPortalProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsPortalProductServiceImpl implements PmsPortalProductService {
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsProductLadderMapper productLadderMapper;
    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Autowired
    private PortalProductDao portalProductDao;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Override
    public PmsPortalProductDetail detail(Long id) {
        PmsPortalProductDetail result = new PmsPortalProductDetail();
        // 获取商品详情
        PmsProduct product = productMapper.selectById(id);
        result.setProduct(product);
        // 获取品牌信息
        result.setBrand(brandMapper.selectById(product.getBrandId()));
        // 获取商品属性信息
        LambdaQueryWrapper<PmsProductAttribute> productAttributeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId, product.getProductAttributeCategoryId());
        List<PmsProductAttribute> productAttributeList = productAttributeMapper.selectList(productAttributeLambdaQueryWrapper);
        result.setProductAttributeList(productAttributeList);
        //获取商品属性值信息
        if (CollUtil.isNotEmpty(productAttributeList)) {
            List<Long> attributeIds = productAttributeList.stream().map(PmsProductAttribute::getId).collect(Collectors.toList());
            LambdaQueryWrapper<PmsProductAttributeValue> productAttributeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            productAttributeValueLambdaQueryWrapper.eq(PmsProductAttributeValue::getProductId, product.getId())
                    .in(PmsProductAttributeValue::getProductAttributeId, attributeIds);
            List<PmsProductAttributeValue> productAttributeValueList = productAttributeValueMapper.selectList(productAttributeValueLambdaQueryWrapper);
            result.setProductAttributeValueList(productAttributeValueList);
        }
        //获取商品SKU库存信息
        LambdaQueryWrapper<PmsSkuStock> skuStockLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuStockLambdaQueryWrapper.eq(PmsSkuStock::getProductId, product.getId());
        List<PmsSkuStock> skuStockList = skuStockMapper.selectList(skuStockLambdaQueryWrapper);
        result.setSkuStockList(skuStockList);
        //商品阶梯价格设置
        if (product.getPromotionType() == 3) {
            LambdaQueryWrapper<PmsProductLadder> productLadderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            productLadderLambdaQueryWrapper.eq(PmsProductLadder::getProductId, product.getId());
            List<PmsProductLadder> productLadderList = productLadderMapper.selectList(productLadderLambdaQueryWrapper);
            result.setProductLadderList(productLadderList);
        }
        //商品满减价格设置
        if (product.getPromotionType() == 4) {
            LambdaQueryWrapper<PmsProductFullReduction> fullReductionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fullReductionLambdaQueryWrapper.eq(PmsProductFullReduction::getProductId, product.getId());
            List<PmsProductFullReduction> productFullReductionList = productFullReductionMapper.selectList(fullReductionLambdaQueryWrapper);
            result.setProductFullReductionList(productFullReductionList);
        }
        //商品可用优惠券
        result.setCouponList(portalProductDao.getAvailableCouponList(product.getId(), product.getProductCategoryId()));
        return result;
    }

    @Override
    public Page<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        Page<PmsProduct> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PmsProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.eq(PmsProduct::getDeleteStatus, 0)
                .eq(PmsProduct::getPublishStatus, 1);
        if (StrUtil.isNotEmpty(keyword)) {
            productLambdaQueryWrapper.like(PmsProduct::getName, keyword);
        }
        if (brandId != null) {
            productLambdaQueryWrapper.eq(PmsProduct::getBrandId, brandId);
        }
        if (productCategoryId != null) {
            productLambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, productCategoryId);
        }
        //1->按新品；2->按销量；3->价格从低到高；4->价格从高到低
        if (sort == 1) {
            productLambdaQueryWrapper.orderByDesc(PmsProduct::getId);
        } else if (sort == 2) {
            productLambdaQueryWrapper.orderByDesc(PmsProduct::getSale);
        } else if (sort == 3) {
            productLambdaQueryWrapper.orderByAsc(PmsProduct::getPrice);
        } else if (sort == 4) {
            productLambdaQueryWrapper.orderByDesc(PmsProduct::getPrice);
        }
        return productMapper.selectPage(page, productLambdaQueryWrapper);
    }

    @Override
    public List<PmsProductCategoryNode> categoryTreeList() {
        List<PmsProductCategory> productCategoryList = productCategoryMapper.selectList(null);
        List<PmsProductCategoryNode> result = productCategoryList.stream()
                .filter(item -> item.getParentId().equals(0L))
                .map(item -> convert(item, productCategoryList))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 初始对象转化为节点对象
     */
    private PmsProductCategoryNode convert(PmsProductCategory item, List<PmsProductCategory> allList) {
        PmsProductCategoryNode node = new PmsProductCategoryNode();
        BeanUtils.copyProperties(item, node);
        List<PmsProductCategoryNode> children = allList.stream()
                .filter(subItem -> subItem.getParentId().equals(item.getId()))
                .map(subItem -> convert(subItem, allList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
