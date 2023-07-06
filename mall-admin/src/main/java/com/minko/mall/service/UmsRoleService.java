package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.UmsMenu;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleService extends IService<UmsRole> {
    int create(UmsRole role);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);

    Page<UmsRole> page(Page<UmsRole> page, String keyword);

    void refreshAdminCount();

    @Transactional
    int delete(List<Long> ids);

    List<UmsMenu> listMenu(Long roleId);

    List<UmsResource> listResource(Long roleId);

    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
