package com.minko.mall.portal.repository;

import com.minko.mall.portal.domain.MemberReadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {

    Page<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId, Pageable pageable);

    void deleteAllByMemberId(Long memberId);
}
