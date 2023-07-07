package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsProductParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @ApiOperation("添加商品")
    @PostMapping("/create")
    public Result create(@RequestBody PmsProductParam pmsProductParam) {
        return Result.failed();
    }
}
