package com.minko.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.common.exception.Asserts;
import com.minko.mall.portal.mapper.UmsMemberMapper;
import com.minko.mall.portal.model.UmsMember;
import com.minko.mall.portal.service.UmsMemberCacheService;
import com.minko.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private UmsMemberCacheService memberCacheService;

    @Override
    public void register(String username, String password, String telephone, String authCode) {
        // 验证redis中验证码
        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("验证码错误");
        }
        // 查询是否已有该用户
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMember::getUsername, username);
        queryWrapper.or().eq(UmsMember::getPhone, telephone);
        List<UmsMember> members = memberMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(members)) {
            Asserts.fail("该用户已经存在");
        }
    }

    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StrUtil.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);
        return authCode.equals(realAuthCode);
    }
}
