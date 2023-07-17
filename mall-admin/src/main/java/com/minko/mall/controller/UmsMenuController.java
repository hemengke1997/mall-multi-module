package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.UmsMenuNode;
import com.minko.mall.model.UmsMenu;
import com.minko.mall.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {
    @Autowired
    private UmsMenuService umsMenuService;

    @ApiOperation("添加后台菜单")
    @PostMapping("/create")
    public Result create(@RequestBody UmsMenu umsMenu) {
        boolean saved = umsMenuService.save(umsMenu);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("根据菜单名分页获取菜单列表")
    @GetMapping("/list/{parentId}")
    public Result list(@PathVariable Long parentId,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsMenu> page = new Page<>(pageNum, pageSize);
        Page<UmsMenu> umsMenuList = umsMenuService.selectPage(page, parentId);
        return Result.success(CPage.restPage(umsMenuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping("/treeList")
    public Result<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> list = umsMenuService.treeList();
        return Result.success(list);
    }

    @ApiOperation("显示或隐藏菜单")
    @PostMapping("/updateHidden/{id}")
    public Result updateHidde(@PathVariable Long id, @RequestParam(value = "hidden") Integer hidden) {
        int count = umsMenuService.updateHidden(id, hidden);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = umsMenuService.removeById(id);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("根据id获取菜单详情")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        UmsMenu umsMenu = umsMenuService.getById(id);
        return Result.success(umsMenu);
    }

    @ApiOperation("根据id更新菜单")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody UmsMenu umsMenu) {
        umsMenu.setId(id);
        boolean b = umsMenuService.updateById(umsMenu);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }
}
