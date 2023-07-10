package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.PmsSkuStock;
import com.minko.mall.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "sku商品库存管理")
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    private PmsSkuStockService pmsSkuStockService;

    @ApiOperation("根据商品ID及sku编码模糊搜索sku库存")
    @GetMapping("/{pid}")
    public Result<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword", required = false) String keyword) {
        List<PmsSkuStock> skuStockList = pmsSkuStockService.getList(pid, keyword);
        return Result.success(skuStockList);
    }

    @ApiOperation("批量更新sku库存信息")
    @PostMapping("/update/{pid}")
    public Result update(@PathVariable Long pid, @RequestBody List<PmsSkuStock> skuStockList) {
        int count = pmsSkuStockService.update(pid, skuStockList);
        if (count > 0) {
            return Result.success(count);
        } else {
            return Result.failed();
        }
    }
}
