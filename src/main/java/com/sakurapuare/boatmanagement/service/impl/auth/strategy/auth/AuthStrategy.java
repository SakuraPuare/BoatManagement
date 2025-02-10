package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef;
import org.springframework.stereotype.Component;

@Component
public interface AuthStrategy {

    AccountsTableDef accountsTableDef = new AccountsTableDef();

    static String getFieldName(AuthStatus status) {
        String field;
        switch (status.getName()) {
            case AuthName.USERNAME -> field = accountsTableDef.USERNAME.getName();
            case AuthName.PHONE -> field = accountsTableDef.PHONE.getName();
            case AuthName.EMAIL -> field = accountsTableDef.EMAIL.getName();
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }
        return field;
    }

    Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO);
}