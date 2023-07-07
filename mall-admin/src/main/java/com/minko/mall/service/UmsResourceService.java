package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.UmsResource;

public interface UmsResourceService extends IService<UmsResource> {
    Page<UmsResource> selectPage(Page<UmsResource> page, Long categoryId, String nameKeyword, String urlKeyword);


    int update(UmsResource umsResource);

    int remove(Long id);
}
