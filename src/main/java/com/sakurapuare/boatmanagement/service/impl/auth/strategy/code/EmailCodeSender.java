package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.sakurapuare.boatmanagement.pojo.entity.Codes;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.stereotype.Component;

@Component
public class EmailCodeSender implements CodeSender {

    private final CodesService codeService;

    public EmailCodeSender(CodesService codeService) {
        this.codeService = codeService;
    }

    @Override
    public boolean sendCode(Users user) {
        // TODO: send code to email

        Codes code = codeService.generateCode(user);
        return false;
    }

}
