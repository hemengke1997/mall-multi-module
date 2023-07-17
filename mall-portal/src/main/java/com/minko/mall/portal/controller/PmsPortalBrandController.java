package com.minko.mall.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.portal.service.PmsPortalBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "前台品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsPortalBrandController {
    @Autowired
    private PmsPortalBrandService brandService;

    @ApiOperation("分页获取推荐品牌列表")
    @GetMapping("/recommendList")
    public Result recommendList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        List<PmsBrand> list = brandService.recommendList(pageNum, pageSize);
        return Result.success(list);
    }

    @ApiOperation("获取品牌详情")
    @GetMapping("/detail/{brandId}")
    public Result detail(@PathVariable Long brandId) {
        PmsBrand brand = brandService.detail(brandId);
        return Result.success(brand);
    }

    @ApiOperation("分页获取品牌相关商品")
    @GetMapping("/productList")
    public Result productList(@RequestParam Long brandId,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        Page<PmsProduct> page = brandService.productList(brandId, pageNum, pageSize);
        return Result.success(CPage.restPage(page));
    }
}
