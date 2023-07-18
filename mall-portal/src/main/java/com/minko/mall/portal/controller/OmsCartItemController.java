package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.OmsCartItem;
import com.minko.mall.portal.service.OmsCartItemService;
import com.minko.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "购物车管理")
@RestController
@RequestMapping("/cart")
public class OmsCartItemController {
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public Result add(@RequestBody OmsCartItem cartItem) {
        int count = cartItemService.add(cartItem);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("删除购物车")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = cartItemService.delete(memberService.getCurrentMember().getId(), ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("获取购物车列表")
    @GetMapping("/list")
    public Result list() {
        List<OmsCartItem> cartItemList = cartItemService.list(memberService.getCurrentMember().getId());
        return Result.success(cartItemList);
    }

    @ApiOperation("修改购物车中商品的数量")
    @GetMapping("/update/quantity")
    public Result updateQuantity(@RequestParam Long id,
                                 @RequestParam Integer quantity) {
        int count = cartItemService.updateQuantity(id, memberService.getCurrentMember().getId(), quantity);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("清空购物车")
    @PostMapping("/clear")
    public Result clear() {
        int count = cartItemService.clear();
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }
}
