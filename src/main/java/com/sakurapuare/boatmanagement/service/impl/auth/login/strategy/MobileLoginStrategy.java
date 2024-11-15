package com.sakurapuare.boatmanagement.service.impl.auth.login.strategy;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;

public class MobileLoginStrategy implements LoginStrategy {
    private final UserMapper userMapper;

    public MobileLoginStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        String mobile = loginRequestDTO.getUsername();
        User user = userMapper.selectOneByQuery(QueryWrapper.create().eq("phone", mobile));
        if (user == null) {
            return null;
        }
        return user;
    }
}