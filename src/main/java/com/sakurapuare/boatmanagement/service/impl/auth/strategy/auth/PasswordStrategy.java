package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef;

public class PasswordStrategy implements AuthStrategy {

    private static final AccountsTableDef accountsTableDef = new AccountsTableDef();

    private final AccountsMapper accountsMapper;

    public PasswordStrategy(AccountsMapper accountsMapper) {
        this.accountsMapper = accountsMapper;
    }

    @Override
    public Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        String field = AuthStrategy.getFieldName(status);


        Accounts account = null;
        if (status.getType() == AuthType.LOGIN) {
            account = accountsMapper.selectOneByQuery(
                    QueryWrapper.create()
                            .eq(field, username)
                            .eq(accountsTableDef.PASSWORD.getName(), password));

            if (account == null) {
                throw new IllegalArgumentException("账号或密码错误");
            }
        } else {
            account = accountsMapper.selectOneByQuery(
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

            accountsMapper.insertSelective(account);    
        }

        return account;
    }

}
