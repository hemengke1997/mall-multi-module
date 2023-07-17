package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.CmsSubject;
import com.minko.mall.service.CmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品专题管理")
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
    @Autowired
    private CmsSubjectService subjectService;

    @ApiOperation("根据专题名称分页获取商品专题")
    @GetMapping("/list")
    public Result list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<CmsSubject> page = new Page<>(pageNum, pageSize);
        Page<CmsSubject> list = subjectService.selectPage(page, keyword);
        return Result.success(CPage.restPage(list));
    }


    @ApiOperation("获取全部商品专题")
    @GetMapping("/listAll")
    public Result listAll() {
        List<CmsSubject> list = subjectService.list();
        return Result.success(list);
    }
}
