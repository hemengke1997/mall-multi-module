package com.minko.mall.portal.controller;

import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.MemberProductCollection;
import com.minko.mall.portal.service.MemberCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 会员商品收藏管理
 */
@Api(tags = "会员商品收藏管理")
@RestController
@RequestMapping("/member/productCollection")
public class MemberCollectionController {
    @Autowired
    private MemberCollectionService memberCollectionService;

    @ApiOperation("添加商品收藏")
    @PostMapping("/add")
    public Result add(@RequestBody MemberProductCollection memberProductCollection) {
        int count = memberCollectionService.add(memberProductCollection);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("删除商品收藏")
    @PostMapping("/delete")
    public Result delete(Long productId) {
        int count = memberCollectionService.delete(productId);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("显示商品收藏详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Result<MemberProductCollection> detail(@RequestParam Long productId) {
        MemberProductCollection memberProductCollection = memberCollectionService.detail(productId);
        return Result.success(memberProductCollection);
    }

    @ApiOperation("显示用户商品收藏列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<MemberProductCollection> collections = memberCollectionService.list(pageNum, pageSize);
        return Result.success(CPage.restPage(collections));
    }

    @ApiOperation("清空用户收藏商品")
    @PostMapping("/clear")
    public Result clear() {
        memberCollectionService.clear();
        return Result.success(1);
    }
}
