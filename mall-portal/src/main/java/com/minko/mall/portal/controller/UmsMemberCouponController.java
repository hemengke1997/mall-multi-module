package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsCoupon;
import com.minko.mall.portal.service.UmsMemberCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "会员优惠券管理")
@RestController
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {
    @Autowired
    private UmsMemberCouponService memberCouponService;

    @ApiOperation("获取用户优惠券列表")
    @ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<SmsCoupon> couponList = memberCouponService.list(useStatus);
        return Result.success(couponList);
    }

    @ApiOperation("获取当前商品相关优惠券")
    @GetMapping("/listByProduct/{productId}")
    public Result<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
        List<SmsCoupon> couponHistoryList = memberCouponService.listByProduct(productId);
        return Result.success(couponHistoryList);
    }
}
