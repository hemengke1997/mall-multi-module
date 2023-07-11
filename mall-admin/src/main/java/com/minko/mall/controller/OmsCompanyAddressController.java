package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.OmsCompanyAddress;
import com.minko.mall.service.OmsCompanyAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "收货地址管理")
@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {
    @Autowired
    private OmsCompanyAddressService omsCompanyAddressService;

    @ApiOperation("获取所有收货地址")
    @GetMapping("/list")
    public Result list() {
        List<OmsCompanyAddress> list = omsCompanyAddressService.list();
        return Result.success(list);
    }
}
