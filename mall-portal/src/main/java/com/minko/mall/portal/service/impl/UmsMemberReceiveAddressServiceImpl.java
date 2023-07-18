package com.minko.mall.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.mapper.UmsMemberReceiveAddressMapper;
import com.minko.mall.model.UmsMember;
import com.minko.mall.model.UmsMemberReceiveAddress;
import com.minko.mall.portal.service.UmsMemberReceiveAddressService;
import com.minko.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;

    @Override
    public List<UmsMemberReceiveAddress> getList() {
        UmsMember member = memberService.getCurrentMember();
        LambdaQueryWrapper<UmsMemberReceiveAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMemberReceiveAddress::getMemberId, member.getId());
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public int add(UmsMemberReceiveAddress address) {
        UmsMember currentMember = memberService.getCurrentMember();
        address.setMemberId(currentMember.getId());
        return addressMapper.insert(address);
    }

    @Override
    public UmsMemberReceiveAddress getItem(Long id) {
        UmsMember currentMember = memberService.getCurrentMember();
        LambdaQueryWrapper<UmsMemberReceiveAddress> addressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressLambdaQueryWrapper
                .eq(UmsMemberReceiveAddress::getMemberId, currentMember.getId())
                .eq(UmsMemberReceiveAddress::getId, id);
        List<UmsMemberReceiveAddress> addressList = addressMapper.selectList(addressLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(addressList)) {
            return addressList.get(0);
        }
        return null;
    }

    @Override
    public int update(Long id, UmsMemberReceiveAddress address) {
        address.setId(null);
        UmsMember member = memberService.getCurrentMember();
        LambdaQueryWrapper<UmsMemberReceiveAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMemberReceiveAddress::getMemberId, member.getId()).eq(UmsMemberReceiveAddress::getId, id);
        if (address.getDefaultStatus() == 1) {
            UmsMemberReceiveAddress record = new UmsMemberReceiveAddress();
            record.setDefaultStatus(0);
            LambdaQueryWrapper<UmsMemberReceiveAddress> addressLambdaQueryWrapper = new LambdaQueryWrapper<>();
            addressLambdaQueryWrapper.eq(UmsMemberReceiveAddress::getMemberId, member.getId())
                    .eq(UmsMemberReceiveAddress::getDefaultStatus, 1);
            addressMapper.update(record, addressLambdaQueryWrapper);
        }
        return addressMapper.update(address, queryWrapper);
    }

    @Override
    public int delete(Long id) {
        UmsMember member = memberService.getCurrentMember();
        LambdaQueryWrapper<UmsMemberReceiveAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsMemberReceiveAddress::getId, id).eq(UmsMemberReceiveAddress::getMemberId, member.getId());
        return addressMapper.delete(queryWrapper);
    }
}
