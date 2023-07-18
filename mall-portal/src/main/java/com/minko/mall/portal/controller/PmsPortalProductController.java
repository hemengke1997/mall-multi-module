package com.minko.mall.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.portal.domain.PmsPortalProductDetail;
import com.minko.mall.portal.domain.PmsProductCategoryNode;
import com.minko.mall.portal.service.PmsPortalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "前台商品管理")
@RestController
@RequestMapping("/product")
public class PmsPortalProductController {
    @Autowired
    private PmsPortalProductService portalProductService;

    @ApiOperation(value = "综合搜索、筛选、排序")
    @GetMapping("/search")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    public Result search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Long brandId,
                         @RequestParam(required = false) Long productCategoryId,
                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<PmsProduct> productList = portalProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return Result.success(CPage.restPage(productList));
    }

    @ApiOperation("以树形结构获取所有商品分类")
    @GetMapping("/categoryTreeList")
    public Result<List<PmsProductCategoryNode>> categoryTreeList() {
        List<PmsProductCategoryNode> list = portalProductService.categoryTreeList();
        return Result.success(list);
    }

    @ApiOperation("获取商品详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id) {
        PmsPortalProductDetail productDetail = portalProductService.detail(id);
        return Result.success(productDetail);
    }
}
