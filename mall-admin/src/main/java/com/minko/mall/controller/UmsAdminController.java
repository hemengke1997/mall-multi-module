package com.minko.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.UmsAdminLoginParam;
import com.minko.mall.dto.UmsAdminParam;
import com.minko.mall.dto.UpdateAdminPasswordParam;
import com.minko.mall.model.UmsAdmin;
import com.minko.mall.model.UmsResource;
import com.minko.mall.model.UmsRole;
import com.minko.mall.security.util.JwtTokenUtil;
import com.minko.mall.service.UmsAdminService;
import com.minko.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsRoleService umsRoleService;

    private final String COOKIE_NAME = "jwt_token";

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UmsAdminParam umsAdminDto) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminDto);
        if (umsAdmin == null) {
            return Result.failed();
        }
        return Result.success(umsAdmin);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(HttpServletResponse response, @Validated @RequestBody UmsAdminLoginParam umsAdminLoginDto) {
        String token = umsAdminService.login(umsAdminLoginDto.getUsername(), umsAdminLoginDto.getPassword());
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        log.info("cookie:{}", cookie);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success(tokenMap, "登录成功");
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        String token = jwtTokenUtil.getTokenFromRequest(request);
        String refreshToken = umsAdminService.refreshToken(token);
        if (refreshToken == null) {
            return Result.failed("token过期");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success(tokenMap);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(HttpServletResponse response) {
        umsAdminService.logout();
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return Result.success(null);
    }

    @ApiOperation("获取当前登录用户的信息")
    @GetMapping("/info")
    public Result getAdminInfo(Principal principal) {
        if (principal == null) {
            return Result.unauthorized(null);
        }

        String username = principal.getName();
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());


        data.put("icon", umsAdmin.getIcon());

        data.put("menus", umsRoleService.getMenuList(umsAdmin.getId()));
        List<UmsRole> roleList = umsAdminService.getRoleList(umsAdmin.getId());

        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }


        return Result.success(data);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/{id}")
    public Result<UmsAdmin> getAdminById(@PathVariable Long id) {
        UmsAdmin admin = umsAdminService.getAdminById(id);
        return Result.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody UmsAdmin umsAdmin) {
        umsAdmin.setId(id);
        int s = umsAdminService.update(umsAdmin);
        if (s > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updateAdminPasswordDto) {
        int status = umsAdminService.updatePassword(updateAdminPasswordDto);

        if (status > 0) {
            return Result.success(status);
        } else if (status == -1) {
            return Result.failed("参数不合法");
        } else if (status == -2) {
            return Result.failed("找不到该用户");
        } else if (status == -3) {
            return Result.failed("旧密码错误");
        } else if (status == -4) {
            return Result.failed("新密码不能跟旧密码一样");
        }
        return Result.failed();
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        int removed = umsAdminService.delete(id);
        if (removed > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改账号状态")
    @PostMapping("/updateStatus/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        umsAdmin.setId(id);
        int update = umsAdminService.update(umsAdmin);
        if (update > 0) {
            return Result.success(update);
        }
        return Result.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update")
    public Result updateRole(@RequestParam("adminId") Long adminId, @RequestParam("roleIds") List<Long> roleIds) {
        int count = umsAdminService.updateRole(adminId, roleIds);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public Result<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = umsAdminService.getRoleList(adminId);
        return Result.success(roleList);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        Page<UmsAdmin> umsAdminList = umsAdminService.page(page, keyword);

        return Result.success(CPage.restPage(umsAdminList));
    }

    @ApiOperation("获取用户的权限")
    @GetMapping("/resource/{id}")
    public Result<List<UmsResource>> getResourceByAdminId(@PathVariable Long id) {
        List<UmsResource> resourceList = umsAdminService.getResourceList(id);
        return Result.success(resourceList);
    }

    @ApiOperation("清空redis缓存")
    @PostMapping("/redis/clear")
    public Result<Object> redisClear() {
        umsAdminService.clearCache();
        return Result.success(null);
    }
}
