package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.CmsSubject;

public interface CmsSubjectService extends IService<CmsSubject> {
    Page<CmsSubject> selectPage(Page<CmsSubject> page, String keyword);
}
