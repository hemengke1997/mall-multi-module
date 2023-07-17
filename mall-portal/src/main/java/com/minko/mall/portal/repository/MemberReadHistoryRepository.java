package com.minko.mall.portal.repository;

import com.minko.mall.portal.domain.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
}
