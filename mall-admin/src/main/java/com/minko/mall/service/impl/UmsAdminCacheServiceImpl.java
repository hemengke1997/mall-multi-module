package com.minko.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minko.mall.model.UmsAdmin;
import com.minko.mall.model.UmsAdminRoleRelation;
import com.minko.mall.model.UmsResource;
import com.minko.mall.common.service.RedisService;
import com.minko.mall.dao.UmsAdminRoleRelationDao;
import com.minko.mall.service.UmsAdminCacheService;
import com.minko.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private long REDIS_EXPIRE;

    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    private String generateAdminKey(String username) {
        return REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
    }

    private String generateResourceKey(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return key;
    }

    private String generateResourceKey() {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        return key;
    }


    @Override
    public UmsAdmin getAdmin(String username) {
        String key = generateAdminKey(username);
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = generateAdminKey(admin.getUsername());
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = umsAdminService.getAdminById(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delAll() {
        redisService.delAll();
    }

    @Override
    public void setResouceList(Long adminId, List<UmsResource> list) {
        String key = generateResourceKey(adminId);
        redisService.set(key, list, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return (List<UmsResource>) redisService.get(generateResourceKey(adminId));
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        LambdaQueryWrapper<UmsAdminRoleRelation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UmsAdminRoleRelation::getRoleId, roleId);
        List<UmsAdminRoleRelation> umsAdminRoleRelationList = umsAdminRoleRelationDao.selectList(lambdaQueryWrapper);

        delResourceList(umsAdminRoleRelationList);
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<UmsAdminRoleRelation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UmsAdminRoleRelation::getRoleId, roleIds);
        List<UmsAdminRoleRelation> umsAdminRoleRelationList = umsAdminRoleRelationDao.selectList(lambdaQueryWrapper);

        delResourceList(umsAdminRoleRelationList);
    }

    @Override
    public void delResourceList(Long id) {
        redisService.del(generateResourceKey(id));
    }

    @Override
    public void delResourceListByResource(Long id) {
        List<Long> adminIdList = umsAdminRoleRelationDao.getAdminList(id);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = generateResourceKey();
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    private void delResourceList(List<UmsAdminRoleRelation> list) {
        if (CollUtil.isNotEmpty(list)) {
            String keyPrefix = generateResourceKey();
            List<String> keys = list.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }
}
