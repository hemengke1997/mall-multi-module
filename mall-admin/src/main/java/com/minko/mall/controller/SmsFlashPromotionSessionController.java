package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.dto.SmsFlashPromotionSessionDetail;
import com.minko.mall.model.SmsFlashPromotionSession;
import com.minko.mall.service.SmsFlashPromotionSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "限时购场次管理")
@RestController
@RequestMapping("/flashSession")
public class SmsFlashPromotionSessionController {
    @Autowired
    private SmsFlashPromotionSessionService flashPromotionSessionService;


    @ApiOperation("添加场次")
    @PostMapping("/create")
    public Result create(@RequestBody SmsFlashPromotionSession session) {
        boolean saved = flashPromotionSessionService.insert(session);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取全部场次")
    @GetMapping("/list")
    public Result list() {
        List<SmsFlashPromotionSession> list = flashPromotionSessionService.list();
        return Result.success(list);
    }

    @ApiOperation("获取全部可选场次和数量")
    @GetMapping("/selectList")
    public Result selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> promotionSessionList = flashPromotionSessionService.selectList(flashPromotionId);
        return Result.success(promotionSessionList);
    }

    @ApiOperation("更新指定场次开启状态")
    @PostMapping("/update/status/{id}")
    public Result updateStatus(@PathVariable Long id, Integer status) {
        int count = flashPromotionSessionService.updateStatus(id, status);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("更新场次")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody SmsFlashPromotionSession session) {
        session.setId(id);
        boolean b = flashPromotionSessionService.updateById(session);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除场次")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = flashPromotionSessionService.removeById(id);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }
}
