package com.minko.mall.portal.service;

import com.minko.mall.portal.domain.MemberBrandAttention;
import org.springframework.data.domain.Page;

/**
 * 会员品牌关注管理Service
 */
public interface MemberAttentionService {
    /**
     * 获取用户关注详情
     */
    MemberBrandAttention detail(Long brandId);

    /**
     * 用户添加品牌关注
     */
    int add(MemberBrandAttention memberBrandAttention);

    /**
     * 用户取消品牌关注
     */
    int delete(Long brandId);

    /**
     * 获取用户关注的品牌列表
     */
    Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize);
    
    void clear();
}
