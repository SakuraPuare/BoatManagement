package com.sakurapuare.boatmanagement.service.impl.auth.login;

import com.sakurapuare.boatmanagement.constant.login.LoginStatus;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.CodeLoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.LoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.PasswordLoginStrategy;
import org.springframework.stereotype.Component;

@Component
public class LoginStrategyContext {

    private final CodeLoginStrategy codeLoginStrategy;

    private final PasswordLoginStrategy passwordLoginStrategy;

    public LoginStrategyContext(CodeLoginStrategy codeLoginStrategy, PasswordLoginStrategy passwordLoginStrategy) {
        this.codeLoginStrategy = codeLoginStrategy;
        this.passwordLoginStrategy = passwordLoginStrategy;
    }

    public LoginStrategy getStrategy(LoginStatus loginStatus) {
        switch (loginStatus.getMethod()) {
            case PASSWORD -> {
                passwordLoginStrategy.configureStrategy(loginStatus.getUsername());
                return passwordLoginStrategy;
            }
            case CODE -> {
                codeLoginStrategy.configureStrategy(loginStatus.getUsername());
                return codeLoginStrategy;
            }
            case THIRD_PARTY -> throw new UnsupportedOperationException("暂不支持第三方登录");
        }
        return null;
    }
}