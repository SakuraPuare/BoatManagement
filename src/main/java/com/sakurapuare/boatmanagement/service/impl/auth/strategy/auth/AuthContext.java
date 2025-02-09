package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.CapthaService;
import com.sakurapuare.boatmanagement.utils.WechatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthContext {

    private final CapthaService capthaService;
    private final AccountsMapper userMapper;
    private final WechatUtils wechatUtils;
    private AuthStrategy strategy;

    public void setStrategy(AuthStatus status) {
        switch (status.getMethod()) {
            case AuthMethod.PASSWORD -> strategy = new PasswordStrategy(userMapper);
            case AuthMethod.CODE -> strategy = new CaptchaStrategy(capthaService, userMapper);
            case AuthMethod.WECHAT -> strategy = new WechatStrategy(wechatUtils, userMapper);
            default -> throw new UnsupportedOperationException("暂不支持的登录方式");
        }
    }

    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        return strategy.auth(status, authRequestDTO);
    }
}
