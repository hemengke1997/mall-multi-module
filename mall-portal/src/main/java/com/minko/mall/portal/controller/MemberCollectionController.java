package com.minko.mall.portal.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.MemberProductCollection;
import com.minko.mall.portal.service.MemberCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation("显示商品收藏详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Result<MemberProductCollection> detail(@RequestParam Long productId) {
        MemberProductCollection memberProductCollection = memberCollectionService.detail(productId);
        return Result.success(memberProductCollection);
    }
}
