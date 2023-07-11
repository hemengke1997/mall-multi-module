package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.SmsCouponParam;
import com.minko.mall.model.SmsCoupon;
import com.minko.mall.service.SmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "优惠券管理")
@RestController
@RequestMapping("/coupon")
public class SmsCouponController {
    @Autowired
    private SmsCouponService couponService;

    @ApiOperation("添加优惠券")
    @PostMapping("/create")
    public Result create(@RequestBody SmsCouponParam couponParam) {
        int count = couponService.create(couponParam);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除优惠券")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        int count = couponService.delete(id);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("修改优惠券")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody SmsCouponParam couponParam) {
        int count = couponService.update(id, couponParam);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("获取指定优惠券信息")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        SmsCoupon byId = couponService.getById(id);
        return Result.success(byId);
    }


    @ApiOperation("根据优惠券名称和类型分页获取优惠券列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "type", required = false) Integer type,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsCoupon> page = new Page<>(pageNum, pageSize);
        Page<SmsCoupon> list = couponService.selectList(page, name, type);
        return Result.success(CPage.restPage(list));
    }
}
