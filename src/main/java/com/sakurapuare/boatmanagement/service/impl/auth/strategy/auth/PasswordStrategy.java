package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;

public class PasswordStrategy extends AuthStrategy {

    public PasswordStrategy(AccountsMapper accountsMapper) {
        super(accountsMapper);
    }

    @Override
    public Accounts auth(AuthRequestDTO authRequestDTO) {
        Accounts account = super.auth(authRequestDTO);

        if (account == null) {
            return null;
        }

        if (account.getPassword().equals(authRequestDTO.getPassword())) {
            return account;
        }

        return null;
    }

}
