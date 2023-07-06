package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.common.exception.Asserts;
import com.minko.mall.mapper.UmsAdminRoleRelationMapper;
import com.minko.mall.mapper.UmsRoleMapper;
import com.minko.mall.mapper.UmsRoleResourceRelationMapper;
import com.minko.mall.model.*;
import com.minko.mall.service.UmsAdminCacheService;
import com.minko.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;

    @Autowired
    private UmsAdminCacheService umsAdminCacheService;

    @Override
    public int create(UmsRole role) {
        role.setSort(0);
        role.setAdminCount(0);
        int insert = baseMapper.insert(role);
        return insert;
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        List<UmsMenu> menuList = umsRoleMapper.getMenuList(adminId);
        return menuList;
    }

    @Override
    public Page<UmsRole> page(Page<UmsRole> page, String keyword) {
        LambdaQueryWrapper<UmsRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isEmpty(keyword)) {
            lambdaQueryWrapper.like(UmsRole::getName, keyword);
        }
        Page<UmsRole> umsRolePage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return umsRolePage;
    }

    @Override
    public void refreshAdminCount() {
        umsRoleMapper.refreshAdminCount();
    }

    @Override
    public int delete(List<Long> ids) {
        ids.forEach(id -> {
            // 查询当前角色id是否绑定了用户
            LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getRoleId, id);
            int count1 = umsAdminRoleRelationMapper.selectCount(adminRoleRelationLambdaQueryWrapper);
            if (count1 > 0) {
                Asserts.fail("角色ID：" + id + "绑定了后台用户，无法删除");
            }

            // 查询当前角色id是否绑定了菜单
            LambdaQueryWrapper<UmsRoleMenuRelation> roleMenuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleMenuRelationLambdaQueryWrapper.eq(UmsRoleMenuRelation::getRoleId, id);
            int count2 = umsAdminRoleRelationMapper.selectCount(adminRoleRelationLambdaQueryWrapper);
            if (count2 > 0) {
                Asserts.fail("角色ID：" + id + "绑定了菜单，无法删除");
            }

            // 删除
            UmsRole role = new UmsRole();
            role.setId(id);
            umsRoleMapper.deleteById(role);
        });
        umsAdminCacheService.delResourceListByRoleIds(ids);
        return 1;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return umsRoleMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return umsRoleMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        // 先删除原有关系
        LambdaQueryWrapper<UmsRoleResourceRelation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UmsRoleResourceRelation::getRoleId, roleId);
        umsRoleResourceRelationMapper.delete(lambdaQueryWrapper);
        // 批量插入新关系
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            umsRoleResourceRelationMapper.insert(relation);
        }
        umsAdminCacheService.delResourceListByRole(roleId);
        return 1;
    }
}
