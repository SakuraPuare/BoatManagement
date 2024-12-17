package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public abstract class AuthStrategy {

    private final UsersMapper userMapper;
    private AuthStatus status = null;
    private String field = null;

    public AuthStrategy(UsersMapper userMapper) {
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

    public Users auth(AuthRequestDTO authRequestDTO) {

        Users user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq(this.field, authRequestDTO.getUsername()));

        if (user == null) {
            if (status.getType().equals(AuthType.LOGIN)) {
                return null;
            }
            user = Users.builder()
                    .password(authRequestDTO.getPassword())
                    .isActive(false)
                    .isBlocked(false)
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