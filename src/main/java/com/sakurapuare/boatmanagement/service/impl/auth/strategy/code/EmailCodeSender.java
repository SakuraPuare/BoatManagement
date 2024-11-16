package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.sakurapuare.boatmanagement.pojo.entity.Code;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.CodeService;
import org.springframework.stereotype.Component;

@Component
public class EmailCodeSender implements CodeSender {

    private final CodeService codeService;

    public EmailCodeSender(CodeService codeService) {
        this.codeService = codeService;
    }

    @Override
    public boolean sendCode(User user) {
        // TODO: send code to email

        Code code = codeService.generateCode(user);
        return false;
    }

}
