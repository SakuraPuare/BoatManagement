package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public abstract class AuthStrategy {

    private final UserMapper userMapper;
    private AuthStatus status = null;
    private String field = null;

    public AuthStrategy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void configureStrategy(AuthStatus status) {
        this.status = status;

        switch (status.getName()) {
            case AuthName.USERNAME:
                this.field = TableName.USER_USERNAME;
                break;
            case AuthName.PHONE:
                this.field = TableName.USER_PHONE;
                break;
            case AuthName.EMAIL:
                this.field = TableName.USER_EMAIL;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + status.getName());
        }
    }

    public User auth(AuthRequestDTO authRequestDTO) {

        User user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq(this.field, authRequestDTO.getUsername()));

        if (user == null) {
            if (status.getType().equals(AuthType.LOGIN)) {
                return null;
            }
            user = User.builder()
                    .password(authRequestDTO.getPassword())
                    .build();

            AuthName name = AuthNameUtils.getAuthName(authRequestDTO.getUsername());
            switch (name) {
                case USERNAME -> user.setUsername(authRequestDTO.getUsername());
                case PHONE -> user.setPhone(authRequestDTO.getUsername());
                case EMAIL -> user.setEmail(authRequestDTO.getUsername());
                default -> user.setUsername(authRequestDTO.getUsername());
            }

            userMapper.insertSelective(user);
        } else if (status.getType().equals(AuthType.REGISTER)) {
            return null;
        }

        return user;

    }
}