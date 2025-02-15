package com.sakurapuare.boatmanagement.service.auth;

import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import org.springframework.stereotype.Component;

import static com.sakurapuare.boatmanagement.pojo.entity.table.Tables.ACCOUNTS;

@Component
public interface AuthStrategy {
    static String getFieldName(AuthStatus status) {
        String field;
        switch (status.getName()) {
            case AuthName.USERNAME -> field = ACCOUNTS.USERNAME.getName();
            case AuthName.PHONE -> field = ACCOUNTS.PHONE.getName();
            case AuthName.EMAIL -> field = ACCOUNTS.EMAIL.getName();
            default -> throw new IllegalArgumentException("不支持的验证码发送方式");
        }
        return field;
    }

    Accounts auth(AuthStatus status, AuthRequestDTO authRequestDTO);
}