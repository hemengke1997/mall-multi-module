package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.UmsResourceCategory;

import java.util.List;

public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {
    List<UmsResourceCategory> orderList();
}
