package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.model.SmsHomeRecommendSubject;
import com.minko.mall.service.SmsHomeRecommendSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "首页专题推荐管理")
@RestController
@RequestMapping("/home/recommendSubject")
public class SmsHomeRecommendSubjectController {
    @Autowired
    private SmsHomeRecommendSubjectService recommendSubjectService;

    @ApiOperation("添加首页推荐专题")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody List<SmsHomeRecommendSubject> homeRecommendSubjectList) {
        int count = recommendSubjectService.create(homeRecommendSubjectList);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSort(@PathVariable Long id, Integer sort) {
        int count = recommendSubjectService.updateSort(id, sort);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = recommendSubjectService.delete(ids);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = recommendSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return Result.success(count);
        }
        return Result.failed();
    }

    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<CPage<SmsHomeRecommendSubject>> list(@RequestParam(value = "subjectName", required = false) String subjectName,
                                                       @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SmsHomeRecommendSubject> page = new Page<>(pageNum, pageSize);
        Page<SmsHomeRecommendSubject> homeRecommendSubjectList = recommendSubjectService.list(subjectName, recommendStatus, page);
        return Result.success(CPage.restPage(homeRecommendSubjectList));
    }
}
