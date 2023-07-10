package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.CmsSubjectMapper;
import com.minko.mall.model.CmsSubject;
import com.minko.mall.service.CmsSubjectService;
import org.springframework.stereotype.Service;

@Service
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements CmsSubjectService {
}
