package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.OmsOrderReturnReason;
import com.minko.mall.service.OmsOrderReturnReasonService;
import com.minko.mall.validator.FlagValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "退货原因管理")
@RestController
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {
    @Autowired
    private OmsOrderReturnReasonService returnReasonService;

    @ApiOperation("分页获取退货原因")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<OmsOrderReturnReason> page = new Page<>(pageNum, pageSize);
        Page<OmsOrderReturnReason> list = returnReasonService.selectPage(page);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("修改退货原因启用状态")
    @PostMapping("/update/status")
    public Result updateStatus(@Validated @FlagValidator(value = {"0", "1"}, message = "状态只能为0或1") @RequestParam(value = "status") Integer status,
                               @RequestParam("ids") List<Long> ids) {
        int count = returnReasonService.updateStatus(ids, status);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("添加退货原因")
    @PostMapping("/create")
    public Result create(@RequestBody OmsOrderReturnReason reason) {
        boolean saved = returnReasonService.save(reason);
        if (saved) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("批量删除退货原因")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = returnReasonService.delete(ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("更新退货原因")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id, @RequestBody OmsOrderReturnReason reason) {
        reason.setId(id);
        boolean b = returnReasonService.updateById(reason);
        if (b) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("获取单个退货原因")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        OmsOrderReturnReason byId = returnReasonService.getById(id);
        return Result.success(byId);
    }
}
