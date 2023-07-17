package com.minko.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.common.exception.Asserts;
import com.minko.mall.mapper.UmsMemberLevelMapper;
import com.minko.mall.mapper.UmsMemberMapper;
import com.minko.mall.model.UmsMember;
import com.minko.mall.model.UmsMemberLevel;
import com.minko.mall.portal.domain.MemberDetails;
import com.minko.mall.portal.service.UmsMemberCacheService;
import com.minko.mall.portal.service.UmsMemberService;
import com.minko.mall.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;


@Slf4j
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;

    @Autowired
    private UmsMemberCacheService memberCacheService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsMember getByUsername(String username) {
        // 先读redis缓存
        UmsMember member = memberCacheService.getMember(username);
        if (member != null) {
            return member;
        }
        // 若没有缓存，则读数据库
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMember::getUsername, username);
        List<UmsMember> umsMemberList = memberMapper.selectList(queryWrapper);
        // 读数据库成功后，设置redis缓存
        if (CollectionUtil.isNotEmpty(umsMemberList)) {
            memberCacheService.setMember(umsMemberList.get(0));
            return umsMemberList.get(0);
        }
        return null;
    }

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
        // 没有该用户，执行添加操作
        UmsMember member = new UmsMember();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setPhone(telephone);
        member.setStatus(1);
        LambdaQueryWrapper<UmsMemberLevel> memberLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLevelLambdaQueryWrapper.eq(UmsMemberLevel::getDefaultStatus, 1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectList(memberLevelLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            member.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(member);
        member.setPassword(null);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        return memberDetails.getUmsMember();
    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(rd.nextInt(10));
        }
        String authCode = sb.toString();
        memberCacheService.setAuthCode(telephone, authCode);
        return authCode;
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        // 判断手机号是否存在
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMember::getPhone, telephone);
        List<UmsMember> memberList = memberMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(memberList)) {
            Asserts.fail("手机号不存在");
        }
        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("验证码错误");
        }
        UmsMember member = memberList.get(0);
        member.setPassword(passwordEncoder.encode(password));
        memberMapper.updateById(member);
    }

    @Override
    public String refreshToken(String token) {
        String refreshToken = jwtTokenUtil.refreshToken(token);
        return refreshToken;
    }

    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StrUtil.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);
        return authCode.equals(realAuthCode);
    }
}
