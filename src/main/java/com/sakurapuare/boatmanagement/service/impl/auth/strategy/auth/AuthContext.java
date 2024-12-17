package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    private final UsersMapper userMapper;

    private final CodesService codeService;

    public AuthContext(CodesService codeService, UsersMapper userMapper) {
        this.codeService = codeService;
        this.userMapper = userMapper;
    }

    public AuthStrategy getStrategy(AuthStatus status) {
        switch (status.getMethod()) {
            case AuthMethod.PASSWORD -> {
                return new PasswordStrategy(
                        userMapper
                );
            }
            case AuthMethod.CODE -> {
                return new CodeStrategy(
                        codeService,
                        userMapper
                );
            }
            default -> throw new UnsupportedOperationException("暂不支持的登录方式");
        }
    }

}
