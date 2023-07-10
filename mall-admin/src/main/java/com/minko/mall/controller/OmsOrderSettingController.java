package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.OmsOrderSetting;
import com.minko.mall.service.OmsOrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单设置管理")
@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
    @Autowired
    private OmsOrderSettingService omsOrderSettingService;

    @ApiOperation("获取指定订单设置")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        OmsOrderSetting omsOrderSetting = omsOrderSettingService.getById(id);
        return Result.success(omsOrderSetting);
    }

    @ApiOperation("修改订单设置")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody OmsOrderSetting orderSetting) {
        orderSetting.setId(id);
        boolean updated = omsOrderSettingService.updateById(orderSetting);
        if (updated) {
            return Result.success(1);
        }
        return Result.failed();
    }
}
