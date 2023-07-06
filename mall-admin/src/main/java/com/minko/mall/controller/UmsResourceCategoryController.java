package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.UmsResourceCategory;
import com.minko.mall.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {
    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;

    @ApiOperation("查询所有资源分类")
    @GetMapping("/listAll")
    public Result<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> list = umsResourceCategoryService.orderList();
        return Result.success(list);
    }

    @ApiOperation("添加资源分类")
    @PostMapping("/create")
    public Result create(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean saved = umsResourceCategoryService.save(umsResourceCategory);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("更新资源分类")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        boolean updated = umsResourceCategoryService.updateById(umsResourceCategory);
        if (updated) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除资源分类")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = umsResourceCategoryService.removeById(id);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }
}
