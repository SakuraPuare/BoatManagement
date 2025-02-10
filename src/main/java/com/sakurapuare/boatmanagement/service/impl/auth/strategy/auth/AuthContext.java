package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthContext {

    private final PasswordStrategy passwordStrategy;
    private final CaptchaStrategy captchaStrategy;
    private final WechatStrategy wechatStrategy;
    private AuthStrategy strategy;

    public void setStrategy(AuthStatus status) {
        switch (status.getMethod()) {
            case AuthMethod.PASSWORD -> strategy = passwordStrategy;
            case AuthMethod.CODE -> strategy = captchaStrategy;
            case AuthMethod.WECHAT -> strategy = wechatStrategy;
            default -> throw new UnsupportedOperationException("暂不支持的登录方式");
        }
    }

    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        return strategy.auth(status, authRequestDTO);
    }
}
