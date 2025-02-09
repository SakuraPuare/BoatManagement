package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.CapthaService;
import org.springframework.stereotype.Component;

@Component
public class CodeStrategy extends AuthStrategy {

    private final CapthaService capthaService;

    public CodeStrategy(CapthaService capthaService, AccountsMapper accountsMapper) {
        super(accountsMapper);
        this.capthaService = capthaService;
    }

    @Override
    public Accounts auth(AuthRequestDTO authRequestDTO) {
        Accounts account = super.auth(authRequestDTO);

        if (account == null) {
            return null;
        }

        // check code validity
        if (capthaService.verifyCode(account, authRequestDTO.getPassword())) {
            return account;
        }

        return null;
    }

}
