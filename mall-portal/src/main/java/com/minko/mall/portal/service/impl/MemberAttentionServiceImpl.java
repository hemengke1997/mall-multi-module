package com.minko.mall.portal.service.impl;

import com.minko.mall.mapper.PmsBrandMapper;
import com.minko.mall.model.PmsBrand;
import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.domain.MemberBrandAttention;
import com.minko.mall.portal.repository.MemberBrandAttentionRepository;
import com.minko.mall.portal.service.MemberAttentionService;
import com.minko.mall.portal.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MemberAttentionServiceImpl implements MemberAttentionService {
    @Value("${mongo.insert.sqlEnable}")
    private Boolean sqlEnable;

    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private MemberBrandAttentionRepository memberBrandAttentionRepository;

    @Override
    public MemberBrandAttention detail(Long brandId) {
        UmsMember member = memberService.getCurrentMember();
        return memberBrandAttentionRepository.findByMemberIdAndBrandId(member.getId(), brandId);
    }

    @Override
    public int add(MemberBrandAttention memberBrandAttention) {
        int count = 0;
        if (memberBrandAttention.getBrandId() == null) {
            return 0;
        }
        UmsMember member = memberService.getCurrentMember();
        memberBrandAttention.setMemberId(member.getId());
        memberBrandAttention.setMemberNickname(member.getNickname());
        memberBrandAttention.setMemberIcon(member.getIcon());
        memberBrandAttention.setCreateTime(new Date());

        MemberBrandAttention findAttention = memberBrandAttentionRepository.findByMemberIdAndBrandId(memberBrandAttention.getMemberId(), memberBrandAttention.getBrandId());
        log.info("mongo find attention:{}", findAttention);
        if (findAttention == null) {
            if (sqlEnable) {
                PmsBrand brand = brandMapper.selectById(memberBrandAttention.getBrandId());
                if (brand == null) {
                    return 0;
                } else {
                    memberBrandAttention.setBrandCity(null);
                    memberBrandAttention.setBrandLogo(brand.getLogo());
                    memberBrandAttention.setBrandName(brand.getName());
                }

            }
            memberBrandAttentionRepository.save(memberBrandAttention);
            count = 1;
        }
        return count;
    }

    @Override
    public Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize) {
        UmsMember member = memberService.getCurrentMember();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return memberBrandAttentionRepository.findByMemberId(member.getId(), pageable);
    }

    @Override
    public void clear() {
        UmsMember member = memberService.getCurrentMember();
        memberBrandAttentionRepository.deleteAllByMemberId(member.getId());
    }

    @Override
    public int delete(Long brandId) {
        UmsMember member = memberService.getCurrentMember();
        return memberBrandAttentionRepository.deleteByMemberIdAndBrandId(member.getId(), brandId);
    }
}
