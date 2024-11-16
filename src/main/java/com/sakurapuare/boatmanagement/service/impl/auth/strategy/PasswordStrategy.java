package com.sakurapuare.boatmanagement.service.impl.auth.strategy;

import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;

public class PasswordStrategy extends BaseStrategy {

    public PasswordStrategy(UserMapper userMapper) {
        super(userMapper);
    }

    @Override
    public User auth(AuthRequestDTO authRequestDTO) {
        User user = super.auth(authRequestDTO);

        if (user == null) {
            return null;
        }

        // check password validity
        if (user.getPassword().equals(authRequestDTO.getPassword())) {
            return user;
        }

        return null;
    }

}
