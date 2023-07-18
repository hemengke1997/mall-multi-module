package com.minko.mall.portal.controller;

import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.portal.domain.ConfirmOrderResult;
import com.minko.mall.portal.domain.OmsOrderDetail;
import com.minko.mall.portal.domain.OrderParam;
import com.minko.mall.portal.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "会员订单管理")
@RestController
@RequestMapping("/order")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderService portalOrderService;

    @ApiOperation("根据购物车信息生成确认单")
    @PostMapping("/generateConfirmOrder")
    public Result generateConfirmOrder(@RequestBody List<Long> cartIds) {
        ConfirmOrderResult confirmOrderResult = portalOrderService.generateConfirmOrder(cartIds);
        return Result.success(confirmOrderResult);
    }


    @ApiOperation("根据购物车信息生成订单")
    @PostMapping("/generateOrder")
    public Result generateOrder(@RequestBody OrderParam orderParam) {
        Map<String, Object> result = portalOrderService.generateOrder(orderParam);
        return Result.success(result, "下单成功");
    }

    @ApiOperation("用户支付成功回调")
    @PostMapping("/paySuccess")
    public Result paySuccess(@RequestParam Long orderId, @RequestParam Integer payType) {
        int count = portalOrderService.paySuccess(orderId, payType);
        return Result.success(count, "支付成功");
    }

    @ApiOperation("自动取消超时订单")
    @PostMapping("/cancelTimeOutOrder")
    public Result cancelTimeOutOrder() {
        portalOrderService.cancelTimeOutOrder();
        return Result.success(null);
    }

    @ApiOperation("取消单个超时订单")
    @PostMapping("/cancelOrder")
    public Result cancelOrder(@RequestParam Long orderId) {
        portalOrderService.sendDelayMessageCancelOrder(orderId);
        return Result.success(null);
    }

    @ApiOperation("按状态分页获取用户订单列表")
    @ApiImplicitParam(name = "status", value = "订单状态：-1->全部；0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭",
            defaultValue = "-1", allowableValues = "-1,0,1,2,3,4", paramType = "query", dataType = "int")
    @GetMapping("/list")
    public Result<CPage<OmsOrderDetail>> list(@RequestParam Integer status,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        CPage<OmsOrderDetail> orderPage = portalOrderService.list(status, pageNum, pageSize);
        return Result.success(orderPage);
    }

    @ApiOperation("根据ID获取订单详情")
    @GetMapping("/detail/{orderId}")
    public Result<OmsOrderDetail> detail(@PathVariable Long orderId) {
        OmsOrderDetail orderDetail = portalOrderService.detail(orderId);
        return Result.success(orderDetail);
    }

    @ApiOperation("用户取消订单")
    @PostMapping("/cancelUserOrder")
    public Result cancelUserOrder(Long orderId) {
        portalOrderService.cancelOrder(orderId);
        return Result.success(1);
    }

    @ApiOperation("用户确认收货")
    @PostMapping("/confirmReceiveOrder")
    public Result confirmReceiveOrder(Long orderId) {
        portalOrderService.confirmReceiveOrder(orderId);
        return Result.success(1);
    }

    @ApiOperation("用户删除订单")
    @PostMapping("/deleteOrder")
    public Result deleteOrder(Long orderId) {
        portalOrderService.deleteOrder(orderId);
        return Result.success(1);
    }
}
