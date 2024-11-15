package com.sakurapuare.boatmanagement.service.impl.auth.login;

import cn.hutool.core.lang.Validator;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.EmailLoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.LoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.MobileLoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.UsernameLoginStrategy;

public class LoginStrategyContext {

    private final UserMapper userMapper;

    public LoginStrategyContext(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LoginStrategy getStrategy(String username) {
        if (Validator.isEmail(username)) {
            return new EmailLoginStrategy(userMapper);
        } else if (Validator.isMobile(username)) {
            return new MobileLoginStrategy(userMapper);
        } else if (Validator.isGeneral(username, 10, 25)) {
            return new UsernameLoginStrategy(userMapper);
        } else {
            return null;
        }
    }
}