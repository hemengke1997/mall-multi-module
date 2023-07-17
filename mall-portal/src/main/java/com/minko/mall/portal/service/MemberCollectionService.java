package com.minko.mall.portal.service;

import com.minko.mall.portal.domain.MemberProductCollection;

public interface MemberCollectionService {
    /**
     * 用户收藏商品详情
     */
    MemberProductCollection detail(Long productId);
}
