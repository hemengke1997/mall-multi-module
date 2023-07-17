package com.minko.mall.portal.service.impl;

import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.domain.MemberProductCollection;
import com.minko.mall.portal.repository.MemberProductCollectionRepository;
import com.minko.mall.portal.service.MemberCollectionService;
import com.minko.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private MemberProductCollectionRepository productCollectionRepository;

    @Override
    public MemberProductCollection detail(Long productId) {
        UmsMember member = memberService.getCurrentMember();
        return productCollectionRepository.findByMemberIdAndProductId(member.getId(), productId);
    }
}
