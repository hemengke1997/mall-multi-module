package com.minko.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.common.api.CPage;
import com.minko.mall.common.api.Result;
import com.minko.mall.dto.PmsProductAttributeParam;
import com.minko.mall.dto.ProductAttrInfo;
import com.minko.mall.model.PmsProductAttribute;
import com.minko.mall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品属性管理")
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;

    @ApiOperation("新增商品属性")
    @PostMapping("/create")
    public Result create(@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = pmsProductAttributeService.create(productAttributeParam);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
    @GetMapping("/list/{cid}")
    public Result list(@PathVariable Long cid,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "type") Integer type) {
        Page<PmsProductAttribute> page = new Page<>(pageNum, pageSize);
        Page<PmsProductAttribute> list = pmsProductAttributeService.selectPage(cid, type, page);
        return Result.success(CPage.restPage(list));
    }

    @ApiOperation("获取单个商品属性")
    @GetMapping("/{id}")
    public Result getItem(@PathVariable Long id) {
        PmsProductAttribute byId = pmsProductAttributeService.getById(id);
        return Result.success(byId);
    }

    @ApiOperation("修改商品属性")
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Long id,
                         @RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = pmsProductAttributeService.updateItem(id, productAttributeParam);
        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }

    @ApiOperation("删除商品属性")
    @PostMapping("/delete")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        int count = pmsProductAttributeService.delete(ids);

        if (count > 0) {
            return Result.success(1);
        }
        return Result.failed();
    }


    @ApiOperation("根据商品分类的id获取商品属性及属性分类")
    @GetMapping("/attrInfo/{productCategoryId}")
    public Result getAttrInfo(@PathVariable Long productCategoryId) {
        List<ProductAttrInfo> productAttrInfoList = pmsProductAttributeService.getProductAttrInfo(productCategoryId);
        return Result.success(productAttrInfoList);
    }
}
