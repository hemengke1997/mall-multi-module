package com.minko.mall.portal.controller;

import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.MemberBrandAttention;
import com.minko.mall.portal.service.MemberAttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags = "会员关注品牌管理")
@RestController
@RequestMapping("/member/attention")
public class MemberAttentionController {
    @Autowired
    private MemberAttentionService memberAttentionService;

    @ApiOperation("添加品牌关注")
    @PostMapping("/add")
    public Result add(@RequestBody MemberBrandAttention memberBrandAttention) {
        int count = memberAttentionService.add(memberBrandAttention);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("取消品牌关注")
    @PostMapping("/delete")
    public Result delete(Long brandId) {
        int count = memberAttentionService.delete(brandId);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("显示用户关注品牌列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<MemberBrandAttention> page = memberAttentionService.list(pageNum, pageSize);
        return Result.success(CPage.restPage(page));
    }


    @ApiOperation("显示品牌关注详情")
    @GetMapping("/detail")
    public Result detail(@RequestParam Long brandId) {
        MemberBrandAttention memberBrandAttention = memberAttentionService.detail(brandId);
        return Result.success(memberBrandAttention);
    }

    @ApiOperation("清空用户关注品牌列表")
    @PostMapping("/clear")
    public Result clear() {
        memberAttentionService.clear();
        return Result.success(1);
    }
}
