package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import com.minko.mall.model.PmsProductAttributeCategory;
import com.minko.mall.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品属性分类管理")
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @ApiOperation("添加商品属性分类")
    @PostMapping("/create")
    public Result create(@RequestParam String name) {
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setName(name);
        boolean saved = pmsProductAttributeCategoryService.save(pmsProductAttributeCategory);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取所有商品属性分类")
    @GetMapping("/list")
    public Result getList(@RequestParam(defaultValue = "5") Integer pageSize,
                          @RequestParam(defaultValue = "1") Integer pageNum) {
        Page<PmsProductAttributeCategory> page = new Page<>(pageNum, pageSize);
        Page<PmsProductAttributeCategory> list = pmsProductAttributeCategoryService.selectPage(page);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("删除商品属性分类")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean removed = pmsProductAttributeCategoryService.removeById(id);
        if (removed) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("更新商品属性分类")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestParam String name) {
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setId(id);
        pmsProductAttributeCategory.setName(name);
        boolean b = pmsProductAttributeCategoryService.updateById(pmsProductAttributeCategory);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取所有商品属性分类及其下属性")
    @GetMapping("/list/withAttr")
    public Result getListWithAttr() {
        List<PmsProductAttributeCategoryDto> list = pmsProductAttributeCategoryService.getListWithAttr();
        return Result.success(list);
    }
}
