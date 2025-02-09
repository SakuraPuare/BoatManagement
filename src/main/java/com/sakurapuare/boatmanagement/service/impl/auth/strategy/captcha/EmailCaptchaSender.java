package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CaptchaService;
import org.springframework.stereotype.Component;

@Component
public class EmailCaptchaSender implements CaptchaSender {

    private final CaptchaService captchaService;

    public EmailCaptchaSender(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        // TODO: Send email

        Captcha captha = captchaService.generateCaptcha(nameRequestDTO.getUsername());
        return false;
    }

}
