package com.minko.mall.portal.service.impl;

import com.minko.mall.common.service.RedisService;
import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;

    @Override
    public void delMember(Long memberId) {

    }

    @Override
    public UmsMember getMember(String username) {
        return null;
    }

    @Override
    public void setMember(UmsMember member) {

    }

    // todo 添加异常捕获注解
    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = getAuthRedisKey(telephone);
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    // todo 添加异常捕获注解
    @Override
    public String getAuthCode(String telephone) {
        String key = getAuthRedisKey(telephone);
        return (String) redisService.get(key);
    }

    /**
     * 根据手机号获取验证码的redis缓存
     */
    private String getAuthRedisKey(String telephone) {
        return REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
    }
}
