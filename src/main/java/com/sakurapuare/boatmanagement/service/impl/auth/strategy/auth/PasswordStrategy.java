package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;

public class PasswordStrategy extends AuthStrategy {

    public PasswordStrategy(UsersMapper userMapper) {
        super(userMapper);
    }

    @Override
    public Users auth(AuthRequestDTO authRequestDTO) {
        Users users = super.auth(authRequestDTO);

        if (users == null) {
            return null;
        }

        // check password validity
        if (users.getPassword().equals(authRequestDTO.getPassword())) {
            return users;
        }

        return null;
    }

}
