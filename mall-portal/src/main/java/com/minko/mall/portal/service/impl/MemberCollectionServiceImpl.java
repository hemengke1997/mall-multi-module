package com.minko.mall.portal.service.impl;

import com.minko.mall.mapper.PmsProductMapper;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.domain.MemberProductCollection;
import com.minko.mall.portal.repository.MemberProductCollectionRepository;
import com.minko.mall.portal.service.MemberCollectionService;
import com.minko.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
    @Value("${mongo.insert.sqlEnable}")
    private Boolean sqlEnable;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private MemberProductCollectionRepository productCollectionRepository;
    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public MemberProductCollection detail(Long productId) {
        UmsMember member = memberService.getCurrentMember();
        return productCollectionRepository.findByMemberIdAndProductId(member.getId(), productId);
    }

    @Override
    public int add(MemberProductCollection productCollection) {
        if (productCollection.getProductId() == null) {
            return 0;
        }
        UmsMember member = memberService.getCurrentMember();
        productCollection.setMemberId(member.getId());
        productCollection.setMemberNickname(member.getNickname());
        productCollection.setMemberIcon(member.getIcon());
        MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
        if (findCollection == null) {
            if (sqlEnable) {
                PmsProduct product = productMapper.selectById(productCollection.getProductId());
                if (product == null || product.getDeleteStatus() == 1) {
                    return 0;
                }
                productCollection.setProductName(product.getName());
                productCollection.setProductSubTitle(product.getSubTitle());
                productCollection.setProductPrice(String.valueOf(product.getPrice()));
                productCollection.setProductPic(product.getPic());
            }
            productCollectionRepository.save(productCollection);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Long productId) {
        UmsMember member = memberService.getCurrentMember();
        int i = productCollectionRepository.deleteByMemberIdAndProductId(member.getId(), productId);
        return i;
    }

    @Override
    public Page<MemberProductCollection> list(Integer pageNum, Integer pageSize) {
        UmsMember member = memberService.getCurrentMember();
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return productCollectionRepository.findByMemberId(member.getId(), pageRequest);
    }

    @Override
    public void clear() {
        UmsMember member = memberService.getCurrentMember();
        productCollectionRepository.deleteAllByMemberId(member.getId());
    }
}
