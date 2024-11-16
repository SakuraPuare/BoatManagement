package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.CodeService;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.AuthContext;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.BaseStrategy;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final UserMapper userMapper;

    private final CodeService codeService;

    public AuthServiceImpl(CodeService codeService, UserMapper userMapper) {
        this.codeService = codeService;
        this.userMapper = userMapper;
    }

    private User authContext(AuthStatus status, AuthRequestDTO authRequestDTO) {

        AuthContext context = new AuthContext(codeService, userMapper);
        BaseStrategy strategy = context.getStrategy(status);

        return strategy.auth(authRequestDTO);
    }

    @Override
    public User loginWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public User loginWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public User registerWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public User registerWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

}
