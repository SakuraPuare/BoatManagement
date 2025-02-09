package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.service.CapthaService;
import com.sakurapuare.boatmanagement.utils.WechatUtils;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthContext {

    private final AccountsMapper userMapper;

    private final CapthaService capthaService;

    private final WechatUtils wechatUtils;

    public AuthStrategy getStrategy(AuthStatus status) {
        switch (status.getMethod()) {
            case AuthMethod.PASSWORD -> {
                return new PasswordStrategy(
                        userMapper
                );
            }
            case AuthMethod.CODE -> {
                return new CaptchaStrategy(
                        capthaService,
                        userMapper
                );
            }
            case AuthMethod.WECHAT -> {
                return new WechatStrategy(wechatUtils, userMapper);
            }
            default -> throw new UnsupportedOperationException("暂不支持的登录方式");
        }
    }

}
