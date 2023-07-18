package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.UmsMemberReceiveAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {
    List<UmsMemberReceiveAddress> getList();

    int add(UmsMemberReceiveAddress address);

    UmsMemberReceiveAddress getItem(Long id);


    /**
     * 修改收货地址
     */
    @Transactional
    int update(Long id, UmsMemberReceiveAddress address);

    /**
     * 删除收货地址
     */
    int delete(Long id);
}
