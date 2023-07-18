package com.minko.mall.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService extends IService<UmsMember> {
    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    @Transactional
    void register(String username, String password, String telephone, String authCode);

    String login(String username, String password);

    UserDetails loadUserByUsername(String username);

    UmsMember getCurrentMember();

    String generateAuthCode(String telephone);

    /**
     * 会员修改密码
     */
    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    /**
     * 刷新token
     */
    String refreshToken(String token);

    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long memberId, Integer integration);
}
