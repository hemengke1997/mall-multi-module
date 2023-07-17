package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.PmsPortalProductDetail;
import com.minko.mall.portal.service.PmsPortalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "前台商品管理")
@RestController
@RequestMapping("/product")
public class PmsPortalProductController {
    @Autowired
    private PmsPortalProductService productService;

    @ApiOperation("获取商品详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id) {
        PmsPortalProductDetail productDetail = productService.detail(id);
        return Result.success(productDetail);
    }
}
