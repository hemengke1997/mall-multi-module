package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.portal.model.UmsMember;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService extends IService<UmsMember> {
    @Transactional
    void register(String username, String password, String telephone, String authCode);
}
