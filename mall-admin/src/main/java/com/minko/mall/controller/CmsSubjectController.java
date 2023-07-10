package com.minko.mall.controller;

import com.minko.mall.common.api.Result;
import com.minko.mall.model.CmsSubject;
import com.minko.mall.service.CmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品专题管理")
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
    @Autowired
    private CmsSubjectService cmsSubjectService;

    @ApiOperation("获取全部商品专题")
    @GetMapping("/listAll")
    public Result listAll() {
        List<CmsSubject> list = cmsSubjectService.list();
        return Result.success(list);
    }
}
