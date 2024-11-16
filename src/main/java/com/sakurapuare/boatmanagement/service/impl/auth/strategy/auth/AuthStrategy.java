package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import org.springframework.stereotype.Component;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.UserRole;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;

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
                QueryWrapper.create().eq(this.field, authRequestDTO.getUsername()));

        if (user == null) {
            if (status.getType().equals(AuthType.LOGIN)) {
                return null;
            }
            user = new User();

            AuthName name = AuthNameUtils.getAuthName(authRequestDTO.getUsername());
            switch (name) {
                case USERNAME -> user.setUsername(authRequestDTO.getUsername());
                case PHONE -> user.setPhone(authRequestDTO.getUsername());
                case EMAIL -> user.setEmail(authRequestDTO.getUsername());
                default -> user.setUsername(authRequestDTO.getUsername());
            }

            user.setPassword(authRequestDTO.getPassword());
            user.setRole(UserRole.USER);
            user.setIsBlocked(false);
            user.setIsActive(false);
            user.setIsDeleted(false);

            userMapper.insert(user);
        } else if (status.getType().equals(AuthType.REGISTER)) {
            return null;
        }

        return user;

    }
}