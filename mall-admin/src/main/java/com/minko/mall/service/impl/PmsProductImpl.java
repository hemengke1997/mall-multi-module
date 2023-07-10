package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductParam;
import com.minko.mall.dto.PmsProductQueryParam;
import com.minko.mall.dto.PmsProductResult;
import com.minko.mall.mapper.*;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.PmsSkuStock;
import com.minko.mall.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PmsProductImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsMemberPriceMapper pmsMemberPriceMapper;
    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;
    @Autowired
    private CmsSubjectProductRelationMapper cmsSubjectProductRelationMapper;
    @Autowired
    private CmsPrefrenceAreaProductRelationMapper cmsPrefrenceAreaProductRelationMapper;

    @Override
    public int create(PmsProductParam productParam) {
        int count;
        // 创建商品
        PmsProduct product = productParam;
        product.setId(null);
        pmsProductMapper.insert(product);
        // 根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = product.getId();

        // 会员价格
        relateAndInsertList(pmsMemberPriceMapper, productParam.getMemberPriceList(), productId);
        // 阶梯价格
        relateAndInsertList(pmsProductLadderMapper, productParam.getProductLadderList(), productId);
        // todo 满减价格
        // relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
        // 处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), productId);
        // 添加sku库存信息
        relateAndInsertList(pmsSkuStockMapper, productParam.getSkuStockList(), productId);
        // todo 添加商品参数,添加自定义商品规格
        // relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        // 关联专题
        relateAndInsertList(cmsSubjectProductRelationMapper, productParam.getSubjectProductRelationList(), productId);
        // 关联优选
        relateAndInsertList(cmsPrefrenceAreaProductRelationMapper, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    @Override
    public Page<PmsProduct> selectPage(Page<PmsProduct> pmsProductPage, PmsProductQueryParam pmsProductQueryParam) {
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PmsProduct::getDeleteStatus, 0);

        if (pmsProductQueryParam.getPublishStatus() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getPublishStatus, pmsProductQueryParam.getPublishStatus());
        }

        if (pmsProductQueryParam.getVerifyStatus() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getVerifyStatus, pmsProductQueryParam.getVerifyStatus());
        }

        if (StrUtil.isNotEmpty(pmsProductQueryParam.getKeyword())) {
            lambdaQueryWrapper.like(PmsProduct::getName, pmsProductQueryParam.getKeyword());
        }

        if (StrUtil.isNotEmpty(pmsProductQueryParam.getProductSn())) {
            lambdaQueryWrapper.eq(PmsProduct::getProductSn, pmsProductQueryParam.getProductSn());
        }
        if (pmsProductQueryParam.getBrandId() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getBrandId, pmsProductQueryParam.getBrandId());
        }

        if (pmsProductQueryParam.getProductCategoryId() != null) {
            lambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, pmsProductQueryParam.getProductCategoryId());
        }
        return pmsProductMapper.selectPage(pmsProductPage, lambdaQueryWrapper);
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProduct::getId, ids);
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setPublishStatus(publishStatus);
        int updated = pmsProductMapper.update(pmsProduct, lambdaQueryWrapper);
        return updated;
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProduct::getId, ids);
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setRecommandStatus(recommendStatus);
        int updated = pmsProductMapper.update(pmsProduct, lambdaQueryWrapper);
        return updated;
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProduct::getId, ids);
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setNewStatus(newStatus);
        int updated = pmsProductMapper.update(pmsProduct, lambdaQueryWrapper);
        return updated;
    }

    @Override
    public int deleteStatus(List<Long> ids, Integer deleteStatus) {
        LambdaQueryWrapper<PmsProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PmsProduct::getId, ids);
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setDeleteStatus(deleteStatus);
        int updated = pmsProductMapper.update(pmsProduct, lambdaQueryWrapper);
        return updated;
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return pmsProductMapper.getUpdateInfo(id);
    }

    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;


            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);

                Method insert = dao.getClass().getMethod("insert", Object.class);
                insert.invoke(dao, item);
            }
        } catch (Exception e) {
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) return;
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock skuStock = skuStockList.get(i);
            if (StrUtil.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                // 日期
                sb.append(sdf.format(new Date()));
                // 四位商品id
                sb.append(String.format("%04d", productId));
                // 三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }
}
