package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsCouponHistory;
import com.minko.mall.service.SmsCouponHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "优惠券领取记录管理")
@RestController
@RequestMapping("/couponHistory")
public class SmsCouponHistoryController {
    @Autowired
    private SmsCouponHistoryService historyService;

    @ApiOperation("根据优惠券id，使用状态，订单编号分页获取领取记录")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "couponId", required = false) Long couponId,
                       @RequestParam(value = "useStatus", required = false) Integer useStatus,
                       @RequestParam(value = "orderSn", required = false) String orderSn,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsCouponHistory> page = new Page<>(pageNum, pageSize);
        Page<SmsCouponHistory> list = historyService.selectPage(page, couponId, useStatus, orderSn);
        return Result.success(CPage.restPage(list));
    }
}
