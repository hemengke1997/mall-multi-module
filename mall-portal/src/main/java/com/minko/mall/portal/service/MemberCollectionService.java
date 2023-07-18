package com.minko.mall.portal.service;

import com.minko.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Page;

public interface MemberCollectionService {
    /**
     * 用户收藏商品详情
     */
    MemberProductCollection detail(Long productId);

    /**
     * 添加商品收藏
     */
    int add(MemberProductCollection memberProductCollection);

    int delete(Long productId);

    Page<MemberProductCollection> list(Integer pageNum, Integer pageSize);

    void clear();
}
