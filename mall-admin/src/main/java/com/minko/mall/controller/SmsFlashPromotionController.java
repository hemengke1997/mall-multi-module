package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsFlashPromotion;
import com.minko.mall.service.SmsFlashPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "限时购活动管理")
@RestController
@RequestMapping("/flash")
public class SmsFlashPromotionController {
    @Autowired
    private SmsFlashPromotionService flashPromotionService;

    @ApiOperation("添加活动")
    @PostMapping("/create")
    public Result create(@RequestBody SmsFlashPromotion promotion) {
        boolean saved = flashPromotionService.save(promotion);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("分页获取活动列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsFlashPromotion> page = new Page<>(pageNum, pageSize);
        Page<SmsFlashPromotion> list = flashPromotionService.selectPage(page, keyword);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("修改活动上架状态")
    @PostMapping("/update/status/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
        int count = flashPromotionService.updateStatus(id, status);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("编辑活动")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody SmsFlashPromotion promotion) {
        promotion.setId(id);
        boolean b = flashPromotionService.updateById(promotion);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除活动")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = flashPromotionService.removeById(id);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }
}
