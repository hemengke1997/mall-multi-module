package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.portal.domain.HomeContentResult;
import com.minko.mall.portal.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "首页内容管理")
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @ApiOperation("首页内容信息展示")
    @GetMapping("/content")
    public Result content() {
        HomeContentResult contentResult = homeService.content();
        return Result.success(contentResult);
    }

    @ApiOperation("分页获取推荐商品")
    @GetMapping("/recommendProductList")
    public Result recommendProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = homeService.recommendProductList(pageSize, pageNum);
        return Result.success(productList);
    }

    @ApiOperation("首页获取商品分类")
    @GetMapping("/productCateList/{parentId}")
    public Result productCateList(@PathVariable Long parentId) {
        List<PmsProductCategory> productCategoryList = homeService.getProductCateList(parentId);
        return Result.success(productCategoryList);
    }

    @ApiOperation("分页获取商品人气推荐列表")
    @GetMapping("/hotProductList")
    public Result hotProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        List<PmsProduct> productList = homeService.hotProductList(pageNum, pageSize);
        return Result.success(productList);
    }

    @ApiOperation("分页获取新品列表")
    @GetMapping("/newProductList")
    public Result newProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        List<PmsProduct> productList = homeService.newProductList(pageNum, pageSize);
        return Result.success(productList);
    }
}
