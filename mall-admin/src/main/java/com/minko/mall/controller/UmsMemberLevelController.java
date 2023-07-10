package com.minko.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.UmsMemberLevel;
import com.minko.mall.service.UmsMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "会员等级管理")
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {
    @Autowired
    private UmsMemberLevelService umsMemberLevelService;

    @ApiOperation("查询所有会员等级")
    @GetMapping("/list")
    public Result list(@RequestParam("defaultStatus") Integer defaultStatus) {
        LambdaQueryWrapper<UmsMemberLevel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UmsMemberLevel::getDefaultStatus, defaultStatus);
        List<UmsMemberLevel> list = umsMemberLevelService.list(lambdaQueryWrapper);
        return Result.success(list);
    }
}
