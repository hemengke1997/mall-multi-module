package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsCoupon;
import com.minko.mall.portal.service.UmsMemberCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "会员优惠券管理")
@RestController
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {
    @Autowired
    private UmsMemberCouponService memberCouponService;

    @ApiOperation("获取当前商品相关优惠券")
    @GetMapping("/listByProduct/{productId}")
    public Result<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
        List<SmsCoupon> couponHistoryList = memberCouponService.listByProduct(productId);
        return Result.success(couponHistoryList);
    }
}
