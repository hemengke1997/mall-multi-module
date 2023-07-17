package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.SmsFlashPromotionProduct;
import com.minko.mall.model.SmsFlashPromotionProductRelation;
import com.minko.mall.service.SmsFlashPromotionProductRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限时购和商品关系管理
 */
@Api(tags = "限时购和商品关系管理")
@RestController
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {
    @Autowired
    private SmsFlashPromotionProductRelationService relationService;

    @ApiOperation("批量选择商品添加关联")
    @PostMapping("/create")
    public Result create(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {
        int count = relationService.create(relationList);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("删除商品关联")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = relationService.removeById(id);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("修改商品关联")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
        relation.setId(id);
        boolean b = relationService.updateById(relation);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("查询指定商品关联信息")
    @GetMapping("/{id}")
    public Result<SmsFlashPromotionProductRelation> getItem(@PathVariable Long id) {
        SmsFlashPromotionProductRelation byId = relationService.getById(id);
        return Result.success(byId);
    }

    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "flashPromotionId") Long flashPromotionId,
                       @RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsFlashPromotionProduct> page = new Page<>(pageNum, pageSize);
        Page<SmsFlashPromotionProduct> list = relationService.selectPage(page, flashPromotionId, flashPromotionSessionId);
        return Result.success(CPage.restPage(list));
    }
}
