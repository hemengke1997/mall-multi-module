package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsProductCategoryParam;
import com.minko.mall.dto.PmsProductCategoryWithChildrenItem;
import com.minko.mall.model.PmsProductCategory;
import com.minko.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品分类管理")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation("添加商品分类")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody PmsProductCategoryParam pmsProductCategoryParam) {
        int count = pmsProductCategoryService.create(pmsProductCategoryParam);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("分页查询商品分类")
    @GetMapping("/list/{parentId}")
    public Result getList(@PathVariable Long parentId,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<PmsProductCategory> page = new Page<>(pageNum, pageSize);
        Page<PmsProductCategory> list = pmsProductCategoryService.selectPage(page, parentId);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("修改导航栏显示状态")
    @PostMapping("/update/navStatus")
    public Result navStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
        int count = pmsProductCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改显示状态")
    @PostMapping("/update/showStatus")
    public Result showStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
        int count = pmsProductCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("根据id获取商品详情")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        PmsProductCategory pmsProductCategory = pmsProductCategoryService.getById(id);
        return Result.success(pmsProductCategory);
    }

    @ApiOperation("修改商品分类")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody PmsProductCategoryParam pmsProductCategoryParam) {

        int i = pmsProductCategoryService.updateItem(id, pmsProductCategoryParam);
        if (i > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除商品分类")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean removed = pmsProductCategoryService.removeById(id);
        if (removed) {
            return Result.success(null);
        }
        return Result.failed();
    }

    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping("/list/withChildren")
    public Result<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> list = pmsProductCategoryService.listWithChildren();
        return Result.success(list);
    }
}
