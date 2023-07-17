package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.OmsReturnApplyQueryParam;
import com.minko.mall.dto.OmsUpdateStatusParam;
import com.minko.mall.model.OmsOrderReturnApply;
import com.minko.mall.service.OmsOrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单退货申请管理")
@RestController
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {
    @Autowired
    private OmsOrderReturnApplyService omsOrderReturnApplyService;

    @ApiOperation("分页查询退货申请")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       OmsReturnApplyQueryParam queryParam) {
        Page<OmsOrderReturnApply> page = new Page<>(pageNum, pageSize);

        Page<OmsOrderReturnApply> list = omsOrderReturnApplyService.selectPage(page, queryParam);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("查看退货详情")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        OmsOrderReturnApply applyServiceById = omsOrderReturnApplyService.getById(id);
        return Result.success(applyServiceById);
    }

    @ApiOperation("修改退货申请")
    @PostMapping("/update/status/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestBody OmsUpdateStatusParam param) {
        int count = omsOrderReturnApplyService.updateStatus(id, param);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

}
