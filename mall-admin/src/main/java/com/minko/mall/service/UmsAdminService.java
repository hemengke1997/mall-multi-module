package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.UmsAdminParam;
import com.minko.mall.dto.UpdateAdminPasswordParam;
import com.minko.mall.model.UmsAdmin;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsAdminService extends IService<UmsAdmin> {

    String login(String username, String password);

    UmsAdmin getAdminByUsername(String username);

    UserDetails loadUserByUsername(String username);

    String refreshToken(String token);

    UmsAdmin register(UmsAdminParam umsAdminDto);

    void insertLoginLog(String username);

    void logout();

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getAdminById(Long id);

    UmsAdminCacheService getCacheService();

    int updatePassword(UpdateAdminPasswordParam updateAdminPasswordDto);

    int delete(Long id);

    int update(UmsAdmin umsAdmin);

    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    List<UmsRole> getRoleList(Long adminId);

    Page<UmsAdmin> page(Page<UmsAdmin> page, String keyword);

    void clearCache();

    List<UmsResource> getResourceList(Long adminId);
}
