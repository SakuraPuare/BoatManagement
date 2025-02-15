package com.sakurapuare.boatmanagement.service.captcha;

import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Captcha;
import com.sakurapuare.boatmanagement.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneCaptchaSender implements CaptchaSender {

    private final CaptchaService captchaService;

    @Override
    public boolean sendCaptcha(NameRequestDTO nameRequestDTO) {
        // TODO: send code to phone

        Captcha captcha = captchaService.generateCaptcha(nameRequestDTO.getUsername());
        return false;
    }

}
