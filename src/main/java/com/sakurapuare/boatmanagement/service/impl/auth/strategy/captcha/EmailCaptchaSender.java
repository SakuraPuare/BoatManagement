package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CapthaService;
import org.springframework.stereotype.Component;

@Component
public class EmailCaptchaSender implements CaptchaSender {

    private final CapthaService capthaService;

    public EmailCaptchaSender(CapthaService capthaService) {
        this.capthaService = capthaService;
    }

    @Override
    public boolean sendCaptcha(Accounts account) {
        // TODO: send code to email

        Captcha captha = capthaService.generateCode(account);
        return false;
    }

}
