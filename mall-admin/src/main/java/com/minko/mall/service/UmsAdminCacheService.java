package com.minko.mall.service;

import com.minko.mall.model.UmsAdmin;
import com.minko.mall.model.UmsResource;

import java.util.List;

public interface UmsAdminCacheService {
    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);


    void delAdmin(Long adminId);

    void delAll();

    /**
     * 设置缓存后台用户资源列表
     */
    void setResouceList(Long adminId, List<UmsResource> list);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 当用户的角色改变时 删除用户的资源缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当用户的角色改变时 删除用户的资源缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 用户改变时，删除用户的资源缓存
     */
    void delResourceList(Long id);

    void delResourceListByResource(Long id);
}
