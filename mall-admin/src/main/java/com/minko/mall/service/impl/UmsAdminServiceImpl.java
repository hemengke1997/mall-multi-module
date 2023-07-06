package com.minko.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.bo.UmsAdminUserDetails;
import com.minko.mall.common.exception.Asserts;
import com.minko.mall.common.util.RequestUtil;
import com.minko.mall.dto.UmsAdminParam;
import com.minko.mall.dto.UpdateAdminPasswordParam;
import com.minko.mall.mapper.UmsAdminLoginLogMapper;
import com.minko.mall.mapper.UmsAdminMapper;
import com.minko.mall.mapper.UmsAdminRoleRelationMapper;
import com.minko.mall.mapper.UmsRoleMapper;
import com.minko.mall.model.*;
import com.minko.mall.security.util.JwtTokenUtil;
import com.minko.mall.service.UmsAdminCacheService;
import com.minko.mall.service.UmsAdminRoleRelationService;
import com.minko.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    private UmsAdminRoleRelationService umsAdminRoleRelationService;

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (userDetails == null) {
                Asserts.fail("用户不存在");
            }
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码错误");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 根据用户账号密码生成jwt
            token = jwtTokenUtil.generateToken(userDetails);

            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }

        return token;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        // 先从缓存中获取数据
        UmsAdmin admin = getCacheService().getAdmin(username);
        if (admin != null) return admin;

        // 缓存中没有，则从数据库中获取
        LambdaQueryWrapper<UmsAdmin> umsAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        umsAdminLambdaQueryWrapper.eq(UmsAdmin::getUsername, username);
        UmsAdmin umsAdmin = umsAdminMapper.selectOne(umsAdminLambdaQueryWrapper);

        if (umsAdmin != null) {
            getCacheService().setAdmin(umsAdmin);
            return umsAdmin;
        }
        return null;
    }


    @Override
    public UmsAdminUserDetails loadUserByUsername(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin != null) {
            // 获取用户对应的权限集
            List<UmsResource> resourceList = getResourceList(umsAdmin.getId());
            return new UmsAdminUserDetails(umsAdmin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminDto) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminDto, umsAdmin);
        umsAdmin.setStatus(1);
        LambdaQueryWrapper<UmsAdmin> umsAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        umsAdminLambdaQueryWrapper.eq(UmsAdmin::getUsername, umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectList(umsAdminLambdaQueryWrapper);
        if (umsAdminList.size() > 0) {
            Asserts.fail("账号已存在");
            return null;
        }

        // 密码加密
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public void insertLoginLog(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin == null) return;

        umsAdmin.setLoginTime(new Date());
        umsAdminMapper.updateById(umsAdmin);

        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(umsAdmin.getId());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            loginLog.setIp(RequestUtil.getRequestIp(request));
        }
        umsAdminLoginLogMapper.insert(loginLog);
    }

    @Override
    public void logout() {
        // 删除redis缓存
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UmsAdminUserDetails umsAdminUserDetails = (UmsAdminUserDetails) authentication.getPrincipal();
        UmsAdmin umsAdmin = umsAdminUserDetails.getUmsAdmin();
        if (umsAdmin != null) {
            getCacheService().delAdmin(umsAdmin.getId());
        }
    }

    @Override
    public UmsAdmin getAdminById(Long id) {
        return umsAdminMapper.selectById(id);
    }

    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername()) || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }

        LambdaQueryWrapper<UmsAdmin> umsAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        umsAdminLambdaQueryWrapper.eq(UmsAdmin::getUsername, param.getUsername());
        UmsAdmin umsAdmin = umsAdminMapper.selectOne(umsAdminLambdaQueryWrapper);
        if (umsAdmin == null) {
            return -2;
        }

        if (!passwordEncoder.matches(param.getOldPassword(), umsAdmin.getPassword())) {
            return -3;
        }
        if (passwordEncoder.matches(param.getNewPassword(), umsAdmin.getPassword())) {
            return -4;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));

        umsAdminMapper.updateById(umsAdmin);
        getCacheService().delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public int delete(Long id) {
        getCacheService().delAdmin(id);
        int deleted = umsAdminMapper.deleteById(id);
        // 删除用户对应的资源缓存
        getCacheService().delResourceList(id);
        return deleted;
    }

    @Override
    public int update(UmsAdmin umsAdmin) {
        getCacheService().delAdmin(umsAdmin.getId());
        return baseMapper.updateById(umsAdmin);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        // 先删除原来的关系
        LambdaQueryWrapper<UmsAdminRoleRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsAdminRoleRelation::getAdminId, adminId);
        umsAdminRoleRelationMapper.delete(queryWrapper);

        if (!CollectionUtil.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation umsAdminRoleRelation = new UmsAdminRoleRelation();
                umsAdminRoleRelation.setRoleId(roleId);
                umsAdminRoleRelation.setAdminId(adminId);
                list.add(umsAdminRoleRelation);
            }
            umsAdminRoleRelationService.saveBatch(list);
        }
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        List<UmsRole> roleList = umsAdminRoleRelationMapper.getRoleList(adminId);
        return roleList;
    }

    @Override
    public Page<UmsAdmin> page(Page<UmsAdmin> page, String keyword) {
        LambdaQueryWrapper<UmsAdmin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isEmpty(keyword)) {
            queryWrapper
                    .like(UmsAdmin::getUsername, keyword)
                    .or()
                    .like(UmsAdmin::getNickName, keyword);
        }
        Page<UmsAdmin> umsAdminPage = baseMapper.selectPage(page, queryWrapper);
        return umsAdminPage;
    }

    @Override
    public void clearCache() {
        getCacheService().delAll();
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        // 优先从缓存中取
        List<UmsResource> resourceList = getCacheService().getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = umsAdminRoleRelationMapper.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            // 存入redis
            getCacheService().setResouceList(adminId, resourceList);
        }
        return resourceList;
    }
}
