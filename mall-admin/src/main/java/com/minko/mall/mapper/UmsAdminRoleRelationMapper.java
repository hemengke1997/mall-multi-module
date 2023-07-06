package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.UmsAdminRoleRelation;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {
    List<UmsRole> getRoleList(@Param(("adminId")) Long adminId);

    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 根据资源id获取对应的用户idList
     */
    List<Long> getAdminList(@Param("resourceId") Long resourceId);
}
