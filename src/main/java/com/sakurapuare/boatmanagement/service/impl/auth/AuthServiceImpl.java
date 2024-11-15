package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.login.AuthMethod;
import com.sakurapuare.boatmanagement.constant.login.LoginStatus;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.impl.auth.login.LoginStrategyContext;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.CodeLoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.LoginStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.PasswordLoginStrategy;
import com.sakurapuare.boatmanagement.utils.LoginTypeUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final CodeLoginStrategy codeLoginStrategy;

    private final PasswordLoginStrategy passwordLoginStrategy;

    public AuthServiceImpl(CodeLoginStrategy codeLoginStrategy, PasswordLoginStrategy passwordLoginStrategy) {
        this.codeLoginStrategy = codeLoginStrategy;
        this.passwordLoginStrategy = passwordLoginStrategy;
    }

    private User loginContext(AuthMethod method, LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();

        LoginStatus loginStatus = LoginTypeUtils.getLoginType(username);
        loginStatus.setMethod(method);
        LoginStrategyContext context = new LoginStrategyContext(codeLoginStrategy, passwordLoginStrategy);
        LoginStrategy strategy = context.getStrategy(loginStatus);

        if (strategy == null) {
            return null;
        }

        return strategy.login(loginRequestDTO);
    }

    @Override
    public User loginWithPassword(LoginRequestDTO loginRequestDTO) {
        return loginContext(AuthMethod.PASSWORD, loginRequestDTO);
    }

    @Override
    public User loginWithCode(LoginRequestDTO loginRequestDTO) {
        return loginContext(AuthMethod.CODE, loginRequestDTO);
    }

}
