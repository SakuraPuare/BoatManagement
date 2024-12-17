package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.sakurapuare.boatmanagement.pojo.entity.Codes;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.stereotype.Component;

@Component
public class PhoneCodeSender implements CodeSender {

    private final CodesService codeService;

    public PhoneCodeSender(CodesService codeService) {
        this.codeService = codeService;
    }

    @Override
    public boolean sendCode(Users user) {
        // TODO: send code to phone

        Codes code = codeService.generateCode(user);
        return false;
    }

}
