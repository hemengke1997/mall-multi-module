package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsHomeAdvertise;
import com.minko.mall.service.SmsHomeAdvertiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "首页广告管理")
@RestController
@RequestMapping("/home/advertise")
public class SmsHomeAdvertiseController {
    @Autowired
    private SmsHomeAdvertiseService advertiseService;

    @ApiOperation("添加广告")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody SmsHomeAdvertise advertise) {
        int count = advertiseService.create(advertise);
        if (count > 0)
            return Result.success(count);
        return Result.failed();
    }

    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = advertiseService.delete(ids);
        if (count > 0)
            return Result.success(count);
        return Result.failed();
    }

    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStatus(@PathVariable Long id, Integer status) {
        int count = advertiseService.updateStatus(id, status);
        if (count > 0)
            return Result.success(count);
        return Result.failed();
    }

    @ApiOperation("获取广告详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<SmsHomeAdvertise> getItem(@PathVariable Long id) {
        SmsHomeAdvertise advertise = advertiseService.getItem(id);
        return Result.success(advertise);
    }

    @ApiOperation("修改广告")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@PathVariable Long id, @RequestBody SmsHomeAdvertise advertise) {
        int count = advertiseService.update(id, advertise);
        if (count > 0)
            return Result.success(count);
        return Result.failed();
    }

    @ApiOperation("分页查询广告")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<CPage<SmsHomeAdvertise>> list(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "type", required = false) Integer type,
                                                @RequestParam(value = "endTime", required = false) String endTime,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsHomeAdvertise> page = new Page<>(pageNum, pageSize);
        Page<SmsHomeAdvertise> advertiseList = advertiseService.list(name, type, endTime, page);
        return Result.success(CPage.restPage(advertiseList));
    }
}
