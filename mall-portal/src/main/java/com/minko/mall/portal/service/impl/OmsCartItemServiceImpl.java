package com.minko.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.OmsCartItemMapper;
import com.minko.mall.model.OmsCartItem;
import com.minko.mall.model.UmsMember;
import com.minko.mall.portal.domain.CartPromotionItem;
import com.minko.mall.portal.service.OmsCartItemService;
import com.minko.mall.portal.service.OmsPromotionService;
import com.minko.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private OmsCartItemMapper cartItemMapper;
    @Autowired
    private OmsPromotionService promotionService;

    @Override
    public int add(OmsCartItem cartItem) {
        UmsMember currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            return cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            return cartItemMapper.updateById(existCartItem);
        }
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        LambdaQueryWrapper<OmsCartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OmsCartItem::getDeleteStatus, 0).eq(OmsCartItem::getMemberId, memberId);
        return cartItemMapper.selectList(queryWrapper);
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        LambdaQueryWrapper<OmsCartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OmsCartItem::getDeleteStatus, 0)
                .eq(OmsCartItem::getId, id)
                .eq(OmsCartItem::getMemberId, memberId);
        return cartItemMapper.update(cartItem, queryWrapper);
    }

    @Override
    public int clear() {
        UmsMember member = memberService.getCurrentMember();
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setDeleteStatus(1);
        LambdaQueryWrapper<OmsCartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OmsCartItem::getMemberId, member.getId());
        return cartItemMapper.update(cartItem, queryWrapper);
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        List<OmsCartItem> cartItemList = list(memberId);
        if (CollUtil.isNotEmpty(cartIds)) {
            cartItemList = cartItemList.stream()
                    .filter(item -> cartIds.contains(item.getId()))
                    .collect(Collectors.toList());
        }
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if (CollUtil.isNotEmpty(cartItemList)) {
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        LambdaQueryWrapper<OmsCartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OmsCartItem::getId, ids).eq(OmsCartItem::getMemberId, memberId);
        return cartItemMapper.update(record, queryWrapper);
    }


    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        LambdaQueryWrapper<OmsCartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OmsCartItem::getMemberId, cartItem.getMemberId())
                .eq(OmsCartItem::getProductId, cartItem.getProductId())
                .eq(OmsCartItem::getDeleteStatus, 0);

        if (cartItem.getProductSkuId() != null) {
            queryWrapper.eq(OmsCartItem::getProductSkuId, cartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }
}
