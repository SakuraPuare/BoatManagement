package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.impl.auth.login.LoginStrategyContext;
import com.sakurapuare.boatmanagement.service.impl.auth.login.strategy.LoginStrategy;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final UserMapper userMapper;

    public AuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();

        LoginStrategyContext context = new LoginStrategyContext(userMapper);
        LoginStrategy strategy = context.getStrategy(username);

        if (strategy == null) {
            return null;
        }

        return strategy.login(loginRequestDTO);
    }

}
