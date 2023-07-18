package com.minko.mall.portal.controller;

import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.MemberReadHistory;
import com.minko.mall.portal.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "会员商品浏览记录")
@RestController
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("新增历史记录")
    @PostMapping("/create")
    public Result create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("分页获取浏览历史记录")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<MemberReadHistory> page = memberReadHistoryService.list(pageNum, pageSize);
        return Result.success(CPage.restPage(page));
    }

    @ApiOperation("批量删除历史记录")
    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "ids") List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("清空历史记录")
    @PostMapping("/clear")
    public Result clear() {
        memberReadHistoryService.clear();
        return Result.success(1);
    }
}
