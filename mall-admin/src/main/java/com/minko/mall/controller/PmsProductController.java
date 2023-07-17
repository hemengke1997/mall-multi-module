package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsProductParam;
import com.minko.mall.dto.PmsProductQueryParam;
import com.minko.mall.dto.PmsProductResult;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService pmsProductService;

    @ApiOperation("添加商品")
    @PostMapping("/create")
    public Result create(@RequestBody PmsProductParam pmsProductParam) {
        int count = pmsProductService.create(pmsProductParam);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("分页获取商品列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       PmsProductQueryParam pmsProductQueryParam) {
        Page<PmsProduct> pmsProductPage = new Page<>(pageNum, pageSize);

        Page<PmsProduct> list = pmsProductService.selectPage(pmsProductPage, pmsProductQueryParam);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("修改商品上架状态")
    @PostMapping("/update/publishStatus")
    public Result updateStatus(@RequestParam("ids") List<Long> ids,
                               @RequestParam("publishStatus") Integer publishStatus) {
        int count = pmsProductService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("批量推荐商品")
    @PostMapping("/update/recommendStatus")
    public Result updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = pmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return Result.success(1);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation("批量设为新品")
    @PostMapping("/update/newStatus")
    public Result updateNewStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("newStatus") Integer newStatus) {
        int count = pmsProductService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return Result.success(count);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation("逻辑删除商品")
    @PostMapping("/update/deleteStatus")
    public Result deleteStatus(@RequestParam("ids") List<Long> ids,
                               @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = pmsProductService.deleteStatus(ids, deleteStatus);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping("/updateInfo/{id}")
    public Result getUpdateInfo(@PathVariable Long id) {
        PmsProductResult pmsProductResult = pmsProductService.getUpdateInfo(id);
        return Result.success(pmsProductResult);
    }
}
