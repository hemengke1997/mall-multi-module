package com.minko.mall.portal.service;

import com.minko.mall.portal.domain.MemberReadHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberReadHistoryService {
    int create(MemberReadHistory memberReadHistory);

    Page<MemberReadHistory> list(Integer pageNum, Integer pageSize);

    void clear();

    int delete(List<String> ids);
}
