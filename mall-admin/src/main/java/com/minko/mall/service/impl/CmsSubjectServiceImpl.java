package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.CmsSubjectMapper;
import com.minko.mall.model.CmsSubject;
import com.minko.mall.service.CmsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements CmsSubjectService {
    @Autowired
    private CmsSubjectMapper subjectMapper;

    @Override
    public Page<CmsSubject> selectPage(Page<CmsSubject> page, String keyword) {
        LambdaQueryWrapper<CmsSubject> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.like(CmsSubject::getTitle, keyword);
        }
        return subjectMapper.selectPage(page, queryWrapper);
    }
}
