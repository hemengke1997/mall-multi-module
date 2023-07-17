package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.UmsResource;
import com.minko.mall.security.component.DynamicSecurityMetadataSource;
import com.minko.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "资源管理")
@RequestMapping("/resource")
@RestController
public class UmsResourceController {
    @Autowired
    private UmsResourceService umsResourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping("/create")
    public Result create(@RequestBody UmsResource umsResource) {
        boolean saved = umsResourceService.save(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改后台资源")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody UmsResource umsResource) {
        umsResource.setId(id);
        int i = umsResourceService.update(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (i > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("更新id获取资源详情")
    @GetMapping("/{id}")
    public Result<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = umsResourceService.getById(id);
        return Result.success(umsResource);
    }

    @ApiOperation("根据id删除后台资源")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        int i = umsResourceService.remove(id);
        dynamicSecurityMetadataSource.clearDataSource();

        if (i > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping("/listAll")
    public Result<List<UmsResource>> listAll() {
        List<UmsResource> list = umsResourceService.list();
        return Result.success(list);
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false) String nameKeyword,
                       @RequestParam(required = false) String urlKeyword,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsResource> page = new Page<>(pageNum, pageSize);

        Page<UmsResource> list = umsResourceService.selectPage(page, categoryId, nameKeyword, urlKeyword);
        return Result.success(CPage.restPage(list));
    }
}
