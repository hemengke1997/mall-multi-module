package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsBrandParam;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.service.PmsBrandSerive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "品牌管理")
@RequestMapping("/brand")
@RestController
public class PmsBrandController {
    @Autowired
    private PmsBrandSerive pmsBrandSerive;

    @ApiOperation("新建品牌")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody PmsBrandParam pmsBrand) {
        int count = pmsBrandSerive.create(pmsBrand);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改品牌")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id,
                         @Validated @RequestBody PmsBrandParam pmsBrandParam) {
        int count = pmsBrandSerive.updateBrand(id, pmsBrandParam);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除品牌")
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean removed = pmsBrandSerive.removeById(id);
        if (removed) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("根据品牌名称分页获取品牌列表")
    @GetMapping("/list")
    public Result getList(@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "showStatus", required = false) Integer showStatus,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<PmsBrand> pmsBrandPage = new Page<>(pageNum, pageSize);
        Page<PmsBrand> list = pmsBrandSerive.selectPage(pmsBrandPage, keyword, showStatus);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("根据id获取品牌信息")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        PmsBrand pmsBrand = pmsBrandSerive.getById(id);
        return Result.success(pmsBrand);
    }
}
