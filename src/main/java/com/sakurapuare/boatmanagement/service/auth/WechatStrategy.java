package com.sakurapuare.boatmanagement.service.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxUserInfo;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WechatStrategy implements AuthStrategy {


    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        // String openid = wechatUtils.getOpenId(authRequestDTO.getCode());

        // Accounts account = accountsMapper.selectOneByOpenid(openid);
        // if (account == null) {
        //     account = createAccount(authRequestDTO.getUserInfo(), openid);
        //     accountsMapper.insert(account);
        // }

        // return account;
        return null;
    }

    private Accounts createAccount(WxUserInfo userInfo, String openid) {
        // return Accounts.builder()
        //         .openid(openid)
        //         .username(userInfo.getNickName())
        //         .avatar(userInfo.getAvatarUrl())
        //         .isActive(true)
        //         .isBlocked(false)
        //         .build();
        return null;
    }
} 