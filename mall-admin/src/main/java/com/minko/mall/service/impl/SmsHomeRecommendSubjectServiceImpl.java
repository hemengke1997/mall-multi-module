package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.SmsHomeRecommendSubjectMapper;
import com.minko.mall.model.SmsHomeRecommendSubject;
import com.minko.mall.service.SmsHomeRecommendSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeRecommendSubjectServiceImpl extends ServiceImpl<SmsHomeRecommendSubjectMapper, SmsHomeRecommendSubject> implements SmsHomeRecommendSubjectService {
    @Autowired
    private SmsHomeRecommendSubjectMapper recommendSubjectMapper;

    @Override
    public int create(List<SmsHomeRecommendSubject> homeRecommendSubjectList) {
        for (SmsHomeRecommendSubject recommendSubject : homeRecommendSubjectList) {
            recommendSubject.setRecommendStatus(1);
            recommendSubject.setSort(0);
            recommendSubjectMapper.insert(recommendSubject);
        }
        return homeRecommendSubjectList.size();
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setSort(sort);
        subject.setId(id);
        return recommendSubjectMapper.updateById(subject);
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<SmsHomeRecommendSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeRecommendSubject::getId, ids);
        return recommendSubjectMapper.delete(queryWrapper);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<SmsHomeRecommendSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SmsHomeRecommendSubject::getId, ids);
        SmsHomeRecommendSubject subject = new SmsHomeRecommendSubject();
        subject.setRecommendStatus(recommendStatus);
        return recommendSubjectMapper.update(subject, queryWrapper);
    }

    @Override
    public Page<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Page<SmsHomeRecommendSubject> page) {
        LambdaQueryWrapper<SmsHomeRecommendSubject> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(subjectName)) {
            queryWrapper.like(SmsHomeRecommendSubject::getSubjectName, subjectName);
        }
        if (recommendStatus != null) {
            queryWrapper.eq(SmsHomeRecommendSubject::getRecommendStatus, recommendStatus);
        }
        return recommendSubjectMapper.selectPage(page, queryWrapper);
    }
}
