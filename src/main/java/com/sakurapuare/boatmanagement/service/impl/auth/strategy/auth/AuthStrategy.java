package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public abstract class AuthStrategy {

    private final AccountsMapper accountsMapper;
    private AuthStatus status = null;
    private String field = null;

    public AuthStrategy(AccountsMapper accountsMapper) {
        this.accountsMapper = accountsMapper;
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

    public Accounts auth(AuthRequestDTO authRequestDTO) {
        Accounts account = accountsMapper.selectOneByQuery(
                QueryWrapper.create().eq(this.field, authRequestDTO.getUsername()));

        if (account == null) {
            if (status.getType().equals(AuthType.LOGIN)) {
                return null;
            }
            account = Accounts.builder()
                    .password(authRequestDTO.getPassword())
                    .isActive(false)
                    .isBlocked(false)
                    .build();

            AuthName name = AuthNameUtils.getAuthName(authRequestDTO.getUsername());
            switch (name) {
                case USERNAME -> account.setUsername(authRequestDTO.getUsername());
                case PHONE -> account.setMobile(authRequestDTO.getUsername());
                case EMAIL -> account.setEmail(authRequestDTO.getUsername());
                default -> account.setUsername(authRequestDTO.getUsername());
            }

            accountsMapper.insertSelective(account);
        } else if (status.getType().equals(AuthType.REGISTER)) {
            return null;
        }

        return account;
    }
}