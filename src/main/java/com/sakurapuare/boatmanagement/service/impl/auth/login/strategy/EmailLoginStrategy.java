package com.sakurapuare.boatmanagement.service.impl.auth.login.strategy;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;

public class EmailLoginStrategy implements LoginStrategy {
    private final UserMapper userMapper;

    public EmailLoginStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getUsername();
        User user = userMapper.selectOneByQuery(QueryWrapper.create().eq("email", email));
        if (user == null) {
            return null;
        }
        return user;
    }
}