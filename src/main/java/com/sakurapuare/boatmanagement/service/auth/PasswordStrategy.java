package com.sakurapuare.boatmanagement.service.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ACCOUNTS;

@Component
@RequiredArgsConstructor
public class PasswordStrategy implements AuthStrategy {

    private final AccountsService accountsService;

    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        String field = AuthStrategy.getFieldName(status);

        Accounts account;
        if (status.getType() == AuthType.LOGIN) {
            account = accountsService.getOne(
                    QueryWrapper.create()
                            .eq(field, username)
                            .eq(ACCOUNTS.PASSWORD.getName(), password));

            if (account == null) {
                throw new IllegalArgumentException("账号或密码错误");
            }
        } else {
            account = accountsService.getOne(
                    QueryWrapper.create()
                            .eq(field, username));

            if (account != null) {
                throw new IllegalArgumentException("账号已存在");
            }

            account = Accounts.builder()
                    .username(username)
                    .password(password)
                    .isActive(true)
                    .isBlocked(false)
                    .build();

            accountsService.save(account);
        }

        return account;
    }

}
