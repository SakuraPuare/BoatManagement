package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CapthaService;
import org.springframework.stereotype.Component;

@Component
public class PhoneCaptchaSender implements CaptchaSender {

    private final CapthaService capthaService;

    public PhoneCaptchaSender(CapthaService capthaService) {
        this.capthaService = capthaService;
    }

    @Override
    public boolean sendCaptcha(Accounts account) {
        // TODO: send code to phone

        Captcha captha = capthaService.generateCode(account);
        return false;
    }

}
