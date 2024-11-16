package com.sakurapuare.boatmanagement.service.impl.auth.strategy;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseStrategy {

    private final UserMapper userMapper;
    private AuthStatus status = null;
    private String field = null;

    public BaseStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void configureStrategy(AuthStatus status) {
        this.status = status;

        switch (status.getName()) {
            case AuthName.USERNAME:
                this.field = "username";
                break;
            case AuthName.PHONE:
                this.field = "phone";
                break;
            case AuthName.EMAIL:
                this.field = "email";
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + status.getName());
        }
    }

    public User auth(AuthRequestDTO authRequestDTO) {

        User user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq(this.field, authRequestDTO.getUsername())
        );

        if (user == null && !status.getType().equals(AuthType.REGISTER)) {
            return null;
        }

        user = new User();
        user.setUsername(authRequestDTO.getUsername());
        user.setPassword(authRequestDTO.getPassword());

        return user;
    }
}