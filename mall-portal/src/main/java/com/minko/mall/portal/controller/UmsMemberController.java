package com.minko.mall.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "会员管理")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private UmsMemberService memberService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public Result register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String telephone,
                           @RequestParam String authCode) {
        memberService.register(username, password, telephone, authCode);
        return Result.success(null, "注册成功");
    }

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password) {
        String token = memberService.login(username, password);
        if (StrUtil.isEmpty(token)) {
            return Result.failed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success(tokenMap);
    }

    @ApiOperation("获取会员信息")
    @GetMapping("/info")
    public Result info(Principal principal) {
        if (principal == null) {
            return Result.unauthorized(null);
        }
        UmsMember member = memberService.getCurrentMember();
        return Result.success(member);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    public Result getAuthCode(@RequestParam String telephone) {
        String authCode = memberService.generateAuthCode(telephone);
        return Result.success(authCode, "获取验证码成功");
    }

    @ApiOperation("会员修改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam String telephone,
                                 @RequestParam String password,
                                 @RequestParam String authCode) {
        memberService.updatePassword(telephone, password, authCode);
        return Result.success(null, "密码修改成功");
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = memberService.refreshToken(token);
        if (refreshToken == null) {
            return Result.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success(tokenMap);
    }
}
