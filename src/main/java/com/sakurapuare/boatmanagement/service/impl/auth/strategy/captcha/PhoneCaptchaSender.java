package com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CaptchaService;
import org.springframework.stereotype.Component;

@Component
public class PhoneCaptchaSender implements CaptchaSender {

    private final CaptchaService captchaService;

    public PhoneCaptchaSender(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        // TODO: send code to phone

        Captcha captha = captchaService.generateCaptcha(nameRequestDTO.getUsername());
        return false;
    }

}
