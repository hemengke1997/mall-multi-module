package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.UmsMenu;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import com.minko.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/role")
public class UmsRoleController {
    @Autowired
    UmsRoleService umsRoleService;

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public Result create(@RequestBody UmsRole umsRole) {
        int i = umsRoleService.create(umsRole);
        if (i > 0) {
            return Result.success(i);
        }
        return Result.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody UmsRole umsRole) {
        umsRole.setId(id);
        boolean b = umsRoleService.updateById(umsRole);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public Result<List<UmsRole>> listAll() {
        List<UmsRole> umsRoleList = umsRoleService.list();
        return Result.success(umsRoleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        Page<UmsRole> umsRoleList = umsRoleService.page(page, keyword);
        return Result.success(CPage.restPage(umsRoleList));
    }

    @ApiOperation("刷新角色的后台用户数量")
    @GetMapping("/refreshAdminCount")
    public Result refreshAdminCount() {
        umsRoleService.refreshAdminCount();
        return Result.success(1);
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRole role = new UmsRole();
        role.setId(id);
        role.setStatus(status);
        boolean updated = umsRoleService.updateById(role);
        if (updated) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = umsRoleService.delete(ids);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("/listMenu/{roleId}")
    public Result listMenu(@PathVariable Long roleId) {
        List<UmsMenu> menuList = umsRoleService.listMenu(roleId);
        return Result.success(menuList);
    }

    @ApiOperation("获取角色对应的资源")
    @GetMapping("/listResource/{roleId}")
    public Result listResource(@PathVariable Long roleId) {
        List<UmsResource> list = umsRoleService.listResource(roleId);
        return Result.success(list);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public Result allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = umsRoleService.allocResource(roleId, resourceIds);
        return Result.success(count);
    }
}
