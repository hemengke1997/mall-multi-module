package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.CmsPrefrenceArea;
import com.minko.mall.service.CmsPrefrenceAreaSerivice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品优选
 */
@Api(tags = "商品优选管理")
@RestController
@RequestMapping("/prefrenceArea")
public class CmsPrefrenceAreaController {
    @Autowired
    private CmsPrefrenceAreaSerivice cmsPrefrenceAreaSerivice;

    @ApiOperation("获取所有商品优选")
    @GetMapping("/listAll")
    public Result listAll() {
        List<CmsPrefrenceArea> list = cmsPrefrenceAreaSerivice.list();
        return Result.success(list);
    }
}
