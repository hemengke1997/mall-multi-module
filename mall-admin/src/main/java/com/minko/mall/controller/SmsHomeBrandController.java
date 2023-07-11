package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsHomeBrand;
import com.minko.mall.service.SmsHomeBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "首页品牌推荐管理")
@RestController
@RequestMapping("/home/brand")
public class SmsHomeBrandController {
    @Autowired
    private SmsHomeBrandService brandService;

    @ApiOperation("新增品牌推荐")
    @PostMapping("/create")
    public Result create(@RequestBody List<SmsHomeBrand> brandList) {
        int count = brandService.create(brandList);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("删除首页推荐")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = brandService.delete(ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("修改推荐排序")
    @PostMapping("/update/sort/{id}")
    public Result updateSort(@PathVariable Long id, Integer sort) {
        SmsHomeBrand brand = new SmsHomeBrand();
        brand.setSort(sort);
        brand.setId(id);
        boolean b = brandService.updateById(brand);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改推荐状态")
    @PostMapping("/update/recommendStatus")
    public Result updateStatus(@RequestParam("ids") List<Long> ids, @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = brandService.updateStatus(ids, recommendStatus);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("获取指定推荐信息")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        SmsHomeBrand byId = brandService.getById(id);
        return Result.success(byId);
    }

    @ApiOperation("分页获取首页品牌推荐列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "brandName", required = false) String brandName,
                       @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsHomeBrand> page = new Page<>(pageNum, pageSize);
        Page<SmsHomeBrand> list = brandService.selectPage(page, brandName, recommendStatus);
        return Result.success(CPage.restPage(list));
    }
}
