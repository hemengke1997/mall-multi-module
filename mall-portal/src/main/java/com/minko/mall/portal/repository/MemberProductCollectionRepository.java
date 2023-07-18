package com.minko.mall.portal.repository;

import com.minko.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberProductCollectionRepository extends MongoRepository<MemberProductCollection, String> {
    /**
     * 根据会员ID和商品ID查找记录
     */
    MemberProductCollection findByMemberIdAndProductId(Long memberId, Long productId);

    int deleteByMemberIdAndProductId(Long memberId, Long productId);

    Page<MemberProductCollection> findByMemberId(Long id, PageRequest pageRequest);

    void deleteAllByMemberId(Long id);
}
