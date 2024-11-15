package com.sakurapuare.boatmanagement.service.impl.auth.login.strategy;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.login.Username;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordLoginStrategy implements LoginStrategy {

    private final UserMapper userMapper;
    private String field;


    @Autowired
    public PasswordLoginStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void configureStrategy(Username username) {
        switch (username) {
            case USERNAME:
                this.field = "username";
                break;
            case EMAIL:
                this.field = "email";
                break;
            case PHONE:
                this.field = "phone";
                break;
            default:
                this.field = null;
        }
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();

        if (field == null) {
            return null;
        }

        User user = userMapper.selectOneByQuery(QueryWrapper.create().eq(field, username));

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            return null;
        }

        return user;
    }
}


