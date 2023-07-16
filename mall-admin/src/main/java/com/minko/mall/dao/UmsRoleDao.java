package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.UmsMenu;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsRoleDao extends BaseMapper<UmsRole> {
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    void refreshAdminCount();

    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
