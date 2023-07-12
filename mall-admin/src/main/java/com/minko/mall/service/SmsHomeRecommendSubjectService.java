package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.SmsHomeRecommendSubject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SmsHomeRecommendSubjectService extends IService<SmsHomeRecommendSubject> {
    @Transactional
    int create(List<SmsHomeRecommendSubject> homeRecommendSubjectList);

    int updateSort(Long id, Integer sort);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Page<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Page<SmsHomeRecommendSubject> page);
}
