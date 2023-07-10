package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.OmsOrderDetail;
import com.minko.mall.dto.OmsOrderQueryParam;
import com.minko.mall.dto.OmsReceiverInfoParam;
import com.minko.mall.model.OmsOrder;
import com.minko.mall.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;

    @ApiOperation("分页获取订单列表")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       OmsOrderQueryParam omsOrderQueryParam) {
        Page<OmsOrder> orderPage = new Page<>(pageNum, pageSize);
        Page<OmsOrder> list = omsOrderService.selectPage(orderPage, omsOrderQueryParam);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("查看订单详情")
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Long id) {
        OmsOrderDetail orderDetail = omsOrderService.getInfo(id);
        return Result.success(orderDetail);
    }

    @ApiOperation("添加订单备注")
    @PostMapping("/update/note")
    public Result updateNote(@RequestParam("id") Long id,
                             @RequestParam("note") String note,
                             @RequestParam("status") Integer status) {
        int count = omsOrderService.updateNote(id, note, status);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("删除订单")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = omsOrderService.delete(ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("修改订单收货信息")
    @PostMapping("/update/receiverInfo")
    public Result updateReceiverInfo(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
        int count = omsOrderService.updateReceiverInfo(receiverInfoParam);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }
}
